package com.web.store.service;

import com.web.store.beans.Invoice;
import com.web.store.beans.Products;
import com.web.store.beans.UserCart;
import com.web.store.beans.Users;
import com.web.store.exception.NotFoundException;
import com.web.store.repository.InvoiceRepository;
import com.web.store.repository.UserCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

/**
 * @author Mohammad Zain
 * @since 14/05/2020
 */
@Service
public class StoreService {

    @Autowired
    private UserCartRepository cartRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    /**
     * @param cartId user cart id
     * @return Invoice generated for the user cart
     */
    public Invoice billUserCart(Integer cartId){

        Optional<UserCart> cart = cartRepository.findById(cartId);

        if(!cart.isPresent()){
            throw new NotFoundException(String.format("Cart with Id [%s] not found",cartId));
        }

        // Discounts applies to grocery items
        double totalWithoutGrocery = cart.get().getProducts()
                .stream()
                .filter(p -> !Products.Category.GROCERIES.equals(p.getType()))
                .mapToDouble(Products::getPrice)
                .sum();

        // Discounts doesn't applies to grocery items
        double totalWithGrocery = cart.get().getProducts()
                .stream()
                .filter(p -> Products.Category.GROCERIES.equals(p.getType()))
                .mapToDouble(Products::getPrice)
                .sum();

        // Without discount amount
        double grossAmount = totalWithGrocery + totalWithoutGrocery;

        // Get discount percentage based on user type
        double discountedPercentage = getDiscountRate(cart.get().getUser());

        // Amount after discount applied
        double netAmount = (totalWithoutGrocery - (totalWithoutGrocery * discountedPercentage)) + totalWithGrocery;

        // If no discount applicable, than need to apply discount based on the bill multiple of 100$
        if(discountedPercentage == 0.0){
            int discount = (int) netAmount/100;
            netAmount = netAmount - (discount*5);
        }

        Invoice invoice = new Invoice();
        invoice.setUserCart(cart.get());
        invoice.setDiscountedPercentage(discountedPercentage);
        invoice.setGrossAmount(grossAmount);
        invoice.setNetAmount(netAmount);
        invoice.setInvoicedDate(LocalDateTime.now());

        return invoice;
    }

    /**
     * @param user store user
     * @return discount rate particular to user category
     */
    private double getDiscountRate(Users user){

        if(Users.UserType.EMPLOYEE.equals(user.getType()))
            return 0.3;

        if(Users.UserType.AFFILIATED.equals(user.getType()))
            return 0.1;

        if(Users.UserType.CUSTOMER.equals(user.getType())){
            LocalDate today = LocalDate.now();

            // Check if user has been registered customer for over 2 years
            if(user.getRegistrationDate().isBefore(ChronoLocalDate.from(today.minusYears(2))))
                return 0.05;
        }

        return 0.0;
    }
}
