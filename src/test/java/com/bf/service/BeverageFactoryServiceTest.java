package com.bf.service;

import com.bf.dto.RequestBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Map;

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
        Map<String, Float> priceMap = new BeverageFactoryService().computePrice(requestBean);
        Assert.assertTrue(!CollectionUtils.isEmpty(priceMap));
    }

    @Test
    public void failureTestWithInvalidData() {
        RequestBean requestBean = new RequestBean();
        requestBean.setQuery(Arrays.asList("Chai, -sugar,-test"));
        Map<String, Float> priceMap = new BeverageFactoryService().computePrice(requestBean);
        Assert.assertTrue(priceMap.containsKey("invalid"));
    }

}
