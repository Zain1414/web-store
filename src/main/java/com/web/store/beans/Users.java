package com.web.store.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "users")
public class Users {

    public enum UserType {
        CUSTOMER,
        AFFILIATED,
        EMPLOYEE;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private UserType type;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<UserCart> cart;
}
