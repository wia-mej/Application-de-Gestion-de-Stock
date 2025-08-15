package com.application.gestiondestock.services.impl;

import com.application.gestiondestock.dto.LigneVenteDto;
import com.application.gestiondestock.dto.MvmStockDto;
import com.application.gestiondestock.dto.ProduitDto;
import com.application.gestiondestock.dto.VenteDto;
import com.application.gestiondestock.exception.EntityNotFoundException;
import com.application.gestiondestock.exception.ErrorCodes;
import com.application.gestiondestock.exception.InvalidEntityException;
import com.application.gestiondestock.exception.InvalidOperationException;
import com.application.gestiondestock.model.*;
import com.application.gestiondestock.repository.LigneVenteRepository;
import com.application.gestiondestock.repository.ProduitRepository;
import com.application.gestiondestock.repository.VenteRepository;
import com.application.gestiondestock.services.MvmStockService;
import com.application.gestiondestock.services.VenteService;
import com.application.gestiondestock.validateur.VenteValidateur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VenteServiceImpl implements VenteService {


    private ProduitRepository produitRepository;
    private VenteRepository ventesRepository;
    private LigneVenteRepository ligneVenteRepository;
    private MvmStockService mvtStkService;

    @Autowired
    public VenteServiceImpl(ProduitRepository produitRepository, VenteRepository ventesRepository,
                             LigneVenteRepository ligneVenteRepository, MvmStockService mvtStkService) {
        this.produitRepository = produitRepository;
        this.ventesRepository = ventesRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public VenteDto save(VenteDto dto) {
        List<String> errors = VenteValidateur.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Ventes n'est pas valide");
            throw new InvalidEntityException("L'objet vente n'est pas valide", ErrorCodes.VENTE_NOT_VALID, errors);
        }

        List<String> produitErrors = new ArrayList<>();

        dto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Produit> produit = produitRepository.findById(ligneVenteDto.getProduit().getId());
            if (produit.isEmpty()) {
                produitErrors.add("Aucun produit avec l'ID " + ligneVenteDto.getProduit().getId() + " n'a ete trouve dans la BDD");
            }
        });

        if (!produitErrors.isEmpty()) {
            log.error("One or more produits were not found in the DB, {}", errors);
            throw new InvalidEntityException("Un ou plusieurs produits n'ont pas ete trouve dans la BDD", ErrorCodes.VENTE_NOT_VALID, errors);
        }

        Vente savedVentes = ventesRepository.save(VenteDto.toEntity(dto));

        dto.getLigneVentes().forEach(ligneVenteDto -> {
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVente(savedVentes);
            ligneVenteRepository.save(ligneVente);
            updateMvtStk(ligneVente);
        });

        return VenteDto.fromEntity(savedVentes);
    }

    @Override
    public VenteDto findById(Integer id) {
        if (id == null) {
            log.error("Ventes ID is NULL");
            return null;
        }
        return ventesRepository.findById(id)
                .map(VenteDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun vente n'a ete trouve dans la BDD", ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public VenteDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Vente CODE is NULL");
            return null;
        }
        return ventesRepository.findVentesByCode(code)
                .map(VenteDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune vente client n'a ete trouve avec le CODE " + code, ErrorCodes.VENTE_NOT_VALID
                ));
    }

    @Override
    public List<VenteDto> findAll() {
        return ventesRepository.findAll().stream()
                .map(VenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Vente ID is NULL");
            return;
        }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVenteId(id);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une vente ...",
                    ErrorCodes.VENTE_ALREADY_IN_USE);
        }
        ventesRepository.deleteById(id);
    }

    private void updateMvtStk(LigneVente lig) {
        MvmStockDto mvtStkDto = MvmStockDto.builder()
                .produit(ProduitDto.fromEntity(lig.getProduit()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStock.SORTIE)
                .sourceMvt(SourceMvtStk.VENTE)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.sortieStock(mvtStkDto);
    }
}
