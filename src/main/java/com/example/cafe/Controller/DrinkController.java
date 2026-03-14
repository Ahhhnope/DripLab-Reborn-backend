package com.example.cafe.Controller;

import com.example.cafe.Entity.Drink;
import com.example.cafe.Repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drinks")
public class DrinkController {

    private final DrinkRepository drinkRepository;

    @GetMapping
    public ResponseEntity<List<Drink>> getAllDrink() {
        return new ResponseEntity<>(drinkRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public Drink addDrink(@RequestBody Drink drink) {
        return drinkRepository.save(drink);
    }

    @PutMapping("/{id}")
    public Drink updateDrink(@PathVariable Integer id, @RequestBody Drink drink) {
        drink.setId(id);
        return drinkRepository.save(drink);
    }

    @DeleteMapping("/{id}")
    public void deleteDrink(@PathVariable Integer id) {
        drinkRepository.deleteById(id);
    }
}
