package com.application.gestiondestock.services.impl;

import com.application.gestiondestock.dto.Categorydto;
import com.application.gestiondestock.exception.EntityNotFoundException;
import com.application.gestiondestock.exception.ErrorCodes;
import com.application.gestiondestock.exception.InvalidEntityException;
import com.application.gestiondestock.exception.InvalidOperationException;
import com.application.gestiondestock.model.Produit;
import com.application.gestiondestock.repository.CategoryRepository;
import com.application.gestiondestock.repository.ProduitRepository;
import com.application.gestiondestock.services.CategoryService;
import com.application.gestiondestock.validateur.CategoryValidateur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ProduitRepository produitRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ProduitRepository produitRepository) {
        this.categoryRepository = categoryRepository;
        this.produitRepository = produitRepository;
    }

    @Override
    public Categorydto save(Categorydto dto) {
        List<String> errors = CategoryValidateur.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Produit is not valid {}", dto);
            throw new InvalidEntityException("La category n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        return Categorydto.fromEntity(
                categoryRepository.save(Categorydto.toEntity(dto))
        );
    }

    @Override
    public Categorydto findById(Integer id) {
        if (id == null) {
            log.error("Category ID is null");
            return null;
        }
        return categoryRepository.findById(id)
                .map(Categorydto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune category avec l'ID = " + id + " n'a ete trouve dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
                );
    }

    @Override
    public Categorydto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Category CODE is null");
            return null;
        }
        return categoryRepository.findCategoryByCode(code)
                .map(Categorydto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune category avec le CODE = " + code + " n' ete trouve dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
                );
    }

    @Override
    public List<Categorydto> findAll() {
        return categoryRepository.findAll().stream()
                .map(Categorydto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Category ID is null");
            return;
        }
        List<Produit> produits = produitRepository.findAllByCategoryId(id);
        if (!produits.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer cette categorie qui est deja utilise",
                    ErrorCodes.CATEGORY_ALREADY_IN_USE);
        }
        categoryRepository.deleteById(id);
    }
}
