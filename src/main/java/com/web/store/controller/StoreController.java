package com.web.store.controller;

import com.web.store.beans.Invoice;
import com.web.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping(value = "/bill-cart/{id}")
    @ResponseBody
    public Invoice getBillAmount(@PathVariable("id") Integer cartId) {

        storeService.billUserCart(cartId);

        return null;
    }
}
