package com.devcambo.springinit.model.dto.response;

public record BookResponseDto(
  Long bookId,
  String title,
  String author,
  String description,
  Double price
) {}