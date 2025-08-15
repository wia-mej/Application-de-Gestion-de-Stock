package com.application.gestiondestock.services;

import com.application.gestiondestock.dto.Categorydto;
import java.util.List;

public interface CategoryService {

    Categorydto save(Categorydto dto);

    Categorydto findById(Integer id);

    Categorydto findByCode(String code);

    List<Categorydto> findAll();

    void delete(Integer id);
}
