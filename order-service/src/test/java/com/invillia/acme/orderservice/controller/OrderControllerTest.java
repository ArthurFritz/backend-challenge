package com.invillia.acme.orderservice.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.acme.orderservice.OrderServiceApplication;
import com.invillia.acme.orderservice.dto.OrderDTO;
import com.invillia.acme.orderservice.dto.OrderItemDTO;
import com.invillia.acme.orderservice.enums.StatusOrder;
import com.invillia.acme.orderservice.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class)
@WebAppConfiguration
public class OrderControllerTest {

    protected MockMvc mvc;

    private String uri = "/order";

    @Autowired
    WebApplicationContext webApplicationContext;

    @InjectMocks
    OrderController orderController;

    @Mock
    private OrderService orderService;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void should_createOrder() throws Exception {
        String inputJson = "{ " +
        "        \"confirmationDate\":\"2016-01-25T21:34:55\", " +
        "        \"statusOrder\":\"NEW\", " +
        "        \"address\":\"endereço\", " +
        "        \"items\": [ " +
        "{ " +
        "    \"quantity\":12, " +
        "        \"description\":\"product\", " +
        "        \"unitPrice\":0.50 " +
        "}" +
        "]}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void should_createOrderInvalidConfimationDate() throws Exception {
        String inputJson = "{ " +
                "        \"statusOrder\":\"NEW\", " +
                "        \"address\":\"endereço\", " +
                "        \"items\": [ " +
                "{ " +
                "    \"quantity\":12, " +
                "        \"description\":\"product\", " +
                "        \"unitPrice\":0.50 " +
                "}" +
                "]}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_createOrderInvalidStatusOrder() throws Exception {
        String inputJson = "{ " +
                "        \"confirmationDate\":\"2016-01-25T21:34:55\", " +
                "        \"address\":\"endereço\", " +
                "        \"items\": [ " +
                "{ " +
                "    \"quantity\":12, " +
                "        \"description\":\"product\", " +
                "        \"unitPrice\":0.50 " +
                "}" +
                "]}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_createOrderInvalidAddress() throws Exception {
        String inputJson = "{ " +
                "        \"confirmationDate\":\"2016-01-25T21:34:55\", " +
                "        \"statusOrder\":\"NEW\", " +
                "        \"items\": [ " +
                "{ " +
                "    \"quantity\":12, " +
                "        \"description\":\"product\", " +
                "        \"unitPrice\":0.50 " +
                "}" +
                "]}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_createOrderInvalidQuantity() throws Exception {
        String inputJson = "{ " +
                "        \"confirmationDate\":\"2016-01-25T21:34:55\", " +
                "        \"statusOrder\":\"NEW\", " +
                "        \"address\":\"endereço\", " +
                "        \"items\": [ " +
                "{ " +
                "        \"description\":\"product\", " +
                "        \"unitPrice\":0.50 " +
                "}" +
                "]}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_createOrderInvalidDescriptionItem() throws Exception {
        String inputJson = "{ " +
                "        \"confirmationDate\":\"2016-01-25T21:34:55\", " +
                "        \"statusOrder\":\"NEW\", " +
                "        \"address\":\"endereço\", " +
                "        \"items\": [ " +
                "{ " +
                "    \"quantity\":12, " +
                "        \"unitPrice\":0.50 " +
                "}" +
                "]}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_createOrderInvalidUnitPrice() throws Exception {
        String inputJson = "{ " +
                "        \"confirmationDate\":\"2016-01-25T21:34:55\", " +
                "        \"statusOrder\":\"NEW\", " +
                "        \"address\":\"endereço\", " +
                "        \"items\": [ " +
                "{ " +
                "    \"quantity\":12, " +
                "        \"description\":\"product\", " +
                "}" +
                "]}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_createOrderNoItems() throws Exception {
        String inputJson = "{ " +
                "        \"confirmationDate\":\"2016-01-25T21:34:55\", " +
                "        \"statusOrder\":\"NEW\", " +
                "        \"address\":\"endereço\", " +
                "        \"items\": [ ]}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
}