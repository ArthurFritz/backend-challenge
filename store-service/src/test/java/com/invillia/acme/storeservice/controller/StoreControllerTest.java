package com.invillia.acme.storeservice.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.acme.storeservice.StoreServiceApplication;
import com.invillia.acme.storeservice.dto.StoreDTO;
import com.invillia.acme.storeservice.entity.Store;
import com.invillia.acme.storeservice.exception.NotFoundStorage;
import com.invillia.acme.storeservice.service.StoreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StoreServiceApplication.class)
@WebAppConfiguration
public class StoreControllerTest {

    protected MockMvc mvc;

    private String uri = "/";
    private String uriUpdate = "/identifier";

    @Autowired
    WebApplicationContext webApplicationContext;

    @InjectMocks
    StoreController storeController;

    @Mock
    private StoreService storeService;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_createStore() throws Exception {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setAddress("address info");
        storeDTO.setName("name info");

        String inputJson = mapToJson(storeDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void should_NotCreateStoreNoName() throws Exception {
        StoreDTO storeDTO = new StoreDTO();

        String inputJson = mapToJson(storeDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_NotCreateStoreNoAddress() throws Exception {
        StoreDTO storeDTO = new StoreDTO();

        String inputJson = mapToJson(storeDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_NotCreateStoreNoAddressAndName() throws Exception {
        StoreDTO storeDTO = new StoreDTO();

        String inputJson = mapToJson(storeDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_updateStoreNotFund() throws Exception {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setAddress("address info");
        storeDTO.setName("name info");
        Mockito.doThrow(new NotFoundStorage("indentifier")).when(storeService).updateStore(any(),any());
        String inputJson = mapToJson(storeDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriUpdate)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    public void should_NotUpdateStoreNoName() throws Exception {
        StoreDTO storeDTO = new StoreDTO();

        String inputJson = mapToJson(storeDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriUpdate)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_NotUpdateStoreNoAddress() throws Exception {
        StoreDTO storeDTO = new StoreDTO();

        String inputJson = mapToJson(storeDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriUpdate)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_NotUpdateStoreNoAddressAndName() throws Exception {
        StoreDTO storeDTO = new StoreDTO();

        String inputJson = mapToJson(storeDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uriUpdate)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void should_getListStore() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
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