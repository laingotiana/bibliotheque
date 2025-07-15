package com.projet.service;

import com.projet.entity.Abonnement;
import com.projet.repository.AbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AbonnementService {

    @Autowired
    private AbonnementRepository abonnementRepository;

    public List<Abonnement> findByAdherantId(int idAdherent) {
        return abonnementRepository.findByAdherant_IdAdherent(idAdherent);
    }
}