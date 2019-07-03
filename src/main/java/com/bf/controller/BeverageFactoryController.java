package com.bf.controller;

import com.bf.constants.AppConstants;
import com.bf.dto.RequestBean;
import com.bf.dto.ServiceResponseBean;
import com.bf.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sadashiv Kadam
 */
@RestController
@RequestMapping("/api/")
public class BeverageFactoryController {

    @Autowired
    @Qualifier("beverageFactoryServiceImpl")
    private FactoryService service;

    /**
     * @param requestBean contains order query
     * @return ServiceResponseBean along with order details
     * @apiNote API to compute cost of an order
     */
    @PostMapping("computePrice")
    public ResponseEntity<ServiceResponseBean> computePriceOfAnOrder(@RequestBody RequestBean requestBean) {
        ServiceResponseBean responseBean = new ServiceResponseBean();
        try {
            if (!StringUtils.isEmpty(requestBean.getQuery())) {
                responseBean = service.computePrice(requestBean);
            } else {
                responseBean.setStatus(AppConstants.FAILURE);
                responseBean.setMessage(AppConstants.ORDER_PROCESSED_FAILURE);
                responseBean.setHttpStatus(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseBean.setStatus(AppConstants.FAILURE);
            responseBean.setMessage(AppConstants.ORDER_PROCESSED_FAILURE);
            responseBean.setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseBean, responseBean.getHttpStatus());
    }
}
