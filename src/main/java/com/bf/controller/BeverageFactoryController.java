package com.bf.controller;

import com.bf.constants.AppConstants;
import com.bf.dto.RequestBean;
import com.bf.dto.ResponseBean;
import com.bf.service.BeverageFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/")
public class BeverageFactoryController {

    @Autowired
    BeverageFactoryService service;

    @PostMapping("computePrice")
    public ResponseEntity<ResponseBean> computePriceOfAnOrder(@RequestBody RequestBean requestBean) {
        ResponseBean responseBean = new ResponseBean();

        if (StringUtils.isEmpty(requestBean.getQuery())) {
            responseBean.setMessage(AppConstants.ORDER_PROCESSED_FAILURE);
            responseBean.setHttpStatus(HttpStatus.BAD_REQUEST);
        } else {
            Map<String, Float> priceMap = service.computePrice(requestBean);
            if (priceMap.containsKey("error")) {
                responseBean.setMessage(AppConstants.CANNOT_EXCLUDE_ALL_INGREDIENT);
                responseBean.setHttpStatus(HttpStatus.BAD_REQUEST);
            }else  if (priceMap.containsKey("invalid")){
                responseBean.setMessage(AppConstants.INVALID_QUERY_PARAMETER);
                responseBean.setHttpStatus(HttpStatus.BAD_REQUEST);
            }else {
                responseBean.setTotalCost(String.valueOf(priceMap.remove("totalPrice")));
                responseBean.setIndividualPrices(priceMap);
                responseBean.setMessage(AppConstants.ORDER_PROCESSED_SUCCESS);
                responseBean.setHttpStatus(HttpStatus.OK);
            }

        }

        return new ResponseEntity<>(responseBean, responseBean.getHttpStatus());
    }

}
