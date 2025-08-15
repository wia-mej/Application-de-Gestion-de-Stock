package com.application.gestiondestock.repository;

import com.application.gestiondestock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Integer> {

    List<LigneVente> findAllByProduitId(Integer idProduit);

    List<LigneVente> findAllByVenteId(Integer id);
}
