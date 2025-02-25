package com.devcambo.springinit.model.dto.response;

public record OrderResponseDto(
  Long orderId,
  Long customerId,
  String orderDate,
  Double total
) {}