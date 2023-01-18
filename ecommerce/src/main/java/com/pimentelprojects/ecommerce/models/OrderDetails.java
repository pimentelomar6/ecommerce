package com.pimentelprojects.ecommerce.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double quantity;
    private double price;
    private double total;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;




}

