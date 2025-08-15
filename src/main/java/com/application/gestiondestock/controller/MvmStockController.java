package com.application.gestiondestock.controller;

import com.application.gestiondestock.controller.api.MvmStockApi;
import com.application.gestiondestock.dto.MvmStockDto;
import com.application.gestiondestock.services.MvmStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MvmStockController implements MvmStockApi {

    private final MvmStockService service;

    @Autowired
    public MvmStockController(MvmStockService service) {
        this.service = service;
    }

    @Override
    public BigDecimal stockReelProduit(Integer idProduit) {
        return service.stockReelProduit(idProduit);
    }

    @Override
    public List<MvmStockDto> mvtStkProduit(Integer idProduit) {
        return service.mvtStockProduit(idProduit);
    }

    @Override
    public MvmStockDto entreeStock(MvmStockDto dto) {
        return service.entreeStock(dto);
    }

    @Override
    public MvmStockDto sortieStock(MvmStockDto dto) {
        return service.sortieStock(dto);
    }

    @Override
    public MvmStockDto correctionStockPos(MvmStockDto dto) {
        return service.correctionStockPos(dto);
    }

    @Override
    public MvmStockDto correctionStockNeg(MvmStockDto dto) {
        return service.correctionStockNeg(dto);
    }
}