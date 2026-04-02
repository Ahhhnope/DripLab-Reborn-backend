package com.example.cafe.Controller;

import com.example.cafe.DTO.IngredientDTO;
import com.example.cafe.Service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping("/{type}")
    private ResponseEntity<?> getAll(@PathVariable String type) {
        return new ResponseEntity<>(ingredientService.getAllIngredients(type), HttpStatus.OK);
    }

    @GetMapping("/{type}/{id}")
    private ResponseEntity<?> findIngredientById(@PathVariable String type, @PathVariable Integer id) {
        return new ResponseEntity<>(ingredientService.getIngredientById(type, id), HttpStatus.OK);
    }

    @PostMapping("/add/{type}")
    public ResponseEntity<IngredientDTO> add(@PathVariable String type, @RequestBody IngredientDTO dto) {
        return new ResponseEntity<>(ingredientService.addIngredient(type, dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{type}/{id}")
    public ResponseEntity<IngredientDTO> update(
            @PathVariable String type,
            @PathVariable Integer id,
            @RequestBody IngredientDTO dto) {
        return new ResponseEntity<>(ingredientService.updateIngredient(type, id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{type}/{id}")
    public ResponseEntity<?> remove(@PathVariable String type, @PathVariable Integer id) {
        ingredientService.deleteIngredient(type, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
