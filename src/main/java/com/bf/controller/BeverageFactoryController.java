package com.bf.controller;

import com.bf.dto.RequestBean;
import com.bf.dto.ResponseBean;
import com.bf.service.BeverageFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class BeverageFactoryController {

    @Autowired
    BeverageFactoryService service;

    @PostMapping("computePrice")
    public ResponseEntity<ResponseBean> computePriceOfAnOrder(@RequestBody RequestBean requestBean){
        ResponseBean responseBean = new ResponseBean();

        System.out.println("Query : "+requestBean.getQuery());

        List<Float> priceList  = service.computePrice(requestBean);
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }

}
