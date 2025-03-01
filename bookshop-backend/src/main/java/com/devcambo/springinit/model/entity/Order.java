package com.devcambo.springinit.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  @Column(name = "order_date")
  private Date orderDate;

  @Column(name = "total")
  private BigDecimal total;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems;
}
