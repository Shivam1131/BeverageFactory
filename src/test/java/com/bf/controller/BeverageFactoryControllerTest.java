package com.bf.controller;

import com.bf.dto.RequestBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BeverageFactoryControllerTest {

    @Autowired
    private BeverageFactoryController factoryController;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(factoryController).build();
    }

    @Test
    public void successForValidOrder() throws Exception {
        RequestBean requestBean = new RequestBean();
        requestBean.setQuery(Arrays.asList("Chai, -sugar", "Chai", "Coffee, -water,-sugar", "Mojito,-sugar,-mint,-soda"));
        String jsonStr = objectMapper.writeValueAsString(requestBean);
        MvcResult result = mockMvc.perform(post("/api/computePrice").contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().isOk()).andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertTrue(result.getResponse().getContentAsString().contains("Order processed Successfully."));
    }

    @Test
    public void failureForEmptyOrder() throws Exception {
        RequestBean requestBean = new RequestBean();
        String jsonStr = objectMapper.writeValueAsString(requestBean);
        MvcResult result = mockMvc.perform(post("/api/computePrice").contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andReturn();
        Assert.assertTrue(result.getResponse().getContentAsString().contains("Query string should not be empty"));
    }

    @Test
    public void failureForAllExclusions() throws Exception {
        RequestBean requestBean = new RequestBean();
        requestBean.setQuery(Arrays.asList("Chai, -sugar,-water,-milk", "Chai", "Coffee, -water,-sugar", "Mojito,-sugar,-mint,-soda"));
        String jsonStr = objectMapper.writeValueAsString(requestBean);
        MvcResult result = mockMvc.perform(post("/api/computePrice").contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andReturn();
        Assert.assertTrue(result.getResponse().getContentAsString().contains("Cannot exclude all the ingredient"));
    }

    @Test
    public void invalidQueryParameter() throws Exception {
        RequestBean requestBean = new RequestBean();
        requestBean.setQuery(Arrays.asList("Chai, -sugar,-water,-milk,-test"));
        String jsonStr = objectMapper.writeValueAsString(requestBean);
        MvcResult result = mockMvc.perform(post("/api/computePrice").contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andReturn();
        Assert.assertTrue(result.getResponse().getContentAsString().contains("Invalid query parameter"));
    }
}
