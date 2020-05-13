package com.web.store.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "user_cart")
public class UserCart {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_cart_products",
            joinColumns = { @JoinColumn(name = "user_cart_id") },
            inverseJoinColumns = { @JoinColumn(name = "products_id") }
    )
    Set<Products> products = new HashSet<>();

    @OneToOne(mappedBy = "userCart")
    private Invoice invoice;
}
