package com.projet.repository;


import com.projet.entity.Adherant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projet.entity.Pret;

import java.util.List;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
   
}