package com.application.gestiondestock.repository;

import com.application.gestiondestock.model.MvmStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MvmStockRepository extends JpaRepository<MvmStock, Integer> {

    @Query("select sum(m.quantite) from MvmStock m where m.produit.id = :idProduit")
    BigDecimal stockReelProduit(@Param("idProduit") Integer idProduit);

    List<MvmStock> findAllByProduitId(Integer idProduit);
}
