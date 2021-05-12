package com.example.demo.data;

import com.example.demo.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}
