package com.devcambo.springinit.model.dto.request;

public record OrderItemUpdateDto(
  Long orderId,
  Long productId,
  Integer quantity,
  Double price
) {}