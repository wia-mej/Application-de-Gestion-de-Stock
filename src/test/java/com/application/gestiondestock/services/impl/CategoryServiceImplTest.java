package com.application.gestiondestock.services.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.application.gestiondestock.dto.Categorydto;
import com.application.gestiondestock.exception.EntityNotFoundException;
import com.application.gestiondestock.exception.ErrorCodes;
import com.application.gestiondestock.exception.InvalidEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl service;

    @Test
    public void shouldSaveCategoryWithSuccess() {
        Categorydto expectedCategory = Categorydto.builder()
                .code("Cat test")
                .designation("Designation test")
                .idEntreprise(1)
                .build();

        Categorydto savedCategory = service.save(expectedCategory);

        assertNotNull(savedCategory);
        assertNotNull(savedCategory.getId());
        assertEquals(expectedCategory.getCode(), savedCategory.getCode());
        assertEquals(expectedCategory.getDesignation(), savedCategory.getDesignation());
        assertEquals(expectedCategory.getIdEntreprise(), savedCategory.getIdEntreprise());

    }

    @Test
    public void shouldUpdateCategoryWithSuccess() {
        Categorydto expectedCategory = Categorydto.builder()
                .code("Cat test")
                .designation("Designation test")
                .idEntreprise(1)
                .build();

        Categorydto savedCategory = service.save(expectedCategory);

        Categorydto categoryToUpdate = savedCategory;
        categoryToUpdate.setCode("Cat update");

        savedCategory = service.save(categoryToUpdate);

        assertNotNull(categoryToUpdate);
        assertNotNull(categoryToUpdate.getId());
        assertEquals(categoryToUpdate.getCode(), savedCategory.getCode());
        assertEquals(categoryToUpdate.getDesignation(), savedCategory.getDesignation());
        assertEquals(categoryToUpdate.getIdEntreprise(), savedCategory.getIdEntreprise());
    }

    @Test
    public void shouldThrowInvalidEntityException() {
        Categorydto expectedCategory = Categorydto.builder().build();

        InvalidEntityException expectedException = assertThrows(InvalidEntityException.class, () -> service.save(expectedCategory));

        assertEquals(ErrorCodes.CATEGORY_NOT_VALID, expectedException.getErrorCode());
        assertEquals(1, expectedException.getErrors().size());
        assertEquals("Veuillez renseigner le code de la categorie", expectedException.getErrors().get(0));
    }

    @Test
    public void shouldThrowEntityNotFoundException() {
        EntityNotFoundException expectedException = assertThrows(EntityNotFoundException.class, () -> service.findById(0));

        assertEquals(ErrorCodes.CATEGORY_NOT_FOUND, expectedException.getErrorCode());
        assertEquals("Aucune category avec l'ID = 0 n'a ete trouve dans la BDD", expectedException.getMessage());
    }

    @Test
    void shouldThrowEntityNotFoundException2() {
        // Vérifie qu'une exception de type EntityNotFoundException est levée
        assertThrows(EntityNotFoundException.class, () -> {
            service.findById(0); // Appelle la méthode testée
        });
    }


}