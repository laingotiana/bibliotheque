package com.projet.controller;
import java.util.Date;
import com.projet.service.AdherantService;
import com.projet.service.ExemplaireService;
import com.projet.service.PretService;
import com.projet.service.TypePretService;
import com.projet.service.ReservationService;
import com.projet.service.StatusService;
import com.projet.service.ProlongementService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.projet.entity.Exemplaire;
import com.projet.entity.Pret;
import com.projet.entity.Adherant;
import com.projet.entity.TypePret;
import com.projet.entity.Reservation;
import com.projet.entity.Status;
import com.projet.entity.Prolongement;

import java.text.SimpleDateFormat;
import java.util.List;

import jakarta.servlet.http.HttpSession;

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


    @PostMapping("/adherent_login")
    public String login(@RequestParam("nom") String nom,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session) {
        if (adherantService.verifierAdherant(nom, password)) {
            // Récupérer l'adhérant connecté
            Adherant adherant = adherantService.findByNom(nom);
            // Stocker l'id dans la session
            session.setAttribute("adherantId", adherant.getIdAdherent());
            // Connexion réussie
            return "Adherant/home_adherant"; // Redirige vers la page d'accueil des adhérents
        } else {
            model.addAttribute("erreur", "Nom ou mot de passe incorrect");
            return "Adherant/form_adherant"; // Redirige vers la page de login en cas d'échec
        }
    }
    
    @GetMapping("/render_insertPret")
    public String showInsertPretForm(Model model) {
        // Récupérer la liste des exemplaires avec leur livre associé
        List<Exemplaire> exemplaires = exemplaireService.getAllExemplairesAvecLivre();
        // Récupérer la liste des adhérents
        List<Adherant> adherants = adherantService.findAll();
        List<TypePret> typePrets = typePretService.getAllTypePrets();

        model.addAttribute("exemplaires", exemplaires);
        model.addAttribute("adherants", adherants);
        model.addAttribute("types", typePrets);

        return "Adherant/insert_pret";
    }
    @PostMapping("/insert_pret")
    public String insertPret(@RequestParam("id_exemplaire") int idExemplaire,
                             @RequestParam("id_type") int idType,
                             @RequestParam("date_debut") String dateDebutStr,
                             @RequestParam("date_fin") String dateFinStr,
                             Model model,
                             HttpSession session) {
        try {
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
            
            Exemplaire exemplaire = exemplaireService.findById(idExemplaire).orElse(null);
            if (exemplaire == null) {
                model.addAttribute("erreur", "Exemplaire non trouvé.");
                return "Adherant/insert_pret";
            }
            
            TypePret typePret = typePretService.findById(idType).orElse(null);
            if (typePret == null) {
                model.addAttribute("erreur", "Type de prêt non trouvé.");
                return "Adherant/insert_pret";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDebut = sdf.parse(dateDebutStr);
            Date dateFin = sdf.parse(dateFinStr);
            
            // Validation des dates
            if (dateDebut.after(dateFin)) {
                model.addAttribute("erreur", "La date de début ne peut pas être après la date de fin.");
                return "Adherant/insert_pret";
            }

            Pret pret = new Pret();
            pret.setAdherant(adherant);
            pret.setExemplaire(exemplaire);
            pret.setTypePret(typePret);
            pret.setDateDebut(dateDebut);
            pret.setDateFin(dateFin);
            pret.setRendu(0); // Par défaut non rendu
            pret.setDate_rendu(null); // Pas de date de rendu au moment de l'insertion

            boolean success = pretService.insererPretSiQuota(adherant, pret);

            if (success) {
                model.addAttribute("message", "Le prêt a été inséré avec succès !");
            } else {
                model.addAttribute("erreur", "Impossible d'insérer le prêt : quota atteint, pénalité ou exemplaire indisponible.");
            }
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de l'insertion du prêt : " + e.getMessage());
            e.printStackTrace(); // Pour le débogage
        }

        // Toujours renvoyer les listes pour le formulaire
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
        // Récupérer la liste des exemplaires avec leur livre associé
        List<Exemplaire> exemplaires = exemplaireService.getAllExemplairesAvecLivre();
        model.addAttribute("exemplaires", exemplaires);
        
        // Récupérer les réservations de l'adhérent connecté
        Integer adherantId = (Integer) session.getAttribute("adherantId");
        if (adherantId != null) {
            List<Reservation> reservations = reservationService.findByAdherantId(adherantId);
            model.addAttribute("reservations", reservations);
        }
        
        return "Adherant/Reservation";
    }

    @PostMapping("/insert_reservation")
    public String insertReservation(@RequestParam("id_exemplaire") int idExemplaire,
                                   @RequestParam("date_debut_pret") String dateDebutPretStr,
                                   @RequestParam("date_fin_pret") String dateFinPretStr,
                                   Model model,
                                   HttpSession session) {
        try {
            Integer adherantId = (Integer) session.getAttribute("adherantId");
            if (adherantId == null) {
                model.addAttribute("erreur", "Vous devez être connecté pour effectuer une réservation.");
                return "Adherant/Reservation";
            }
            Adherant adherant = adherantService.findById(adherantId).orElse(null);
            Exemplaire exemplaire = exemplaireService.findById(idExemplaire).orElse(null);

            // Trouver le status "en attent" (ou "en attente")
            Status status = statusService.findAll().stream()
                .filter(s -> s.getNomStatus().equalsIgnoreCase("en attent") || s.getNomStatus().equalsIgnoreCase("En attente"))
                .findFirst()
                .orElse(null);

            if (status == null) {
                model.addAttribute("erreur", "Le status 'en attent' n'existe pas.");
                model.addAttribute("exemplaires", exemplaireService.getAllExemplairesAvecLivre());
                return "Adherant/Reservation";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDebutPret = sdf.parse(dateDebutPretStr);
            Date dateFinPret = sdf.parse(dateFinPretStr);

            Reservation reservation = new Reservation();
            reservation.setAdherant(adherant);
            reservation.setExemplaire(exemplaire);
            reservation.setStatus(status);
            reservation.setDateReservation(new Date()); // maintenant
            reservation.setDateDebutPret(dateDebutPret);
            reservation.setDateFinPret(dateFinPret);

            reservationService.save(reservation);

            model.addAttribute("message", "Réservation effectuée avec succès !");
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de la réservation.");
        }

        model.addAttribute("exemplaires", exemplaireService.getAllExemplairesAvecLivre());
        return "Adherant/Reservation";
    }
    // Ancienne version commentée :
    /*
    @PostMapping("/prolonger")
    String prolongerPret(@RequestParam("pretId") int pretId,
                        @RequestParam("dateProlongement") String dateProlongement,
                        Model model,
                        HttpSession session) {
        try {
            if(pretId != 0) {
                // Récupérer l'adhérent connecté
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateProlongementStr = sdf.parse(dateProlongement);
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
            } else {
                model.addAttribute("erreur", "Prêt non trouvé.");
            }
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de la demande de prolongement : " + e.getMessage());
            e.printStackTrace();
        }
        if(pretId !=0){
            Pret pretList = pretService.findPretById(pretId);
            Adherant adherant=pretList.getAdherant();
            int id =adherant.getIdAdherent();
            if(id !=0){
                List<Pret> prets =pretService.findByAdherantId(id);
                model.addAttribute("prets", prets);
            }
        }
        return "Adherant/pret_list";
    }
    */
    // Nouvelle version avec toutes les vérifications :
    @PostMapping("/prolonger")
    public String prolongerPret(@RequestParam("pretId") int pretId,
                               @RequestParam("dateProlongement") String dateProlongement,
                               Model model,
                               HttpSession session) {
        try {
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
            // Vérifier si le prêt est déjà rendu
            if (pret.getRendu() == 1) {
                model.addAttribute("erreur", "Ce prêt est déjà rendu, impossible de prolonger.");
                return "Adherant/pret_list";
            }
            // Vérifier s'il existe déjà une demande de prolongement en attente pour ce prêt
            List<Prolongement> prolongements = prolongementService.findAll();
            boolean dejaEnAttente = prolongements.stream().anyMatch(p -> p.getPret().getIdPret() == pretId && (p.getStatus().getNomStatus().equalsIgnoreCase("en attent") || p.getStatus().getNomStatus().equalsIgnoreCase("En attente")));
            if (dejaEnAttente) {
                model.addAttribute("erreur", "Une demande de prolongement est déjà en attente pour ce prêt.");
                return "Adherant/pret_list";
            }
            // Vérifier qu'aucune réservation n'existe sur l'exemplaire pour la période demandée
            List<Reservation> reservations = reservationService.findByExemplaireId(pret.getExemplaire().getIdExemplaire());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateProlongementStr = sdf.parse(dateProlongement);
            boolean reservationEnConflit = reservations.stream().anyMatch(r -> r.getDateDebutPret() != null && r.getDateFinPret() != null &&
                ((dateProlongementStr.after(r.getDateDebutPret()) && dateProlongementStr.before(r.getDateFinPret())) || dateProlongementStr.equals(r.getDateDebutPret()) || dateProlongementStr.equals(r.getDateFinPret())) &&
                (r.getStatus().getNomStatus().equalsIgnoreCase("confirmee") || r.getStatus().getNomStatus().equalsIgnoreCase("Confirmée"))
            );
            if (reservationEnConflit) {
                model.addAttribute("erreur", "Impossible de prolonger : une réservation existe sur cet exemplaire à la date demandée.");
                return "Adherant/pret_list";
            }
            // Vérifier que la nouvelle date est après la date de fin actuelle
            if (!dateProlongementStr.after(pret.getDateFin())) {
                model.addAttribute("erreur", "La date de prolongement doit être après la date de fin actuelle.");
                return "Adherant/pret_list";
            }
            // Créer la demande de prolongement
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
            // Chercher le status "annulee" ou "Annulée"
            Status statusAnnulee = statusService.findAll().stream()
                .filter(s -> s.getNomStatus().equalsIgnoreCase("annulee") || s.getNomStatus().equalsIgnoreCase("Annulée"))
                .findFirst()
                .orElse(null);
            if (statusAnnulee != null) {
                reservation.setStatus(statusAnnulee);
                reservationService.save(reservation);
                model.addAttribute("message", "Réservation annulée avec succès.");
            } else {
                model.addAttribute("erreur", "Le status 'annulée' n'existe pas.");
            }
        }
        // Rafraîchir la liste des réservations
        List<Reservation> reservations = reservationService.findByAdherantId(adherantId);
        model.addAttribute("reservations", reservations);
        return "Adherant/reservation_list";
    }
}
