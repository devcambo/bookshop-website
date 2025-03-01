package com.devcambo.springinit.model.dto.response;

import java.math.BigDecimal;
import java.sql.Date;

public record BookResponseDto(
  Long id,
  String title,
  String author,
  String publisher,
  Date publicationDate,
  String isbn,
  BigDecimal price,
  String description,
  String imageUrl
) {}
