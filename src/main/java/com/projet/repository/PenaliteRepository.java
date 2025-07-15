package com.projet.repository;
import java.util.*;

import com.projet.entity.Adherant;
import com.projet.entity.Penalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {
    List<Penalite> findByAdherantAndDebutPenaliteLessThanEqualAndFinPenaliteGreaterThanEqual(
        Adherant adherant, Date dateDebut, Date dateFin
    );
    @Query("SELECT p FROM Penalite p WHERE p.adherant.idAdherent = :adherantId")
    List<Penalite> findByAdherantId(@Param("adherantId") int adherantId);
}
    

