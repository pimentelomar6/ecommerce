package com.pimentelprojects.ecommerce.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String mail;
    private String address;
    private String phoneNumber;
    private String tipo;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Product> productList;

    @OneToMany(mappedBy = "user")
    private List<Order> orderList;

}
