package com.projet.controller;

import com.projet.entity.*;
import com.projet.service.PretService.PretInsertionResult;
import com.projet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.ParseException;

@Controller
public class AdherantController {

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private PretService pretService;

    @Autowired
    private TypePretService typePretService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private ProlongementService prolongementService;

    @Autowired
    private AbonnementService abonnementService;

    @PostMapping("/adherent_login")
    public String login(@RequestParam("nom") String nom,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session) {
        if (adherantService.verifierAdherant(nom, password)) {
            Adherant adherant = adherantService.findByNom(nom);
            session.setAttribute("adherantId", adherant.getIdAdherent());
            return "Adherant/home_adherant";
        } else {
            model.addAttribute("erreur", "Nom ou mot de passe incorrect");
            return "Adherant/form_adherant";
        }
    }

    @GetMapping("/render_insertPret")
    public String showInsertPretForm(Model model) {
        List<Exemplaire> exemplaires = exemplaireService.getAllExemplairesAvecLivre();
        List<Adherant> adherants = adherantService.findAll();
        List<TypePret> typePrets = typePretService.getAllTypePrets();
        model.addAttribute("exemplaires", exemplaires);
        model.addAttribute("adherants", adherants);
        model.addAttribute("types", typePrets);
        return "Adherant/insert_pret";
    }

    // @PostMapping("/insert_pret")
    // public String insertPret(@RequestParam("id_exemplaire") int idExemplaire,
    //                          @RequestParam("id_type") int idType,
    //                          @RequestParam("date_debut") String dateDebutStr,
    //                          @RequestParam("date_fin") String dateFinStr,
    //                          Model model,
    //                          HttpSession session) {
    //     try {
    //         // Vérification de la session
    //         Integer adherantId = (Integer) session.getAttribute("adherantId");
    //         if (adherantId == null) {
    //             model.addAttribute("erreur", "Vous devez être connecté pour effectuer un prêt.");
    //             return "Adherant/form_adherant";
    //         }
    //         Adherant adherant = adherantService.findById(adherantId).orElse(null);
    //         if (adherant == null) {
    //             model.addAttribute("erreur", "Adhérent non trouvé.");
    //             return "Adherant/form_adherant";
    //         }

    //         // Vérification de l'exemplaire
    //         Exemplaire exemplaire = exemplaireService.findById(idExemplaire).orElse(null);
    //         if (exemplaire == null) {
    //             model.addAttribute("erreur", "Exemplaire non trouvé.");
    //             return "Adherant/insert_pret";
    //         }

    //         // Vérification du type de prêt
    //         TypePret typePret = typePretService.findById(idType).orElse(null);
    //         if (typePret == null) {
    //             model.addAttribute("erreur", "Type de prêt non trouvé.");
    //             return "Adherant/insert_pret";
    //         }

    //         // Validation des chaînes de dates
    //         if (dateDebutStr == null || dateDebutStr.trim().isEmpty() || dateFinStr == null || dateFinStr.trim().isEmpty()) {
    //             model.addAttribute("erreur", "Les dates de début et de fin doivent être fournies.");
    //             return "Adherant/insert_pret";
    //         }

    //         // Parsing des dates
    //         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //         sdf.setLenient(false);
    //         Date dateDebut;
    //         Date dateFin;
    //         try {
    //             dateDebut = sdf.parse(dateDebutStr);
    //             dateFin = sdf.parse(dateFinStr);
    //         } catch (ParseException e) {
    //             model.addAttribute("erreur", "Format de date invalide. Utilisez le format AAAA-MM-JJ.");
    //             return "Adherant/insert_pret";
    //         }

    //         // Validation des dates
    //         if (dateDebut.after(dateFin)) {
    //             model.addAttribute("erreur", "La date de début ne peut pas être après la date de fin.");
    //             return "Adherant/insert_pret";
    //         }

    //         // Création du prêt
    //         Pret pret = new Pret();
    //         pret.setAdherant(adherant);
    //         pret.setExemplaire(exemplaire);
    //         pret.setTypePret(typePret);
    //         pret.setDateDebut(dateDebut);
    //         pret.setDateFin(dateFin);
    //         pret.setRendu(0);
    //         pret.setDate_rendu(null);

    //         // Appel à PretService pour insérer le prêt avec toutes les vérifications
    //         PretInsertionResult result = pretService.insererPretSiQuota(adherant, pret);

    //         if (result.isSuccess()) {
    //             model.addAttribute("message", "Le prêt a été inséré avec succès !");
    //         } else {
    //             model.addAttribute("erreur", result.getErrorMessage());
    //         }
    //     } catch (Exception e) {
    //         model.addAttribute("erreur", "Erreur lors de l'insertion du prêt : " + e.getMessage());
    //         e.printStackTrace();
    //     }

    //     // Renvoyer les listes pour le formulaire
    //     model.addAttribute("exemplaires", exemplaireService.getAllExemplairesAvecLivre());
    //     model.addAttribute("adherants", adherantService.findAll());
    //     model.addAttribute("types", typePretService.getAllTypePrets());

    //     return "Adherant/insert_pret";
    // }


    @PostMapping("/insert_pret")
    public String insertPret(@RequestParam("id_exemplaire") int idExemplaire,
                            @RequestParam("id_type") int idType,
                            @RequestParam("date_debut") String dateDebutStr,
                            Model model,
                            HttpSession session) {
        try {
            // Vérification de la session
            Integer adherantId = (Integer) session.getAttribute("adherantId");
            if (adherantId == null) {
                model.addAttribute("erreur", "Vous devez être connecté pour effectuer un prêt.");
                return "Adherant/form_adherant";
            }
            Adherant adherant = adherantService.findById(adherantId).orElse(null);
            if (adherant == null) {
                model.addAttribute("erreur", "Adhérent non trouvé.");
                return "Adherant/form_adherant";
            }

            // Vérification de l'exemplaire
            Exemplaire exemplaire = exemplaireService.findById(idExemplaire).orElse(null);
            if (exemplaire == null) {
                model.addAttribute("erreur", "Exemplaire non trouvé.");
                return "Adherant/insert_pret";
            }

            // Vérification du type de prêt
            TypePret typePret = typePretService.findById(idType).orElse(null);
            if (typePret == null) {
                model.addAttribute("erreur", "Type de prêt non trouvé.");
                return "Adherant/insert_pret";
            }

            // Validation de la date de début
            if (dateDebutStr == null || dateDebutStr.trim().isEmpty()) {
                model.addAttribute("erreur", "La date de début doit être fournie.");
                return "Adherant/insert_pret";
            }

            // Parsing de la date de début
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date dateDebut;
            try {
                dateDebut = sdf.parse(dateDebutStr);
            } catch (ParseException e) {
                model.addAttribute("erreur", "Format de date invalide. Utilisez le format AAAA-MM-JJ.");
                return "Adherant/insert_pret";
            }

            // Calcul de la date de fin en utilisant jourPret du profil
            if (adherant.getProfil() == null) {
                model.addAttribute("erreur", "Aucun profil associé à l'adhérent.");
                return "Adherant/insert_pret";
            }
            int jourPret = adherant.getProfil().getJourPret();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateDebut);
            calendar.add(Calendar.DAY_OF_MONTH, jourPret);
            Date dateFin = calendar.getTime();

            // Création du prêt
            Pret pret = new Pret();
            pret.setAdherant(adherant);
            pret.setExemplaire(exemplaire);
            pret.setTypePret(typePret);
            pret.setDateDebut(dateDebut);
            pret.setDateFin(dateFin);
            pret.setRendu(0);
            pret.setDate_rendu(null);

            // Appel à PretService pour insérer le prêt avec toutes les vérifications
            PretService.PretInsertionResult result = pretService.insererPretSiQuota(adherant, pret);

            if (result.isSuccess()) {
                model.addAttribute("message", "Le prêt a été inséré avec succès !");
            } else {
                model.addAttribute("erreur", result.getErrorMessage());
            }
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de l'insertion du prêt : " + e.getMessage());
            e.printStackTrace();
        }

        // Renvoyer les listes pour le formulaire
        model.addAttribute("exemplaires", exemplaireService.getAllExemplairesAvecLivre());
        model.addAttribute("adherants", adherantService.findAll());
        model.addAttribute("types", typePretService.getAllTypePrets());

        return "Adherant/insert_pret";
    }


    @GetMapping("/liste_pret")
    public String voirPretsAdherant(HttpSession session, Model model) {
        Integer adherantId = (Integer) session.getAttribute("adherantId");
        if (adherantId == null) {
            model.addAttribute("erreur", "Vous devez être connecté pour voir vos prêts.");
            return "Adherant/form_adherant";
        }
        List<Pret> prets = pretService.findByAdherantId(adherantId);
        model.addAttribute("prets", prets);
        return "Adherant/pret_list";
    }

    @GetMapping("/faire_reservation")
    public String render_reservation(Model model, HttpSession session) {
        List<Exemplaire> exemplaires = exemplaireService.getAllExemplairesAvecLivre();
        model.addAttribute("exemplaires", exemplaires);
        Integer adherantId = (Integer) session.getAttribute("adherantId");
        if (adherantId != null) {
            List<Reservation> reservations = reservationService.findByAdherantId(adherantId);
            model.addAttribute("reservations", reservations);
        }
        return "Adherant/reservation";
    }

    @PostMapping("/insert_reservation")
    public String insertReservation(@RequestParam("id_exemplaire") int idExemplaire,
                                   @RequestParam("date_debut_pret") String dateDebutPretStr,
                                   @RequestParam("date_fin_pret") String dateFinPretStr,
                                   Model model,
                                   HttpSession session) {
        try {
            // Vérification de la session
            Integer adherantId = (Integer) session.getAttribute("adherantId");
            if (adherantId == null) {
                model.addAttribute("erreur", "Vous devez être connecté pour effectuer une réservation.");
                return "Adherant/reservation";
            }
            Adherant adherant = adherantService.findById(adherantId).orElse(null);
            if (adherant == null) {
                model.addAttribute("erreur", "Adhérent non trouvé.");
                return "Adherant/reservation";
            }

            // Vérification de l'exemplaire
            Exemplaire exemplaire = exemplaireService.findById(idExemplaire).orElse(null);
            if (exemplaire == null) {
                model.addAttribute("erreur", "Exemplaire non trouvé.");
                return "Adherant/reservation";
            }

            // Vérification du statut
            Status status = statusService.findAll().stream()
                .filter(s -> s.getNomStatus().equalsIgnoreCase("en attent") || s.getNomStatus().equalsIgnoreCase("En attente"))
                .findFirst()
                .orElse(null);
            if (status == null) {
                model.addAttribute("erreur", "Le statut 'en attente' n'existe pas.");
                model.addAttribute("exemplaires", exemplaireService.getAllExemplairesAvecLivre());
                return "Adherant/reservation";
            }

            // Validation des chaînes de dates
            if (dateDebutPretStr == null || dateDebutPretStr.trim().isEmpty() || dateFinPretStr == null || dateFinPretStr.trim().isEmpty()) {
                model.addAttribute("erreur", "Les dates de début et de fin doivent être fournies.");
                return "Adherant/reservation";
            }

            // Parsing des dates
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date dateDebutPret;
            Date dateFinPret;
            try {
                dateDebutPret = sdf.parse(dateDebutPretStr);
                dateFinPret = sdf.parse(dateFinPretStr);
            } catch (ParseException e) {
                model.addAttribute("erreur", "Format de date invalide. Utilisez le format AAAA-MM-JJ.");
                return "Adherant/reservation";
            }

            // Validation des dates
            if (dateDebutPret.after(dateFinPret)) {
                model.addAttribute("erreur", "La date de début ne peut pas être après la date de fin.");
                return "Adherant/reservation";
            }

            // Vérification de l'abonnement
            List<Abonnement> abonnements = abonnementService.findByAdherantId(adherantId);
            if (abonnements == null || abonnements.isEmpty()) {
                model.addAttribute("erreur", "Aucun abonnement actif trouvé pour cet adhérent.");
                return "Adherant/reservation";
            }

            // Normalisation des dates pour ignorer l'heure
            Calendar calDebutPret = Calendar.getInstance();
            calDebutPret.setTime(dateDebutPret);
            resetTime(calDebutPret);

            Calendar calFinPret = Calendar.getInstance();
            calFinPret.setTime(dateFinPret);
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

                System.out.println("Vérification abonnement pour réservation : Réservation de " + sdf.format(dateDebutPret) + " à " + sdf.format(dateFinPret));
                System.out.println("Abonnement de " + sdf.format(abonnement.getDateDebut()) + " à " + sdf.format(abonnement.getDateFin()));

                return (!calDebutPret.before(calDebutAbonnement) || calDebutPret.equals(calDebutAbonnement)) &&
                       (!calFinPret.after(calFinAbonnement) || calFinPret.equals(calFinAbonnement));
            });

            if (!hasValidAbonnement) {
                model.addAttribute("erreur", "Les dates de la réservation ne sont pas dans la période d'un abonnement actif.");
                return "Adherant/reservation";
            }

            // Création de la réservation
            Reservation reservation = new Reservation();
            reservation.setAdherant(adherant);
            reservation.setExemplaire(exemplaire);
            reservation.setStatus(status);
            reservation.setDateReservation(new Date());
            reservation.setDateDebutPret(dateDebutPret);
            reservation.setDateFinPret(dateFinPret);

            reservationService.save(reservation);

            model.addAttribute("message", "Réservation effectuée avec succès !");
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de la réservation : " + e.getMessage());
            e.printStackTrace();
        }

        model.addAttribute("exemplaires", exemplaireService.getAllExemplairesAvecLivre());
        return "Adherant/reservation";
    }

    @PostMapping("/prolonger")
    public String prolongerPret(@RequestParam("pretId") int pretId,
                               @RequestParam("dateProlongement") String dateProlongement,
                               Model model,
                               HttpSession session) {
        try {
            // Vérification de la session
            Integer adherantId = (Integer) session.getAttribute("adherantId");
            if (adherantId == null) {
                model.addAttribute("erreur", "Vous devez être connecté pour demander un prolongement.");
                return "Adherant/pret_list";
            }
            Adherant adherant = adherantService.findById(adherantId).orElse(null);
            if (adherant == null) {
                model.addAttribute("erreur", "Adhérent non trouvé.");
                return "Adherant/pret_list";
            }
            Pret pret = pretService.findPretById(pretId);
            if (pret == null) {
                model.addAttribute("erreur", "Prêt non trouvé.");
                return "Adherant/pret_list";
            }
            if (pret.getRendu() == 1) {
                model.addAttribute("erreur", "Ce prêt est déjà rendu, impossible de prolonger.");
                return "Adherant/pret_list";
            }
            List<Prolongement> prolongements = prolongementService.findAll();
            boolean dejaEnAttente = prolongements.stream().anyMatch(p -> p.getPret().getIdPret() == pretId && 
                (p.getStatus().getNomStatus().equalsIgnoreCase("en attent") || p.getStatus().getNomStatus().equalsIgnoreCase("En attente")));
            if (dejaEnAttente) {
                model.addAttribute("erreur", "Une demande de prolongement est déjà en attente pour ce prêt.");
                return "Adherant/pret_list";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date dateProlongementStr;
            try {
                dateProlongementStr = sdf.parse(dateProlongement);
            } catch (ParseException e) {
                model.addAttribute("erreur", "Format de date de prolongement invalide. Utilisez le format AAAA-MM-JJ.");
                return "Adherant/pret_list";
            }
            // Vérification de la date de prolongement
            if (!dateProlongementStr.after(pret.getDateFin())) {
                model.addAttribute("erreur", "La date de prolongement doit être après la date de fin actuelle.");
                return "Adherant/pret_list";
            }
            // Vérification des réservations en conflit
            List<Reservation> reservations = reservationService.findByExemplaireId(pret.getExemplaire().getIdExemplaire());
            boolean reservationEnConflit = reservations.stream().anyMatch(r -> r.getDateDebutPret() != null && r.getDateFinPret() != null &&
                ((dateProlongementStr.after(r.getDateDebutPret()) && dateProlongementStr.before(r.getDateFinPret())) || 
                 dateProlongementStr.equals(r.getDateDebutPret()) || dateProlongementStr.equals(r.getDateFinPret())) &&
                (r.getStatus().getNomStatus().equalsIgnoreCase("confirmee") || r.getStatus().getNomStatus().equalsIgnoreCase("Confirmée")));
            if (reservationEnConflit) {
                model.addAttribute("erreur", "Impossible de prolonger : une réservation existe sur cet exemplaire à la date demandée.");
                return "Adherant/pret_list";
            }
            // Vérification de l'abonnement
            List<Abonnement> abonnements = abonnementService.findByAdherantId(adherantId);
            if (abonnements == null || abonnements.isEmpty()) {
                model.addAttribute("erreur", "Aucun abonnement actif trouvé pour cet adhérent.");
                return "Adherant/pret_list";
            }
            Calendar calProlongement = Calendar.getInstance();
            calProlongement.setTime(dateProlongementStr);
            resetTime(calProlongement);

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

                System.out.println("Vérification abonnement pour prolongement : Date de prolongement " + sdf.format(dateProlongementStr));
                System.out.println("Abonnement de " + sdf.format(abonnement.getDateDebut()) + " à " + sdf.format(abonnement.getDateFin()));

                return (!calProlongement.before(calDebutAbonnement) || calProlongement.equals(calDebutAbonnement)) &&
                       (!calProlongement.after(calFinAbonnement) || calProlongement.equals(calFinAbonnement));
            });

            if (!hasValidAbonnement) {
                model.addAttribute("erreur", "La date de prolongement n'est pas dans la période d'un abonnement actif.");
                return "Adherant/pret_list";
            }

            // Création du prolongement
            Prolongement prolongement = new Prolongement();
            prolongement.setPret(pret);
            prolongement.setDateProlongement(dateProlongementStr);
            prolongement.setAdherant(adherant);
            Status statusEnAttente = statusService.findAll().stream()
                .filter(s -> s.getNomStatus().equalsIgnoreCase("en attent") || s.getNomStatus().equalsIgnoreCase("En attente"))
                .findFirst()
                .orElse(new Status(1, "En attente"));
            prolongement.setStatus(statusEnAttente);
            prolongementService.save(prolongement);
            model.addAttribute("message", "Demande de prolongement envoyée avec succès !");
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de la demande de prolongement : " + e.getMessage());
            e.printStackTrace();
        }
        Integer adherantId = (Integer) session.getAttribute("adherantId");
        if (adherantId != null) {
            List<Pret> prets = pretService.findByAdherantId(adherantId);
            model.addAttribute("prets", prets);
        }
        return "Adherant/pret_list";
    }

    @GetMapping("/liste_reservations")
    public String voirReservationsAdherant(HttpSession session, Model model) {
        Integer adherantId = (Integer) session.getAttribute("adherantId");
        if (adherantId == null) {
            model.addAttribute("erreur", "Vous devez être connecté pour voir vos réservations.");
            return "Adherant/form_adherant";
        }
        List<Reservation> reservations = reservationService.findByAdherantId(adherantId);
        model.addAttribute("reservations", reservations);
        return "Adherant/reservation_list";
    }

    @PostMapping("/annuler_reservation")
    public String annulerReservation(@RequestParam("reservationId") int reservationId, HttpSession session, Model model) {
        Integer adherantId = (Integer) session.getAttribute("adherantId");
        if (adherantId == null) {
            model.addAttribute("erreur", "Vous devez être connecté pour annuler une réservation.");
            return "Adherant/form_adherant";
        }
        Reservation reservation = reservationService.findById(reservationId);
        if (reservation == null || reservation.getAdherant().getIdAdherent() != adherantId) {
            model.addAttribute("erreur", "Réservation non trouvée ou accès refusé.");
        } else {
            Status statusAnnulee = statusService.findAll().stream()
                .filter(s -> s.getNomStatus().equalsIgnoreCase("annulee") || s.getNomStatus().equalsIgnoreCase("Annulée"))
                .findFirst()
                .orElse(null);
            if (statusAnnulee != null) {
                reservation.setStatus(statusAnnulee);
                reservationService.save(reservation);
                model.addAttribute("message", "Réservation annulée avec succès.");
            } else {
                model.addAttribute("erreur", "Le statut 'annulée' n'existe pas.");
            }
        }
        List<Reservation> reservations = reservationService.findByAdherantId(adherantId);
        model.addAttribute("reservations", reservations);
        return "Adherant/reservation_list";
    }

    // Méthode utilitaire pour réinitialiser l'heure à 00:00:00
    private void resetTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}