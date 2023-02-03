package com.pimentelprojects.ecommerce.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetails {
    @Id
    @SequenceGenerator(
            name = "detail_id_seq",
            sequenceName = "detail_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "detail_id_seq"
    )
    private Long id;
    private String name;
    private double quantity;
    private double price;
    private double total;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    private Product product;




}

