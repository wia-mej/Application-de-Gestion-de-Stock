package com.application.gestiondestock.services;

import com.application.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.application.gestiondestock.dto.MvmStockDto;
import com.application.gestiondestock.dto.UtilisateurDto;

import java.math.BigDecimal;
import java.util.List;

public interface UtilisateurService {
    UtilisateurDto save(UtilisateurDto dto);

    UtilisateurDto findById(Integer id);

    List<UtilisateurDto> findAll();

    void delete(Integer id);

    UtilisateurDto findByEmail(String email);

    UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);
}
