package com.bf.service;

import com.bf.dto.RequestBean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BeverageFactoryService {

    public Map<String,Float> computePrice(RequestBean requestBean){

        Map<String,List<String>> itemAndIngredientsMap = new HashMap<>();
        itemAndIngredientsMap.put("coffee",Arrays.asList(new String[]{"milk", "sugar","water"}));
        itemAndIngredientsMap.put("chai",Arrays.asList(new String[]{"milk", "sugar","water"}));
        itemAndIngredientsMap.put("banana smoothie",Arrays.asList(new String[]{"banana", "milk", "sugar","water"}));
        itemAndIngredientsMap.put("strawberry shake",Arrays.asList(new String[]{"Strawberries", "milk", "sugar","water"}));

        Map<String,Float> menuMap=new HashMap<>();
        menuMap.put("coffee",5F);
        menuMap.put("chai",4F);
        menuMap.put("banana smoothie",6F);
        menuMap.put("strawberry shake",7F);

        Map<String,Float> ingredientsMap=new HashMap<>();
        ingredientsMap.put("milk",1F);
        ingredientsMap.put("sugar",0.5F);
        ingredientsMap.put("soda",0.5F);
        ingredientsMap.put("mint",0.5F);
        ingredientsMap.put("water",0.5F);

        Map<String,Float> priceMap = new HashMap<>();
        Float totalPrice = 0.00F;

        for (String str:requestBean.getQuery()) {
            float price = 0.00F;
            System.out.println("str : "+str);

            List<String> menu =Arrays.asList(str.split(",")).stream().map(x->x.replace("-","")).collect(Collectors.toList());
            System.out.println("Map : "+menu);
            boolean isValid =  validateIngredients(menu,itemAndIngredientsMap.get(menu.get(0).toLowerCase()));
            if(isValid){
                for (int i = 0; i<menu.size();i++) {
                    if (i==0){
                        price=menuMap.get(menu.get(0).toLowerCase());
                    }else{
                        price-=ingredientsMap.get(menu.get(i).replace("-","").trim());
                    }
                }
                totalPrice+=price;
                priceMap.put(str,price);
                System.out.println("Price for '"+str+"' : "+price);
            }else {
                //Handling invalid request
                priceMap.put("error",0.0F);
                return priceMap;
            }
        }
        priceMap.put("totalPrice",totalPrice);
        return priceMap;
    }

    public boolean validateIngredients(List<String> menu, List<String> ingedients){
       //boolean isvalid = false;
        return !ingedients.containsAll(menu);
        /*if (!ingedients.containsAll(menu)){
            isvalid=true;
        }*/
        /*return isvalid;*/
    }
}
