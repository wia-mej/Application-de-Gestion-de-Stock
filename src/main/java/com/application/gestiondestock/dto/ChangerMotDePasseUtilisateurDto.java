package com.application.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class ChangerMotDePasseUtilisateurDto {

    private Integer id;

    private String motDePasse;

    private String confirmMotDePasse;

}
