package com.projet.controller;

import com.projet.entity.Abonnement;
import com.projet.entity.Adherant;
import com.projet.entity.Penalite;
import com.projet.entity.Pret;
import com.projet.entity.Prolongement;
import com.projet.entity.Reservation;
import com.projet.entity.TypePret;
import com.projet.entity.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.projet.service.*;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Date;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PretService pretService;

    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ProlongementService prolongementService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private LivreService livreService;

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private AbonnementService abonnementService;

    @PostMapping("/login")
    public String login(@RequestParam("nom") String nom,
                        @RequestParam("mdp") String mdp,
                        Model model) {
        if (adminService.verifierAdmin(nom, mdp)) {
            // Connexion réussie, récupération de la liste des prêts
            List<Pret> listePrets = pretService.findAll();
            model.addAttribute("prets", listePrets);
            return "Admin/home";
        } else {
            model.addAttribute("erreur", "Nom ou mot de passe incorrect.");
            return "index";
        }

    }
 


     @GetMapping("/rendre_livre")
    public String rendrePret(
            @RequestParam("pretId") int pretId,
            @RequestParam("date_rendu") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_rendu,
            Model model) {
        try {
            Pret pret = pretService.findPretById(pretId);
            pret.setDate_rendu(date_rendu);
            pret.setRendu(1);
            pretService.save(pret);
            
            if (pret.getDateFin() != null) {
                long joursRetard = TimeUnit.DAYS.convert(
                    date_rendu.getTime() - pret.getDateFin().getTime(),
                    TimeUnit.MILLISECONDS
                );
                
                if (joursRetard > 0) {
                    System.out.println("Vous avez dépassé la date de retour");
                    Penalite penalite = new Penalite();
                    penalite.setAdherant(pret.getAdherant());
                    penalite.setDebutPenalite(date_rendu);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date_rendu);
                    calendar.add(Calendar.DAY_OF_YEAR,pret.getAdherant().getProfil().getJourpenalite());
                    penalite.setFinPenalite(calendar.getTime());
                    penaliteService.save(penalite);
                    model.addAttribute("warning", "Livre rendu avec " + joursRetard + " jours de retard. Pénalité appliquée.");
                }
            }
            
            model.addAttribute("message", "Le prêt a été marqué comme rendu avec succès.");
            model.addAttribute("prets", pretService.findAll());
            return "Admin/home";
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur : " + e.getMessage());
            model.addAttribute("prets", pretService.findAll());
            return "Admin/home";
        }
    }

    @GetMapping("/accepter_reservation")
    public String accepter_reservation(
         @RequestParam("id_reservation") int id_reservation,
         Model model
    ){
        Reservation reservation = reservationService.findById(id_reservation);
        Status status =new Status(2,"Confirmée");
        reservation.setStatus(status);
        reservationService.save(reservation);
        Pret pret = new Pret();
        pret.setAdherant(reservation.getAdherant());
        pret.setExemplaire(reservation.getExemplaire());
        pret.setDateDebut(reservation.getDateDebutPret());
        pret.setDateFin(reservation.getDateFinPret());
        pret.setRendu(0);
        TypePret typePret = new TypePret(2,"Long terme");
        pret.setTypePret(typePret);
        pretService.save(pret);
        List<Reservation> reservations = reservationService.findAll();
        model.addAttribute("reservations", reservations);
        return "Admin/Reservation";
    }
   

      @GetMapping("/prolongation")
    public String afficherProlongements(Model model) {
        List<Prolongement> prolongements = prolongementService.findAll();
        model.addAttribute("prolongements", prolongements);
        return "Admin/prolongement_list";
    }


    
    // Accepter une demande de prolongement (statut = confirmée)
    @PostMapping("/accepter_prolongement")
    public String accepterProlongement(@RequestParam("idProlongement") int idProlongement, Model model) {
        Prolongement prolongement = prolongementService.findById(idProlongement).orElse(null);
        if (prolongement == null) {
            model.addAttribute("erreur", "Prolongement introuvable.");
        } else {
            // Mettre à jour le statut à "confirmée"
            Status statusConfirmee = statusService.findAll().stream()
                .filter(s -> s.getNomStatus().equalsIgnoreCase("Confirme") || s.getNomStatus().equalsIgnoreCase("Confirmée"))
                .findFirst().orElse(null);
            if (statusConfirmee == null) {
                model.addAttribute("erreur", "Le statut 'Confirmée' n'existe pas.");
            } else {
                prolongement.setStatus(statusConfirmee);
                prolongementService.save(prolongement);
                // Mettre à jour la date de fin du prêt
                Pret pret = prolongement.getPret();
                pret.setDateFin(prolongement.getDateProlongement());
                pretService.save(pret);
                model.addAttribute("message", "Prolongement accepté et date de fin du prêt mise à jour.");
            }
        }
        List<Prolongement> prolongements = prolongementService.findAll();
        model.addAttribute("prolongements", prolongements);
        return "Admin/prolongement_list";
    }

    // Refuser une demande de prolongement (statut = annulée)
    @PostMapping("/refuser_prolongement")
    public String refuserProlongement(@RequestParam("idProlongement") int idProlongement, Model model) {
        Prolongement prolongement = prolongementService.findById(idProlongement).orElse(null);
        if (prolongement == null) {
            model.addAttribute("erreur", "Prolongement introuvable.");
        } else {
            Status statusAnnulee = statusService.findAll().stream()
                .filter(s -> s.getNomStatus().equalsIgnoreCase("annulee") || s.getNomStatus().equalsIgnoreCase("Annulée"))
                .findFirst().orElse(null);
            if (statusAnnulee == null) {
                model.addAttribute("erreur", "Le statut 'Annulée' n'existe pas.");
            } else {
                prolongement.setStatus(statusAnnulee);
                prolongementService.save(prolongement);
                model.addAttribute("message", "Prolongement refusé.");
            }
        }
        List<Prolongement> prolongements = prolongementService.findAll();
        model.addAttribute("prolongements", prolongements);
        return "Admin/prolongement_list";
    }

    @GetMapping("/penalites")
    public String afficherPenalites(Model model) {
        List<Penalite> penalites = penaliteService.findAll();
        model.addAttribute("penalites", penalites);
        return "Admin/penalite_list";
    }

    @GetMapping("/livre_list")
    public String afficherListeLivres(Model model) {
        model.addAttribute("livres", livreService.findAll());
        return "Admin/livre_list";
    }

    @GetMapping("/adherent_list")
    public String afficherListeAdherents(Model model) {
        List<Adherant> adherants = adherantService.findAll();
        model.addAttribute("adherants", adherants);
        return "Admin/adherent_list";
    }

    @GetMapping("/adherent_detail")
    public String afficherDetailAdherent(@RequestParam("id") int id, Model model) {
        var adherantOpt = adherantService.findById(id);
        if (adherantOpt.isEmpty()) {
            model.addAttribute("error", "Adhérent non trouvé");
            return "Admin/adherent_detail";
        }

        Adherant adherant = adherantOpt.get();
        model.addAttribute("adherant", adherant);

        // Quota de prêts
        int quotaPretTotal = adherant.getProfil() != null ? adherant.getProfil().getQuotaPret() : 0;
        List<Pret> pretsActifs = pretService.findByAdherant(adherant).stream()
            .filter(p -> p.getRendu() == 0)
            .toList();
        int quotaPretRestant = quotaPretTotal - pretsActifs.size();
        model.addAttribute("quotaPretTotal", quotaPretTotal);
        model.addAttribute("quotaPretRestant", Math.max(0, quotaPretRestant));

        // Quota de réservations
        int quotaReservationTotal = adherant.getProfil() != null ? adherant.getProfil().getQuotaReservation() : 0;
        List<Reservation> reservationsActives = reservationService.findByAdherantId(id).stream()
            .filter(r -> r.getStatus() != null && 
                (r.getStatus().getNomStatus().equalsIgnoreCase("en attente") || 
                 r.getStatus().getNomStatus().equalsIgnoreCase("confirmée")))
            .toList();
        int quotaReservationRestant = quotaReservationTotal - reservationsActives.size();
        model.addAttribute("quotaReservationTotal", quotaReservationTotal);
        model.addAttribute("quotaReservationRestant", Math.max(0, quotaReservationRestant));

        // Abonnements
        List<Abonnement> abonnements = abonnementService.findByAdherantId(id);
        model.addAttribute("abonnements", abonnements);

        // Pénalités
        List<Penalite> penalites = penaliteService.findByAdherantId(id);
        model.addAttribute("penalites", penalites);

        return "Admin/adherent_detail";
    }

    // @GetMapping("/adherent_detail")
    // public String afficherDetailAdherent(@RequestParam("id") int id, Model model) {
    //     var adherantOpt = adherantService.findById(id);
    //     if (adherantOpt.isEmpty()) {
    //         model.addAttribute("error", "Adhérent non trouvé");
    //         return "Admin/adherent_detail";
    //     }

    //     Adherant adherant = adherantOpt.get();
    //     model.addAttribute("adherant", adherant);

    //     // Quota total et restant
    //     int quotaTotal = adherant.getProfil() != null ? adherant.getProfil().getQuotaPret() : 0;
    //     List<Pret> pretsActifs = pretService.findByAdherant(adherant).stream()
    //         .filter(p -> p.getRendu() == 0)
    //         .toList();
    //     int quotaRestant = quotaTotal - pretsActifs.size();
    //     model.addAttribute("quotaTotal", quotaTotal);
    //     model.addAttribute("quotaRestant", Math.max(0, quotaRestant));

    //     // Abonnements
    //     List<Abonnement> abonnements = abonnementService.findByAdherantId(id);
    //     model.addAttribute("abonnements", abonnements);

    //     // Pénalités
    //     List<Penalite> penalites = penaliteService.findByAdherantId(id);
    //     model.addAttribute("penalites", penalites);

    //     return "Admin/adherent_detail";
    // }


    @GetMapping("/livre_detail")
    public String afficherDetailLivre(@RequestParam("id") int id, Model model) {
        var livreOpt = livreService.findById(id);
        if (livreOpt.isPresent()) {
            var livre = livreOpt.get();
            model.addAttribute("livre", livre);
            // Correction : filtrer les exemplaires du livre côté Java
            var allExemplaires = exemplaireService.getAllExemplairesAvecLivre();
            java.util.List<com.projet.entity.Exemplaire> exemplairesLivre = new java.util.ArrayList<>();
            java.util.Map<Integer, Integer> restantDispoMap = new java.util.HashMap<>();
            for (var ex : allExemplaires) {
                if (ex.getLivre() != null && ex.getLivre().getIdLivre() == livre.getIdLivre()) {
                    exemplairesLivre.add(ex);
                }
            }
            // Calcul du restant disponible pour chaque exemplaire
            java.util.List<com.projet.entity.Pret> allPrets = pretService.findAll();
            for (var ex : exemplairesLivre) {
                int nbPretsNonRendus = 0;
                for (var pret : allPrets) {
                    if (pret.getExemplaire() != null && pret.getExemplaire().getIdExemplaire() == ex.getIdExemplaire() && pret.getRendu() == 0) {
                        nbPretsNonRendus++;
                    }
                }
                int restant = ex.getDisponible() - nbPretsNonRendus;
                if (restant < 0) restant = 0;
                restantDispoMap.put(ex.getIdExemplaire(), restant);
            }
            model.addAttribute("exemplaires", exemplairesLivre);
            model.addAttribute("restantDispoMap", restantDispoMap);
        } else {
            model.addAttribute("error", "Livre non trouvé");
        }
        return "Admin/livre_detail";
    }


    @GetMapping("/livre_detail_json")
    @ResponseBody
    public java.util.Map<String, Object> getLivreDetailJson(@RequestParam("id") int id) {
        java.util.Map<String, Object> result = new java.util.HashMap<>();
        var livreOpt = livreService.findById(id);
        if (livreOpt.isPresent()) {
            var livre = livreOpt.get();
            result.put("id", livre.getIdLivre());
            result.put("titre", livre.getTitre());
            result.put("isbn", livre.getIsbn());
            result.put("langue", livre.getLangue());
            result.put("anneePublication", livre.getAnneePublication());
            result.put("nbPage", livre.getNbPage());
            result.put("synopsis", livre.getSynopsis());
            if (livre.getAuteur() != null) {
                var auteur = new java.util.HashMap<String, Object>();
                auteur.put("nom", livre.getAuteur().getNomAuteur());
                auteur.put("prenom", livre.getAuteur().getPrenomAuteur());
                result.put("auteur", auteur);
            }
            // Exemplaires
            var exemplaires = exemplaireService.findByLivreIdLivre(livre.getIdLivre());
            java.util.List<java.util.Map<String, Object>> exList = new java.util.ArrayList<>();
            for (var ex : exemplaires) {
                var exMap = new java.util.HashMap<String, Object>();
                exMap.put("idExemplaire", ex.getIdExemplaire());
                exMap.put("disponible", ex.getDisponible());
                exList.add(exMap);
            }
            result.put("exemplaires", exList);
        } else {
            result.put("error", "Livre non trouvé");
        }
        return result;
    }


    



}