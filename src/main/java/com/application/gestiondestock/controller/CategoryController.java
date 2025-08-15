package com.application.gestiondestock.controller;

import com.application.gestiondestock.controller.api.CategoryApi;
import com.application.gestiondestock.dto.Categorydto;
import com.application.gestiondestock.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Categorydto save(Categorydto dto) {
        return categoryService.save(dto);
    }

    @Override
    public Categorydto findById(Integer idCategory) {
        return categoryService.findById(idCategory);
    }

    @Override
    public Categorydto findByCode(String codeCategory) {
        return categoryService.findByCode(codeCategory);
    }

    @Override
    public List<Categorydto> findAll() {
        return categoryService.findAll();
    }

    @Override
    public void delete(Integer id) {
        categoryService.delete(id);
    }
}