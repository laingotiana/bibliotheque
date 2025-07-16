package com.projet.repository;

import com.projet.entity.JourFerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface JourFerieRepository extends JpaRepository<JourFerie, Integer> {
    Optional<JourFerie> findByDate(Date date);
}