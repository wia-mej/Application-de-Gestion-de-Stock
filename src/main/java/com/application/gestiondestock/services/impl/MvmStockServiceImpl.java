package com.application.gestiondestock.services.impl;

import com.application.gestiondestock.dto.MvmStockDto;
import com.application.gestiondestock.exception.InvalidEntityException;
import com.application.gestiondestock.model.TypeMvtStock;
import com.application.gestiondestock.repository.MvmStockRepository;
import com.application.gestiondestock.services.MvmStockService;
import com.application.gestiondestock.services.ProduitService;
import com.application.gestiondestock.validateur.MvmStockValidateur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.gestiondestock.exception.ErrorCodes;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class MvmStockServiceImpl implements MvmStockService {

    private MvmStockRepository repository;
    private ProduitService produitService;

    @Autowired
    public MvmStockServiceImpl(MvmStockRepository repository, ProduitService produitService) {
        this.repository = repository;
        this.produitService = produitService;
    }

    @Override
    public BigDecimal stockReelProduit(Integer idProduit) {
        if (idProduit == null) {
            log.warn("ID produit is NULL");
            return BigDecimal.valueOf(-1);
        }
        produitService.findById(idProduit);
        return repository.stockReelProduit(idProduit);
    }

    @Override
    public List<MvmStockDto> mvtStockProduit(Integer idProduit) {
        return repository.findAllByProduitId(idProduit).stream()
                .map(MvmStockDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MvmStockDto entreeStock(MvmStockDto dto) {
        return entreePositive(dto, TypeMvtStock.ENTREE);
    }

    @Override
    public MvmStockDto sortieStock(MvmStockDto dto) {
        return sortieNegative(dto, TypeMvtStock.SORTIE);
    }

    @Override
    public MvmStockDto correctionStockPos(MvmStockDto dto) {
        return entreePositive(dto, TypeMvtStock.CORRECTION_POS);
    }

    @Override
    public MvmStockDto correctionStockNeg(MvmStockDto dto) {
        return sortieNegative(dto, TypeMvtStock.CORRECTION_NEG);
    }

    private MvmStockDto entreePositive(MvmStockDto dto, TypeMvtStock TypeMvtStock) {
        List<String> errors = MvmStockValidateur.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Produit is not valid {}", dto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MVT_STOCK_NOT_VALID, errors);
        }
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue())
                )
        );
        dto.setTypeMvt(TypeMvtStock);
        return MvmStockDto.fromEntity(
                repository.save(MvmStockDto.toEntity(dto))
        );
    }

    private MvmStockDto sortieNegative(MvmStockDto dto, TypeMvtStock TypeMvtStock) {
        List<String> errors = MvmStockValidateur.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Produit is not valid {}", dto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MVT_STOCK_NOT_VALID, errors);
        }
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue()) * -1
                )
        );
        dto.setTypeMvt(TypeMvtStock);
        return MvmStockDto.fromEntity(
                repository.save(MvmStockDto.toEntity(dto))
        );
    }
}
