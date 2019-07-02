package com.bf.service;

import com.bf.dto.RequestBean;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BeverageFactoryService {

    public List<Float> computePrice(RequestBean requestBean){

        Map<String,Float> menuMap=new HashMap<>();
        menuMap.put("coffee",5F);
        menuMap.put("chai",4F);
        menuMap.put("banana smoothie",6F);
        menuMap.put("strawberry shake",7F);


        Map<String,Float> ingMap=new HashMap<>();
        ingMap.put("milk",1F);
        ingMap.put("sugar",0.5F);
        ingMap.put("soda",0.5F);
        ingMap.put("mint",0.5F);
        ingMap.put("water",0.5F);

        List<Float> priceList = new ArrayList();

        for (String str:requestBean.getQuery()) {

            float price = 0.00F;
            List<String> menu =Arrays.asList(str.split(","));
            for (int i = 0; i<menu.size();i++) {
                if (i==0){
                    price=menuMap.get(menu.get(i).toLowerCase());
                }else{
                    price-=ingMap.get(menu.get(i).replace("-","").trim());
                }
            }
            System.out.println("Price for '"+str+"' : "+price);

        }

        return priceList;
    }
}
