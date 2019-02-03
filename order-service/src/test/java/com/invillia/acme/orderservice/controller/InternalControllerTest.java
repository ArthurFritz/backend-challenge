package com.invillia.acme.orderservice.controller;

import com.invillia.acme.orderservice.OrderServiceApplication;
import com.invillia.acme.orderservice.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class InternalControllerTest {

    protected MockMvc mvc;

    private String uriResume = "/internal/resume/idOrder";

    private String uriUpdate = "/internal/update/idOrder/NEW";

    @Autowired
    private InternalController internalController;

    @MockBean
    private OrderService orderService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_updateStatus() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriUpdate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void should_updateStatusInvalid() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriUpdate+"Invalid")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_getResume() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uriResume)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}