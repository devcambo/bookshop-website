package com.devcambo.springinit.model.dto.request;

public record BookUpdateDto(
  String title,
  String author,
  String description,
  Double price
) {}