package com.application.gestiondestock.services;

import com.application.gestiondestock.dto.MvmStockDto;

import java.math.BigDecimal;
import java.util.List;

public interface MvmStockService {

    BigDecimal stockReelProduit(Integer idProduit);

    List<MvmStockDto> mvtStockProduit(Integer idProduit);

    MvmStockDto entreeStock(MvmStockDto dto);

    MvmStockDto sortieStock(MvmStockDto dto);

    MvmStockDto correctionStockPos(MvmStockDto dto);

    MvmStockDto correctionStockNeg(MvmStockDto dto);
}
