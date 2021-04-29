package com.example.demo;

import lombok.Data;

/**
 * 配方
 */
@Data
public class Ingredient {

    private final String id;

    private final String name;

    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
