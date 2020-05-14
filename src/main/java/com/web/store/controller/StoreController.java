package com.web.store.controller;

import com.web.store.beans.Invoice;
import com.web.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mohammad Zain
 * @since 14/05/2020
 */
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    /**
     * @param cartId user cart id
     * @return Invoice generated for the user cart
     */
    @GetMapping(value = "/bill-cart/{id}")
    @ResponseBody
    public Invoice getBillAmount(@PathVariable("id") Integer cartId) {

        return storeService.billUserCart(cartId);

    }
}
