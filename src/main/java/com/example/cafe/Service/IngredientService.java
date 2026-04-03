package com.example.cafe.Service;

import com.example.cafe.DTO.IngredientDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IngredientService {
    List<IngredientDTO> getAllIngredients(String type);
    IngredientDTO getIngredientById(String type, int id);
    IngredientDTO addIngredient(String type, IngredientDTO ingredientDTO);
    IngredientDTO updateIngredient(String type, Integer id, IngredientDTO ingredientDTO);
    void deleteIngredient(String type, int id);
}
