package com.devcambo.springinit.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "author")
  private String author;

  @Column(name = "publisher")
  private String publisher;

  @Column(name = "publication_date")
  private Date publicationDate;

  @Column(name = "isbn")
  private String isbn;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "description")
  private String description;

  @Column(name = "image_url")
  private String imageUrl;

}