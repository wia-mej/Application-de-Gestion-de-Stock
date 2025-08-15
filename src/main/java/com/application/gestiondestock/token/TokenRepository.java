package com.application.gestiondestock.token;

import com.application.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    /**
     * Récupère tous les tokens valides (non expirés et non révoqués) associés à un utilisateur donné.
     * 
     * @param id Identifiant de l'utilisateur
     * @return Liste des tokens valides
     */
    @Query(value = """
        select t from Token t inner join Utilisateur u
        on t.utilisateur.id = u.id
        where u.id = :id and (t.expired = false or t.revoked = false)
        """)
    List<Token> findAllValidTokenByUser(Integer id);

    /**
     * Recherche un token par sa valeur unique.
     * 
     * @param token Valeur du token
     * @return Optional contenant le token s'il existe
     */
    Optional<Token> findByToken(String token);
}