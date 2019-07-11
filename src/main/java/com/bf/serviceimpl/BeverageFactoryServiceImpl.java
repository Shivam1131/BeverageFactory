package com.bf.serviceimpl;

import com.bf.configuration.MenuDetails;
import com.bf.constants.AppConstants;
import com.bf.dto.RequestBean;
import com.bf.dto.ServiceResponseBean;
import com.bf.exception.CannotExcludeAllIngredientsException;
import com.bf.exception.IllegalIngredientException;
import com.bf.service.FactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
public class BeverageFactoryServiceImpl implements FactoryService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * Method to ensure that the order is valid
     *
     * @param requestBean contains order query
     * @return is order vaid or not as boolean value
     */
    @Override
    public ServiceResponseBean computePrice(RequestBean requestBean) {

        ServiceResponseBean responseBean = new ServiceResponseBean();

        //store Item as key and ingredients as values
        Map<String, List<String>> itemAndIngredientsMap = MenuDetails.getItemIngredientMap();
        //store name as key and price as value
        Map<String, Float> menuMap = MenuDetails.getMenuMap();
        //store name of ingredient as key and price as value
        Map<String, Float> ingredientsMap = MenuDetails.getIngredientMap();

        Map<String, Float> priceMap = new HashMap<>();
        Float totalPrice = 0.00F;

        try {
            for (String str : requestBean.getQuery()) {

                logger.info("Processing order : {} ", str);
                List<String> menu = Arrays.asList(str.split(","))
                        .stream().map(String::trim)
                        .map(x -> x.replace("-", ""))
                        .collect(Collectors.toList());

                if (!menuMap.containsKey(menu.get(0).toLowerCase())){
                    if (ingredientsMap.containsKey(menu.get(0)))
                        throw new IllegalIngredientException("Ingredients can only be ordered with menu Item."+ menu.get(0));
                    throw new IllegalIngredientException("Invalid menu item found in the order: " + menu.get(0));
                }

                //varifying order query string
                if (isValidOrder(itemAndIngredientsMap, menu)) {
                    if (!menu.containsAll(itemAndIngredientsMap.get(menu.get(0).toLowerCase()))) {
                        float price = menuMap.get(menu.get(0).toLowerCase());
                        for (int i = 1; i < menu.size(); i++) {
                            price -= ingredientsMap.get(menu.get(i));
                        }
                        totalPrice += price;
                        priceMap.put(str, price);
                    } else {
                        throw new CannotExcludeAllIngredientsException("Cannot exclude all the ingredients in the order");
                    }
                }
            }
            responseBean.setTotalCost(String.valueOf(totalPrice));
            responseBean.setHttpStatus(HttpStatus.OK);
            responseBean.setMessage(AppConstants.ORDER_PROCESSED_SUCCESS);
            responseBean.setIndividualPrices(priceMap);
            responseBean.setStatus(AppConstants.SUCCESS);

        } catch (CannotExcludeAllIngredientsException e) {
            responseBean.setHttpStatus(HttpStatus.BAD_REQUEST);
            responseBean.setMessage(e.getMessage());
        } catch (IllegalIngredientException e) {
            responseBean.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
            responseBean.setMessage(e.getMessage());
        }
        return responseBean;
    }

    /**
     * Method to ensure that the order is valid
     *
     * @param itemAndIngredientsMap,priceMap
     * @return is order vaid or not as boolean value
     */
    private boolean isValidOrder(Map<String, List<String>> itemAndIngredientsMap, List<String> menu) {
        boolean isValid = false;
        if (null != itemAndIngredientsMap.get(menu.get(0).toLowerCase())) {
            List productIngredient = itemAndIngredientsMap.get(menu.get(0).toLowerCase());

            if (menu.size() > 1 && !productIngredient.containsAll(menu.subList(1, menu.size()))) {
                throw new IllegalIngredientException("Invalid ingredient found in the order.");
            }
            isValid = true;
        }
        return isValid;
    }
}