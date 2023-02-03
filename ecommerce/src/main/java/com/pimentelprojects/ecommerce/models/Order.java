package com.pimentelprojects.ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @SequenceGenerator(
            name = "order_id_seq",
            sequenceName = "order_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_id_seq"
    )
    private Long id;
    private String number;
    @CreationTimestamp
    private LocalDateTime dateCreate;
    private LocalDateTime dateReceived;

    private double total;

    @ManyToOne
    private UserEntity userEntity;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderDetails> orderDetails;


}
