package com.devcambo.springinit.model.dto.response;

public record OrderItemResponseDto(
  Long orderItemId,
  Long orderId,
  Long productId,
  Integer quantity,
  Double price
) {}
