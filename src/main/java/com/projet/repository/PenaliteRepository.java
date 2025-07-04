package com.projet.repository;
import java.util.*;

import com.projet.entity.Adherant;
import com.projet.entity.Penalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {
    List<Penalite> findByAdherantAndDebutPenaliteLessThanEqualAndFinPenaliteGreaterThanEqual(
        Adherant adherant, Date dateDebut, Date dateFin
    );
}
    

