package com.example.cafe.Controller;

import com.example.cafe.Entity.Drink.Drink;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.Drink.DrinkRepository;
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
        return new ResponseEntity<>(drinkRepository.findAllByActiveTrue(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drink> getDrinkById(@PathVariable int id) {
        return new ResponseEntity<>(drinkRepository.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Drink> addDrink(@RequestBody Drink drink) {
        drink.setActive(true);
        Drink savedDrink = drinkRepository.save(drink);
        return new ResponseEntity<>(savedDrink, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Drink> updateDrink(@PathVariable Integer id, @RequestBody Drink drink) {
        drink.setId(id);
        return new ResponseEntity<>(drinkRepository.save(drink), HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{id}")
    public void deleteDrink(@PathVariable Integer id) {
        Drink drink = drinkRepository.findById(id).orElseThrow(() -> new CustomResourceNotFound("No drink found: " + id));
        drink.setActive(false);
        drinkRepository.save(drink);
    }
}
