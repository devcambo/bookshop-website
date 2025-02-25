package com.devcambo.springinit.service;

import com.devcambo.springinit.model.dto.request.OrderCreationDto;
import com.devcambo.springinit.model.dto.request.OrderUpdateDto;
import com.devcambo.springinit.model.dto.response.OrderResponseDto;
import org.springframework.data.domain.Page;

public interface OrderService {

  /**
   * Retrieves a paginated list of all orders.
   *
   * @param offset The offset from which to start retrieving orders.
   * @param pageSize The number of orders to retrieve per page.
   * @param sortBy The field to sort the orders by.
   * @param sortDir The direction to sort the orders (ASC or DESC).
   * @return A paginated list of OrderResponseDto objects.
   */
  Page<OrderResponseDto> readAll(
    Integer offset,
    Integer pageSize,
    String sortBy,
    String sortDir
  );

  /**
   * Retrieves an order by its ID.
   *
   * @param orderId The ID of the order to retrieve.
   * @return The OrderResponseDto object for the specified order.
   */
  OrderResponseDto readById(Long orderId);

  /**
   * Creates a new order.
   *
   * @param orderCreationDto The OrderRequestDto object containing the order's details.
   * @return The OrderResponseDto object for the newly created order.
   */
  OrderResponseDto create(OrderCreationDto orderCreationDto);

  /**
   * Updates an existing order.
   *
   * @param orderId The ID of the order to update.
   * @param orderUpdateDto The OrderRequestDto object containing the updated order's details.
   * @return The OrderResponseDto object for the updated order.
   */
  OrderResponseDto update(Long orderId, OrderUpdateDto orderUpdateDto);

  /**
   * Deletes an order by its ID.
   *
   * @param orderId The ID of the order to delete.
   */
  void delete(Long orderId);
}
