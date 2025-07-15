package com.projet.repository;

import com.projet.entity.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;



@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
   @Query("SELECT e FROM Exemplaire e JOIN FETCH e.livre")
    List<Exemplaire> findAllWithLivre();
    List<Exemplaire> findAllByOrderByIdExemplaireAsc();
    List<Exemplaire> findByLivreIdLivre(int idLivre);
}
