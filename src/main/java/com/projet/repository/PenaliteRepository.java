package com.projet.repository;

import com.projet.entity.Penalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {
    
}
