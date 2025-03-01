package com.devcambo.springinit.model.dto.response;

import java.math.BigDecimal;
import java.sql.Date;

public record OrderResponseDto(Long orderId, Date orderDate, BigDecimal total) {}
