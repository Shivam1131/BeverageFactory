package com.bf.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sadashiv Kadam
 */
public class MenuDetails {

    //Items
    private static final String CHAI = "chai";
    private static final String BANANA_SMOOTHIE = "banana smoothie";
    private static final String COFFEE = "coffee";
    private static final String STRAWBERRY_SHAKE = "strawberry shake";
    private static final String MOJITO = "mojito";

    //Ingredeients
    private static final String SUGAR = "sugar";
    private static final String WATER = "water";
    private static final String MILK = "milk";
    private static final String BANANA = "sugar";
    private static final String SODA = "soda";
    private static final String MINT = "mint";


    private MenuDetails() {
        super();
    }

    public static Map<String, List<String>> getItemIngredientMap() {
        Map<String, List<String>> itemAndIngredientsMap = new HashMap<>();
        itemAndIngredientsMap.put(COFFEE, Arrays.asList(MILK, SUGAR, WATER));
        itemAndIngredientsMap.put(CHAI, Arrays.asList(MILK, SUGAR, WATER));
        itemAndIngredientsMap.put(BANANA_SMOOTHIE, Arrays.asList(BANANA, MILK, SUGAR, WATER));
        itemAndIngredientsMap.put(STRAWBERRY_SHAKE, Arrays.asList(MILK, SUGAR, WATER));
        itemAndIngredientsMap.put(MOJITO, Arrays.asList(SUGAR, WATER, SODA, MINT));
        return itemAndIngredientsMap;
    }

    public static Map<String, Float> getMenuMap() {
        Map<String, Float> menuMap = new HashMap<>();
        menuMap.put(COFFEE, 5F);
        menuMap.put(CHAI, 4F);
        menuMap.put(BANANA_SMOOTHIE, 6F);
        menuMap.put(STRAWBERRY_SHAKE, 7F);
        menuMap.put(MOJITO, 7.5F);
        return menuMap;
    }

    public static Map<String, Float> getIngredientMap() {
        Map<String, Float> ingredientsMap = new HashMap<>();
        ingredientsMap.put(MILK, 1F);
        ingredientsMap.put(SUGAR, 0.5F);
        ingredientsMap.put(SODA, 0.5F);
        ingredientsMap.put(MINT, 0.5F);
        ingredientsMap.put(WATER, 0.5F);
        return ingredientsMap;
    }
}
