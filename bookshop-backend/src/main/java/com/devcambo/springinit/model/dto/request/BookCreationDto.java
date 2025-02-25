package com.devcambo.springinit.model.dto.request;

public record BookCreationDto(
  String title,
  String author,
  String description,
  Double price
) {}
