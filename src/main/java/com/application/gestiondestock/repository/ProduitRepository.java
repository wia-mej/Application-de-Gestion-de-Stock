package com.application.gestiondestock.repository;

import com.application.gestiondestock.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {

    Optional<Produit> findProduitByCodeProduit(String codeProduit);

    List<Produit> findAllByCategoryId(Integer idCategory);

}
