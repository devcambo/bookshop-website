package com.devcambo.springinit.model.dto.request;

public record OrderItemCreationDto(
  Long orderId,
  Long productId,
  Integer quantity,
  Double price
) {}