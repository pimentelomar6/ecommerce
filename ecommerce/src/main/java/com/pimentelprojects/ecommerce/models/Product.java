package com.pimentelprojects.ecommerce.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_id_seq",
            sequenceName = "product_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_id_seq"
    )
    private Long id;
    private String name;
    private String description;
    private String image;
    private double price;
    private  int quantity;

    @CreationTimestamp
    private LocalDateTime dateCreate;
    @ManyToOne
    private UserEntity userEntity;

}
