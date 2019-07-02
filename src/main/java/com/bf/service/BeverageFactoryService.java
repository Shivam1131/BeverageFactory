package com.bf.service;

import com.bf.constants.AppData;
import com.bf.dto.RequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Sadashiv Kadam
 */
@Service
public class BeverageFactoryService implements FactoryService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * Method to ensure that the order is valid
     *
     * @param requestBean contains order query
     * @return is order vaid or not as boolean value
     */
    @Override
    public Map<String, Float> computePrice(RequestBean requestBean) {
        //store Item as key and ingredients as values
        Map<String, List<String>> itemAndIngredientsMap = AppData.getItemIngredientMap();
        //store name as key and price as value
        Map<String, Float> menuMap = AppData.getMenuMap();
        //store name of ingredient as key and price as value
        Map<String, Float> ingredientsMap = AppData.getIngredientMap();

        Map<String, Float> priceMap = new HashMap<>();
        Float totalPrice = 0.00F;

        for (String str : requestBean.getQuery()) {
            logger.info("Processing order : {} " + str);
            float price = 0.00F;
            List<String> menu = Arrays.asList(str.split(","))
                    .stream().map(String::trim)
                    .map(x -> x.replace("-", ""))
                    .collect(Collectors.toList());
            //varifying order query
            if (isValidOrder(itemAndIngredientsMap, priceMap, menu)) {
                return priceMap;
            }
            //Checking all ingredient are excluded or not
            if (!menu.containsAll(itemAndIngredientsMap.get(menu.get(0).toLowerCase()))) {
                for (int i = 0; i < menu.size(); i++) {
                    if (i == 0) {
                        price = menuMap.get(menu.get(0).toLowerCase());
                    } else {
                        price -= ingredientsMap.get(menu.get(i).replace("-", "").trim());
                    }
                }
                totalPrice += price;
                priceMap.put(str, price);
            } else {
                //Handling invalid request
                priceMap.put("error", 0.0F);
                return priceMap;
            }
        }
        priceMap.put("totalPrice", totalPrice);
        return priceMap;
    }

    /**
     * Method to ensure that the order is valid
     *
     * @param itemAndIngredientsMap,priceMap
     * @return is order vaid or not as boolean value
     */
    private boolean isValidOrder(Map<String, List<String>> itemAndIngredientsMap, Map<String, Float> priceMap, List<String> menu) {
        boolean isValid = false;
        if (null != itemAndIngredientsMap.get(menu.get(0).toLowerCase())) {
            List productIngredient = itemAndIngredientsMap.get(menu.get(0).toLowerCase())
                    .stream().map(x -> x.replaceAll("-", ""))
                    .collect(Collectors.toList());
            if (menu.size() > 1 && !productIngredient.containsAll(menu.subList(1, menu.size()))) {
                priceMap.put("invalid", 0.0F);
                isValid = true;
            }
        }
        return isValid;
    }
}