package com.bf.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sadashiv Kadam
 */
public class AppData {

    private AppData() {
        super();
    }

    public static Map<String, List<String>> getItemIngredientMap() {
        Map<String, List<String>> itemAndIngredientsMap = new HashMap<>();
        itemAndIngredientsMap.put("coffee", Arrays.asList("milk", AppConstants.SUGAR, AppConstants.WATER));
        itemAndIngredientsMap.put("chai", Arrays.asList("milk", AppConstants.SUGAR, AppConstants.WATER));
        itemAndIngredientsMap.put("banana smoothie", Arrays.asList("banana", "milk", AppConstants.SUGAR, AppConstants.WATER));
        itemAndIngredientsMap.put("strawberry shake", Arrays.asList("milk", AppConstants.SUGAR, AppConstants.WATER));
        itemAndIngredientsMap.put("mojito", Arrays.asList(AppConstants.SUGAR, AppConstants.WATER, "soda", "mint"));
        return itemAndIngredientsMap;
    }

    public static Map<String, Float> getMenuMap() {
        Map<String, Float> menuMap = new HashMap<>();
        menuMap.put("coffee", 5F);
        menuMap.put("chai", 4F);
        menuMap.put("banana smoothie", 6F);
        menuMap.put("strawberry shake", 7F);
        menuMap.put("mojito", 7.5F);
        return menuMap;
    }

    public static Map<String, Float> getIngredientMap() {
        Map<String, Float> ingredientsMap = new HashMap<>();
        ingredientsMap.put("milk", 1F);
        ingredientsMap.put(AppConstants.SUGAR, 0.5F);
        ingredientsMap.put("soda", 0.5F);
        ingredientsMap.put("mint", 0.5F);
        ingredientsMap.put(AppConstants.WATER, 0.5F);
        return ingredientsMap;
    }
}
