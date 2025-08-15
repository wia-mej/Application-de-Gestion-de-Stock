package com.application.gestiondestock.services.impl;

import com.application.gestiondestock.dto.LigneCommandeClientDto;
import com.application.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.application.gestiondestock.dto.LigneVenteDto;
import com.application.gestiondestock.dto.ProduitDto;
import com.application.gestiondestock.exception.EntityNotFoundException;
import com.application.gestiondestock.exception.InvalidEntityException;
import com.application.gestiondestock.exception.InvalidOperationException;
import com.application.gestiondestock.model.LigneCommandeClient;
import com.application.gestiondestock.model.LigneCommandeFournisseur;
import com.application.gestiondestock.model.LigneVente;
import com.application.gestiondestock.repository.LigneCommandeClientRepository;
import com.application.gestiondestock.repository.LigneCommandeFournisseurRepository;
import com.application.gestiondestock.repository.LigneVenteRepository;
import com.application.gestiondestock.repository.ProduitRepository;
import com.application.gestiondestock.services.ProduitService;
import com.application.gestiondestock.validateur.ProduitValidateur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.gestiondestock.exception.ErrorCodes;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProduitServiceImpl implements ProduitService {

    private ProduitRepository produitRepository;
    private LigneVenteRepository venteRepository;
    private LigneCommandeFournisseurRepository commandeFournisseurRepository;
    private LigneCommandeClientRepository commandeClientRepository;

    @Autowired
    public ProduitServiceImpl(
            ProduitRepository ProduitRepository,
            LigneVenteRepository venteRepository, LigneCommandeFournisseurRepository commandeFournisseurRepository,
            LigneCommandeClientRepository commandeClientRepository) {
        this.produitRepository = ProduitRepository;
        this.venteRepository = venteRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.commandeClientRepository = commandeClientRepository;
    }

    @Override
    public ProduitDto save(ProduitDto dto) {
        List<String> errors = ProduitValidateur.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Produit is not valid {}", dto);
            throw new InvalidEntityException("L'produit n'est pas valide", ErrorCodes.PRODUIT_NOT_VALID, errors);
        }

        return ProduitDto.fromEntity(
                produitRepository.save(
                        ProduitDto.toEntity(dto)
                )
        );
    }

    @Override
    public ProduitDto findById(Integer id) {
        if (id == null) {
            log.error("Produit ID is null");
            return null;
        }

        return produitRepository.findById(id).map(ProduitDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun produit avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.PRODUIT_NOT_FOUND)
        );
    }

    @Override
    public ProduitDto findByCodeProduit(String codeProduit) {
        if (!StringUtils.hasLength(codeProduit)) {
            log.error("Produit CODE is null");
            return null;
        }

        return produitRepository.findProduitByCodeProduit(codeProduit)
                .map(ProduitDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucun produit avec le CODE = " + codeProduit + " n' ete trouve dans la BDD",
                                ErrorCodes.PRODUIT_NOT_FOUND)
                );
    }

    @Override
    public List<ProduitDto> findAll() {
        return produitRepository.findAll().stream()
                .map(ProduitDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idProduit) {
        return venteRepository.findAllByProduitId(idProduit).stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idProduit) {
        return commandeClientRepository.findAllByProduitId(idProduit).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idProduit) {
        return commandeFournisseurRepository.findAllByProduitId(idProduit).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProduitDto> findAllProduitByIdCategory(Integer idCategory) {
        return produitRepository.findAllByCategoryId(idCategory).stream()
                .map(ProduitDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            log.error("Produit ID is null");
            return;
        }
        List<LigneCommandeClient> ligneCommandeClients = commandeClientRepository.findAllByProduitId(id);
        if (!ligneCommandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un produit deja utilise dans des commandes client", ErrorCodes.PRODUIT_ALREADY_IN_USE);
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = commandeFournisseurRepository.findAllByProduitId(id);
        if (!ligneCommandeFournisseurs.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un produit deja utilise dans des commandes fournisseur",
                    ErrorCodes.PRODUIT_ALREADY_IN_USE);
        }
        List<LigneVente> ligneVentes = venteRepository.findAllByProduitId(id);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un produit deja utilise dans des ventes",
                    ErrorCodes.PRODUIT_ALREADY_IN_USE);
        }
        produitRepository.deleteById(id);
    }
}
