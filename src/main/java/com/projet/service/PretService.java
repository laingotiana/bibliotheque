package com.projet.service;

import com.projet.entity.Pret;
import com.projet.entity.Adherant;
import com.projet.entity.Exemplaire;
import com.projet.entity.Penalite;
import com.projet.entity.Abonnement;
import com.projet.repository.PretRepository;
import com.projet.repository.PenaliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class PretService {

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private PenaliteRepository penaliteRepository;

    @Autowired
    private AbonnementService abonnementService;

    @Autowired
    private ExemplaireService exemplaireService;

    public static class PretInsertionResult {
        private boolean success;
        private String errorMessage;

        public PretInsertionResult(boolean success, String errorMessage) {
            this.success = success;
            this.errorMessage = errorMessage;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public List<Pret> findAll() {
        return pretRepository.findAll();
    }

    public Pret save(Pret pret) {
        return pretRepository.save(pret);
    }

    public Pret findPretById(int id) {
        return pretRepository.findByIdPret(id);
    }

    public List<Pret> findByAdherantId(int adherantId) {
        return pretRepository.findByAdherant_IdAdherent(adherantId);
    }

    public List<Pret> findByAdherant(Adherant adherant) {
        return pretRepository.findByAdherant(adherant);
    }

    public PretInsertionResult insererPretSiQuota(Adherant adherant, Pret pret) {
        int quota = adherant.getProfil().getQuotaPret();

        // Vérifier la disponibilité de l'exemplaire
        if (pret.getExemplaire() == null || pret.getExemplaire().getDisponible() <= 0) {
            System.out.println("Échec : Exemplaire non disponible ou null.");
            return new PretInsertionResult(false, "L'exemplaire n'est pas disponible.");
        }

        // Vérifier la pénalité sur la date de début du prêt
        List<Penalite> penalites = penaliteRepository.findByAdherantAndDebutPenaliteLessThanEqualAndFinPenaliteGreaterThanEqual(
            adherant, pret.getDateDebut(), pret.getDateDebut()
        );
        boolean estPenalise = !penalites.isEmpty();
        if (estPenalise) {
            System.out.println("Échec : Adhérent pénalisé.");
            return new PretInsertionResult(false, "Vous avez une pénalité active.");
        }

        // Nombre de prêts actifs à la date de début du nouveau prêt (non rendus)
        long nbPretsActifs = pretRepository.findByAdherant(adherant).stream()
            .filter(p -> p.isRendu() == 0 && (p.getDateFin() == null || !p.getDateFin().before(pret.getDateDebut())))
            .count();
        if (nbPretsActifs >= quota) {
            System.out.println("Échec : Quota de prêts actifs atteint (" + nbPretsActifs + "/" + quota + ").");
            return new PretInsertionResult(false, "Quota de prêts actifs atteint (" + nbPretsActifs + "/" + quota + ").");
        }

        // Nombre de prêts déjà faits le même jour
        long nbPretsMemeJour = pretRepository.findByAdherant(adherant).stream()
            .filter(p -> pret.getDateDebut().equals(p.getDateDebut()))
            .count();
        if (nbPretsMemeJour >= quota) {
            System.out.println("Échec : Quota de prêts pour le même jour atteint (" + nbPretsMemeJour + "/" + quota + ").");
            return new PretInsertionResult(false, "Quota de prêts pour le même jour atteint (" + nbPretsMemeJour + "/" + quota + ").");
        }

        // Vérification de l'abonnement
        List<Abonnement> abonnements = abonnementService.findByAdherantId(adherant.getIdAdherent());
        if (abonnements == null || abonnements.isEmpty()) {
            System.out.println("Échec : Aucun abonnement trouvé pour l'adhérent.");
            return new PretInsertionResult(false, "Aucun abonnement actif trouvé.");
        }

        // Normalisation des dates pour ignorer l'heure
        Calendar calDebutPret = Calendar.getInstance();
        calDebutPret.setTime(pret.getDateDebut());
        resetTime(calDebutPret);

        Calendar calFinPret = Calendar.getInstance();
        calFinPret.setTime(pret.getDateFin());
        resetTime(calFinPret);

        boolean hasValidAbonnement = abonnements.stream().anyMatch(abonnement -> {
            if (abonnement.getDateDebut() == null || abonnement.getDateFin() == null) {
                System.out.println("Abonnement ignoré : dates null.");
                return false;
            }
            Calendar calDebutAbonnement = Calendar.getInstance();
            calDebutAbonnement.setTime(abonnement.getDateDebut());
            resetTime(calDebutAbonnement);

            Calendar calFinAbonnement = Calendar.getInstance();
            calFinAbonnement.setTime(abonnement.getDateFin());
            resetTime(calFinAbonnement);

            System.out.println("Vérification abonnement : Prêt de " + calDebutPret.getTime() + " à " + calFinPret.getTime());
            System.out.println("Abonnement de " + calDebutAbonnement.getTime() + " à " + calFinAbonnement.getTime());

            return (!calDebutPret.before(calDebutAbonnement) || calDebutPret.equals(calDebutAbonnement)) &&
                   (!calFinPret.after(calFinAbonnement) || calFinPret.equals(calFinAbonnement));
        });

        if (!hasValidAbonnement) {
            System.out.println("Échec : Les dates du prêt ne sont pas dans la période d'un abonnement actif.");
            return new PretInsertionResult(false, "Les dates du prêt ne sont pas dans la période d'un abonnement actif.");
        }

        // Si toutes les conditions sont remplies, sauvegarder le prêt
        // pret.getExemplaire().setDisponible(pret.getExemplaire().getDisponible() - 1);
        Exemplaire exemplaire = pret.getExemplaire();
        int nouvelleDisponibilite = exemplaire.getDisponible() - 1;
        if (nouvelleDisponibilite < 0) {
            System.out.println("Échec : La disponibilité ne peut pas devenir négative.");
            return new PretInsertionResult(false, "La disponibilité ne peut pas devenir négative.");
        }
        exemplaire.setDisponible(nouvelleDisponibilite);
        try {
            exemplaireService.save(exemplaire);
            System.out.println("Disponibilité de l'exemplaire " + exemplaire.getIdExemplaire() + " mise à jour à " + nouvelleDisponibilite);
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde de l'exemplaire : " + e.getMessage());
            return new PretInsertionResult(false, "Erreur lors de la mise à jour de la disponibilité : " + e.getMessage());
        }
        pretRepository.save(pret);
        System.out.println("Prêt inséré avec succès.");
        return new PretInsertionResult(true, null);
    }

    // Méthode utilitaire pour réinitialiser l'heure à 00:00:00
    private void resetTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}