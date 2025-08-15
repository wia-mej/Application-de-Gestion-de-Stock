package com.application.gestiondestock.dto;

import com.application.gestiondestock.model.MvmStock;
import com.application.gestiondestock.model.SourceMvtStk;
import com.application.gestiondestock.model.TypeMvtStock;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@Builder
public class MvmStockDto {

    private Integer id;

    private Instant dateMvt;

    private BigDecimal quantite;

    private ProduitDto produit;

    private TypeMvtStock typeMvt;

    private SourceMvtStk sourceMvt;

    private Integer idEntreprise;

    public static MvmStockDto fromEntity(MvmStock mvtStk) {
        if (mvtStk == null) {
            return null;
        }

        return MvmStockDto.builder()
                .id(mvtStk.getId())
                .dateMvt(mvtStk.getDateMvt())
                .quantite(mvtStk.getQuantite())
                .produit(ProduitDto.fromEntity(mvtStk.getProduit()))
                .typeMvt(mvtStk.getTypeMvt())
                .sourceMvt(mvtStk.getSourceMvt())
                .idEntreprise(mvtStk.getIdEntreprise())
                .build();
    }

    public static MvmStock toEntity(MvmStockDto dto) {
        if (dto == null) {
            return null;
        }

        MvmStock mvtStk = new MvmStock();
        mvtStk.setId(dto.getId());
        mvtStk.setDateMvt(dto.getDateMvt());
        mvtStk.setQuantite(dto.getQuantite());
        mvtStk.setProduit(ProduitDto.toEntity(dto.getProduit()));
        mvtStk.setTypeMvt(dto.getTypeMvt());
        mvtStk.setSourceMvt(dto.getSourceMvt());
        mvtStk.setIdEntreprise(dto.getIdEntreprise());
        return mvtStk;
    }
}
