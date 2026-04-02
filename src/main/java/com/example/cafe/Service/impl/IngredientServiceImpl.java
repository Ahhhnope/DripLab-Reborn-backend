package com.example.cafe.Service.impl;

import com.example.cafe.DTO.IngredientDTO;
import com.example.cafe.Entity.Drink.Ingredient.*;
import com.example.cafe.Entity.Drink.Topping;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.Drink.Ingredient.CoffeeBeanRepository;
import com.example.cafe.Repository.Drink.Ingredient.HeavyCreamRepository;
import com.example.cafe.Repository.Drink.Ingredient.IceCreamRepository;
import com.example.cafe.Repository.Drink.Ingredient.MilkRepository;
import com.example.cafe.Repository.Drink.ToppingRepository;
import com.example.cafe.Service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final CoffeeBeanRepository coffeeBeanRepository;
    private final MilkRepository milkRepository;
    private final HeavyCreamRepository heavyCreamRepository;
    private final IceCreamRepository iceCreamRepository;
    private final ToppingRepository toppingRepository;

    private final ModelMapper modelMapper;

    private JpaRepository<?, Integer> getRepo(String type) {
        switch (type) {
            case "coffee-beans": return coffeeBeanRepository;
            case "milks": return milkRepository;
            case "heavy-creams": return heavyCreamRepository;
            case "ice-creams": return iceCreamRepository;
            case "toppings": return toppingRepository;
            default: throw new CustomResourceNotFound("Không tìm thất repo của: "+type);
        }
    }

    private Class<? extends BaseIngredient> getEntityClass(String type) {
        return switch (type) {
            case "coffee-beans" -> CoffeeBean.class;
            case "milk" -> Milk.class;
            case "creams" -> HeavyCream.class;
            case "ice-creams" -> IceCream.class;
            case "toppings" -> Topping.class;
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }

    @Override
    public List<IngredientDTO> getAllIngredients(String type) {
        return getRepo(type).findAll().stream().map(item -> modelMapper.map(item, IngredientDTO.class)).toList();
    }

    @Override
    public IngredientDTO getIngredientById(String type, int id) {
        BaseIngredient ingredient = (BaseIngredient) getRepo(type).findById(id).orElseThrow(() -> new CustomResourceNotFound("Không tìm thấy "+type+", id: "+id));
        return modelMapper.map(ingredient, IngredientDTO.class);
    }


    @Transactional
    @Override
    public IngredientDTO addIngredient(String type, IngredientDTO ingredientDTO) {
        var repo = (JpaRepository<BaseIngredient, Integer>) getRepo(type);

        BaseIngredient in = modelMapper.map(ingredientDTO, getEntityClass(type));
        BaseIngredient saved = repo.save(in);

        return modelMapper.map(saved, IngredientDTO.class);
    }

    @Transactional
    @Override
    public IngredientDTO updateIngredient(String type, Integer id, IngredientDTO ingredientDTO) {
        var repo = (JpaRepository<BaseIngredient, Integer>) getRepo(type);
        BaseIngredient temp = repo.findById(id).orElseThrow(() -> new CustomResourceNotFound(type + " not found with id: " + id));

        modelMapper.map(ingredientDTO, temp);
        temp.setId(id);

        BaseIngredient saved = repo.save(temp);
        return modelMapper.map(saved, IngredientDTO.class);
    }

    @Transactional
    @Override
    public void deleteIngredient(String type, int id) {
        getRepo(type).deleteById(id);
    }
}
