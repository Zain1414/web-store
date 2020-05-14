package com.web.store.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "invoice")
public class Invoice {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "gross_amount")
    private double grossAmount;

    @Column(name = "net_amount")
    private double netAmount;

    @Column(name = "invoiced_date")
    private LocalDateTime invoicedDate;

    @Column(name = "discounted_percentage")
    private double discountedPercentage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_cart_id", referencedColumnName = "id")
    private UserCart userCart;
}
