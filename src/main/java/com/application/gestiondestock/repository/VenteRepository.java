package com.application.gestiondestock.repository;

import com.application.gestiondestock.model.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenteRepository extends JpaRepository<Vente, Integer> {

    Optional<Vente> findVentesByCode(String code);

}
