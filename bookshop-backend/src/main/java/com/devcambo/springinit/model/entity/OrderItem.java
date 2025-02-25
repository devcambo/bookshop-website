package com.devcambo.springinit.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "order_items")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "book_id", referencedColumnName = "id")
  private Book book;

  @Column(name = "quantity")
  private Integer quantity;

}