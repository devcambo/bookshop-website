package com.devcambo.springinit.model.dto.request;

import java.util.Date;

public record OrderUpdateDto(Long customerId, Date orderDate, Double total) {}
