package com.application.gestiondestock.model;

import com.application.gestiondestock.auth.Permission;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "roles")
@Builder
public class Roles extends AbstractEntity {

    @Column(name = "rolenom", nullable = false, unique = true)
    private String roleNom;

    @ManyToOne
    @JoinColumn(name = "idutilisateur",referencedColumnName = "id", nullable = false)
    private Utilisateur utilisateur;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private List<Permission> permissions;
}