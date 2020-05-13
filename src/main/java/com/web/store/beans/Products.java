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
@Table(name = "products")
public class Products {

    public enum Category {
        GROCERIES,
        CLOTHING,
        ELECTRONICS;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Category type;

    @Column(name = "price")
    private double price;

    @Column(name = "in_stock")
    private boolean inStock;

    @ManyToMany(mappedBy = "products")
    private Set<UserCart> userCarts = new HashSet<>();
}
