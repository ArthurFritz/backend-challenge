package com.invillia.acme.paymentservice.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.acme.paymentservice.dto.PaymentDTO;
import com.invillia.acme.paymentservice.dto.RefundDTO;
import com.invillia.acme.paymentservice.service.PaymentService;
import com.invillia.acme.paymentservice.service.RefundService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PaymentControllerTest {

    private static final String ORDER_ID = "orderId";
    private static final String PAYMENT_INFO = "paymentInfo";
    private static final String NUMBER_CARD = "numberCard";

    protected MockMvc mvc;

    private String uriPayment = "/payment";

    private String uriRefund = "/payment/refund";

    @Autowired
    private PaymentController paymentController;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private RefundService refundService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_payOrder() throws Exception {
        String inputJson = mapToJson(PaymentDTO.builder().creditCardNumber(NUMBER_CARD).order(ORDER_ID).build());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uriPayment)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void should_payOrderInvalidOrder() throws Exception {
        String inputJson = mapToJson(PaymentDTO.builder().creditCardNumber(NUMBER_CARD).build());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uriPayment)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_payOrderInvalidCardNumber() throws Exception {
        String inputJson = mapToJson(PaymentDTO.builder().order(ORDER_ID).build());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uriPayment)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }


    @Test
    public void should_refundOrder() throws Exception {
        String inputJson = mapToJson(RefundDTO.builder().payment(PAYMENT_INFO).order(ORDER_ID).build());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriRefund)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void should_refundOrderInvalidOrder() throws Exception {
        String inputJson = mapToJson(RefundDTO.builder().payment(PAYMENT_INFO).build());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriRefund)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_refundOrderInvalidPayment() throws Exception {
        String inputJson = mapToJson(RefundDTO.builder().order(ORDER_ID).build());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriRefund)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }


    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    private <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}