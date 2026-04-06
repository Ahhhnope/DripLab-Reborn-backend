package com.example.cafe.Controller;

import com.example.cafe.DTO.DrinkDTO;
import com.example.cafe.Entity.Drink.Drink;
import com.example.cafe.Entity.Drink.Ingredient.CoffeeBean;
import com.example.cafe.Entity.Drink.Ingredient.HeavyCream;
import com.example.cafe.Entity.Drink.Ingredient.IceCream;
import com.example.cafe.Entity.Drink.Ingredient.Milk;
import com.example.cafe.Entity.Drink.Instruction;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.Drink.DrinkRepository;
import com.example.cafe.Repository.Drink.Ingredient.CoffeeBeanRepository;
import com.example.cafe.Repository.Drink.Ingredient.HeavyCreamRepository;
import com.example.cafe.Repository.Drink.Ingredient.IceCreamRepository;
import com.example.cafe.Repository.Drink.Ingredient.MilkRepository;
import com.example.cafe.Repository.Drink.InstructionRepository;
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
    private final CoffeeBeanRepository coffeeBeanRepository;
    private final MilkRepository milkRepository;
    private final HeavyCreamRepository heavyCreamRepository;
    private final IceCreamRepository iceCreamRepository;
    private final InstructionRepository instructionRepository;

    @GetMapping
    public ResponseEntity<List<Drink>> getAllDrink() {
        for (Drink drink : drinkRepository.findAllByActiveTrue()) {
            System.out.println(drink.getName());
        }
        return new ResponseEntity<>(drinkRepository.findAllByActiveTrue(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drink> getDrinkById(@PathVariable int id) {
        return drinkRepository.findById(id)
                .map(drink -> new ResponseEntity<>(drink, HttpStatus.OK))
                .orElseThrow(() -> new CustomResourceNotFound("Drink not found id: " + id));
    }

    @PostMapping("/add")
    public ResponseEntity<Drink> addDrink(@RequestBody DrinkDTO dto) {
        Drink drink = mapDtoToDrink(new Drink(), dto);
        drink.setActive(true);
        return new ResponseEntity<>(drinkRepository.save(drink), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Drink> updateDrink(@PathVariable Integer id, @RequestBody DrinkDTO dto) {
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(() -> new CustomResourceNotFound("Drink not found id: " + id));
        mapDtoToDrink(drink, dto);
        return new ResponseEntity<>(drinkRepository.save(drink), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public void deleteDrink(@PathVariable Integer id) {
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(() -> new CustomResourceNotFound("No drink found: " + id));
        drink.setActive(false);
        drinkRepository.save(drink);
    }

    // ---- Helper: map DTO → Drink entity ----
    private Drink mapDtoToDrink(Drink drink, DrinkDTO dto) {
        drink.setName(dto.getName());
        drink.setCategory(dto.getCategory());
        drink.setBasePrice(dto.getBasePrice() != null ? dto.getBasePrice().floatValue() : null);
        drink.setDescription(dto.getDescription());
        drink.setImageUrl(dto.getImageUrl());

        drink.setCoffeeBean(dto.getCoffeeBeanId() != null
                ? coffeeBeanRepository.findById(dto.getCoffeeBeanId()).orElse(null) : null);
        drink.setMilk(dto.getMilkId() != null
                ? milkRepository.findById(dto.getMilkId()).orElse(null) : null);
        drink.setHeavyCream(dto.getHeavyCreamId() != null
                ? heavyCreamRepository.findById(dto.getHeavyCreamId()).orElse(null) : null);
        drink.setIceCream(dto.getIceCreamId() != null
                ? iceCreamRepository.findById(dto.getIceCreamId()).orElse(null) : null);
        drink.setInstruction(dto.getInstructionId() != null
                ? instructionRepository.findById(dto.getInstructionId()).orElse(null) : null);

        return drink;
    }
}