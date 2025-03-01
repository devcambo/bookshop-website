package com.devcambo.springinit.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.sql.Date;

@Schema(name = "BookUpdateDto", description = "Book update request schema")
public record BookUpdateDto(
  @Schema(description = "Book title", example = "The Great Gatsby Updated") String title,
  @Schema(description = "Book author", example = "F. Scott Fitzgerald Updated")
  String author,
  @Schema(description = "Book publisher", example = "Charles Scribner's Sons Updated")
  String publisher,
  @Schema(description = "Book publication date", example = "1925-04-10")
  Date publicationDate,
  @Schema(description = "Book ISBN", example = "978-3-16-148410-0 Updated") String isbn,
  @Schema(description = "Book price", example = "19.99") BigDecimal price,
  @Schema(
    description = "Book description",
    example = "A classic novel about the American Dream. Updated"
  )
  String description,
  @Schema(description = "Book image URL", example = "https://example.comupdated/book.jpg")
  String imageUrl
) {}
