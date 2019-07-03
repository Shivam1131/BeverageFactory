package com.bf.service;

import com.bf.constants.AppConstants;
import com.bf.dto.RequestBean;
import com.bf.dto.ServiceResponseBean;
import com.bf.serviceimpl.BeverageFactoryServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author Sadashiv Kadam
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BeverageFactoryServiceTest {

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void successTestWithValidData() {
        RequestBean requestBean = new RequestBean();
        requestBean.setQuery(Arrays.asList("Chai, -sugar", "Chai", "Coffee, -water,-sugar", "Mojito,-sugar,-mint,-soda"));
        ServiceResponseBean responseBean = new BeverageFactoryServiceImpl().computePrice(requestBean);
        Assert.assertTrue(responseBean.getMessage().equals(AppConstants.ORDER_PROCESSED_SUCCESS));
        Assert.assertTrue(responseBean.getStatus().equals(AppConstants.SUCCESS));
        Assert.assertTrue(!StringUtils.isEmpty(responseBean.getTotalCost()));
    }

    @Test
    public void failureTestWithInvalidData() {
        RequestBean requestBean = new RequestBean();
        requestBean.setQuery(Arrays.asList("Chai, -sugar,-test"));
        ServiceResponseBean responseBean = new BeverageFactoryServiceImpl().computePrice(requestBean);
        Assert.assertTrue(responseBean.getMessage().equals("Invalid ingredient found in the order."));
        Assert.assertTrue(responseBean.getHttpStatus().equals(HttpStatus.NOT_ACCEPTABLE));
    }

}
