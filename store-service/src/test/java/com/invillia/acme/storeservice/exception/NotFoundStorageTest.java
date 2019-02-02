package com.invillia.acme.storeservice.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class NotFoundStorageTest {

    @Test
    public void validateMessageException(){
        NotFoundStorage notFoundStorage = new NotFoundStorage("1234");
        assertEquals("Not found storage id 1234" , notFoundStorage.getMessage());
    }

}