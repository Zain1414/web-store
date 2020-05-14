package com.web.store.junit;

import com.web.store.beans.Invoice;
import com.web.store.controller.StoreController;
import com.web.store.exception.NotFoundException;
import com.web.store.service.StoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    @Test
    void whenValidInputThenReturns200() throws Exception {

        Mockito.lenient().when(storeService.billUserCart(101)).thenReturn(new Invoice());

        mockMvc.perform(get("/store/bill-cart/{id}",101)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void whenInValidInputThenReturns404() throws Exception {

        Mockito.lenient().when(storeService.billUserCart(102)).thenThrow(new NotFoundException(""));

        mockMvc.perform(get("/store/bill-cart/{id}",102)
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

}