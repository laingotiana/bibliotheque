package com.projet.service;

import com.projet.entity.Pret;
import com.projet.entity.Adherant;
import com.projet.entity.Penalite;
import com.projet.repository.PretRepository;
import com.projet.repository.PenaliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PretService {

    @Autowired
    private PretRepository pretRepository;
    @Autowired
    private PenaliteRepository penaliteRepository;

  

    public List<Pret> findAll() {
        return pretRepository.findAll();
    }

    public Pret save(Pret pret) {
        return pretRepository.save(pret);
    }

    public List<Pret> findByAdherantId(int adherantId) {
        return pretRepository.findByAdherant_IdAdherent(adherantId);
    }

    public List<Pret> findByAdherant(Adherant adherant) {
        return pretRepository.findByAdherant(adherant);
    }

     public boolean insererPretSiQuota(Adherant adherant, Pret pret) {
        int quota = adherant.getProfil().getQuotaPret();

        // Vérifier la disponibilité de l'exemplaire
        if (pret.getExemplaire() == null || pret.getExemplaire().getDisponible() <= 0) {
            return false;
        }

        // Nombre de prêts actifs à la date de début du nouveau prêt (non rendus)
        long nbPretsActifs = pretRepository.findByAdherant(adherant).stream()
            .filter(p -> p.isRendu() == 0 && (p.getDateFin() == null || !p.getDateFin().before(pret.getDateDebut())))
            .count();

        // Nombre de prêts déjà faits le même jour
        long nbPretsMemeJour = pretRepository.findByAdherant(adherant).stream()
            .filter(p -> pret.getDateDebut().equals(p.getDateDebut()))
            .count();

        // Vérifier la pénalité sur la date de début du prêt
        List<Penalite> penalites = penaliteRepository.findByAdherantAndDebutPenaliteLessThanEqualAndFinPenaliteGreaterThanEqual(
            adherant, pret.getDateDebut(), pret.getDateDebut()
        );
        boolean estPenalise = !penalites.isEmpty();

        // Condition : pas de pénalité, quota non atteint pour le jour ET pour les prêts actifs, exemplaire dispo
        if (!estPenalise && nbPretsActifs < quota && nbPretsMemeJour < quota) {
            // Décrémenter le nombre d'exemplaires disponibles
            pret.getExemplaire().setDisponible(pret.getExemplaire().getDisponible() - 1);
            pretRepository.save(pret);
            return true;
        }
        return false;
    }



   
   
}
