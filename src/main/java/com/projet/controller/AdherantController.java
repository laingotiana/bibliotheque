package com.projet.controller;
import com.projet.service.AdherantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.projet.entity.Adherant;




import jakarta.servlet.http.HttpSession;

@Controller
public class AdherantController {

    @Autowired
    private AdherantService adherantService;


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
   

}
