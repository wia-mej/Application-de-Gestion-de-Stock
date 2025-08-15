package com.application.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "utilisateur")
@Builder
public class Utilisateur extends AbstractEntity implements UserDetails {

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "datedenaissance")
    private Instant dateDeNaissance;

    @Column(name = "motdepasse")
    private String moteDePasse;

    @Embedded
    private Adresse adresse;

    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "identreprise")
    private Entreprise entreprise;

    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.EAGER)
    private List<Roles> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convertir la liste des rôles en une collection de GrantedAuthority
        return roles.stream()
            .map(role -> (GrantedAuthority) role::getRoleNom)
            .toList();
    }

    @Override
    public String getPassword() {
        return moteDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Vous pouvez personnaliser cette logique
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Vous pouvez personnaliser cette logique
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Vous pouvez personnaliser cette logique
    }

    @Override
    public boolean isEnabled() {
        return true; // Vous pouvez personnaliser cette logique
    }
}