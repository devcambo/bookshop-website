package com.devcambo.springinit.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.sql.Date;

@Schema(name = "BookCreationDto", description = "Book creation request schema")
public record BookCreationDto(
  @Schema(description = "Book title", example = "The Great Gatsby") String title,
  @Schema(description = "Book author", example = "F. Scott Fitzgerald") String author,
  @Schema(description = "Book publisher", example = "Charles Scribner's Sons")
  String publisher,
  @Schema(description = "Book publication date", example = "1925-04-10")
  Date publicationDate,
  @Schema(description = "Book ISBN", example = "978-3-16-148410-0") String isbn,
  @Schema(description = "Book price", example = "9.99") BigDecimal price,
  @Schema(
    description = "Book description",
    example = "A classic novel about the American Dream."
  )
  String description,
  @Schema(description = "Book image URL", example = "https://example.com/book.jpg")
  String imageUrl
) {}
