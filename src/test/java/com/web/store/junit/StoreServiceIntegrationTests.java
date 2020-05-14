package com.web.store.junit;

import com.web.store.beans.Products;
import com.web.store.beans.UserCart;
import com.web.store.beans.Users;
import com.web.store.exception.NotFoundException;
import com.web.store.repository.UserCartRepository;
import com.web.store.service.StoreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StoreServiceIntegrationTests {

    @MockBean
    private UserCartRepository cartRepository;

    @Autowired
    private StoreService storeService;

    private Set<UserCart> userCarts;

    private Set<Products> products;

    @BeforeEach
    void init(){
        userCarts = new HashSet<>();
        products = new HashSet<>();
    }

    @Test
    public void testUserCartNotFound(){
        Assertions.assertThrows(NotFoundException.class, () -> storeService.billUserCart(123));
    }

    @Test
    @DisplayName("Test NetAmount User Employee (30%) Category")
    public void testUserIsEmployee(){

        Users user = Users.builder().name("Robert").type(Users.UserType.EMPLOYEE)
                .registrationDate(LocalDate.of(2019, 01, 22))
                .build();

        Optional<UserCart> cart = Optional.of(UserCart.builder().id(1).user(user).build());

        userCarts.add(cart.get());

        Optional<Products> product1 = Optional.of(new Products(1,"iphone",Products.Category.ELECTRONICS,450.00,true,userCarts));
        Optional<Products> product2 = Optional.of(new Products(2,"trouser",Products.Category.CLOTHING,50.00,true,userCarts));
        Optional<Products> product3 = Optional.of(new Products(3,"Sanitizer",Products.Category.GROCERIES,30.00,true,userCarts));

        products.add(product1.get());
        products.add(product2.get());
        products.add(product3.get());

        cart.get().setProducts(products);

        Mockito.lenient().when(cartRepository.findById(1)).thenReturn(cart);

        Assertions.assertEquals(380,storeService.billUserCart(cart.get().getId()).getNetAmount());
    }

    @Test
    @DisplayName("Test NetAmount User Affiliated (10%) Category")
    public void testUserIsAffiliated(){

        Users user = Users.builder().name("James").type(Users.UserType.AFFILIATED)
                .registrationDate(LocalDate.of(2019, 01, 22))
                .build();

        Optional<UserCart> cart = Optional.of(UserCart.builder().id(1).user(user).build());

        userCarts.add(cart.get());

        Optional<Products> product1 = Optional.of(new Products(1,"iphone",Products.Category.ELECTRONICS,450.00,true,userCarts));
        Optional<Products> product2 = Optional.of(new Products(2,"trouser",Products.Category.CLOTHING,50.00,true,userCarts));
        Optional<Products> product3 = Optional.of(new Products(3,"Sanitizer",Products.Category.GROCERIES,30.00,true,userCarts));

        products.add(product1.get());
        products.add(product2.get());
        products.add(product3.get());

        cart.get().setProducts(products);

        Mockito.lenient().when(cartRepository.findById(1)).thenReturn(cart);

        Assertions.assertEquals(480,storeService.billUserCart(cart.get().getId()).getNetAmount());
    }

    @Test
    @DisplayName("Test NetAmount User Loyal Customer (5%) Category")
    public void testUserIsLoyalCustomer(){

        Users user = Users.builder().name("Ahmed").type(Users.UserType.CUSTOMER)
                .registrationDate(LocalDate.of(2018, 01, 22))
                .build();

        Optional<UserCart> cart = Optional.of(UserCart.builder().id(1).user(user).build());

        userCarts.add(cart.get());

        Optional<Products> product1 = Optional.of(new Products(1,"iphone",Products.Category.ELECTRONICS,450.00,true,userCarts));
        Optional<Products> product2 = Optional.of(new Products(2,"trouser",Products.Category.CLOTHING,50.00,true,userCarts));
        Optional<Products> product3 = Optional.of(new Products(3,"Sanitizer",Products.Category.GROCERIES,30.00,true,userCarts));

        products.add(product1.get());
        products.add(product2.get());
        products.add(product3.get());

        cart.get().setProducts(products);

        Mockito.lenient().when(cartRepository.findById(1)).thenReturn(cart);

        Assertions.assertEquals(505,storeService.billUserCart(cart.get().getId()).getNetAmount());
    }

    @Test
    @DisplayName("Test NetAmount User Not Loyal Customer Category")
    public void testUserIsNotLoyalCustomer(){

        Users user = Users.builder().name("Zain").type(Users.UserType.CUSTOMER)
                .registrationDate(LocalDate.of(2019, 01, 22))
                .build();

        Optional<UserCart> cart = Optional.of(UserCart.builder().id(1).user(user).build());

        userCarts.add(cart.get());

        Optional<Products> product1 = Optional.of(new Products(1,"iphone",Products.Category.ELECTRONICS,450.00,true,userCarts));
        Optional<Products> product2 = Optional.of(new Products(2,"trouser",Products.Category.CLOTHING,50.00,true,userCarts));
        Optional<Products> product3 = Optional.of(new Products(2,"t-shirt",Products.Category.CLOTHING,80.00,true,userCarts));
        Optional<Products> product4 = Optional.of(new Products(3,"Sanitizer",Products.Category.GROCERIES,30.00,true,userCarts));

        products.add(product1.get());
        products.add(product2.get());
        products.add(product3.get());
        products.add(product4.get());

        cart.get().setProducts(products);

        Mockito.lenient().when(cartRepository.findById(1)).thenReturn(cart);

        Assertions.assertEquals(580,storeService.billUserCart(cart.get().getId()).getNetAmount());
    }
}
