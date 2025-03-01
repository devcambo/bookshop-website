package com.devcambo.springinit.service;

import com.devcambo.springinit.model.dto.request.OrderItemCreationDto;
import com.devcambo.springinit.model.dto.request.OrderItemUpdateDto;
import com.devcambo.springinit.model.dto.response.OrderItemResponseDto;
import org.springframework.data.domain.Page;

public interface OrderItemService {
  /**
   * Retrieves a paginated list of all order items.
   *
   * @param offset The offset from which to start retrieving order items.
   * @param pageSize The number of order items to retrieve per page.
   * @param sortBy The field to sort the order items by.
   * @param sortDir The direction to sort the order items (ASC or DESC).
   * @return A paginated list of OrderItemResponseDto objects.
   */
  Page<OrderItemResponseDto> readAll(
    Integer offset,
    Integer pageSize,
    String sortBy,
    String sortDir
  );

  /**
   * Retrieves an order item by its ID.
   *
   * @param orderItemId The ID of the order item to retrieve.
   * @return The OrderItemResponseDto object for the specified order item.
   */
  OrderItemResponseDto readById(Long orderItemId);

  /**
   * Creates a new order item.
   *
   * @param orderItemCreationDto The OrderItemRequestDto object containing the order item's details.
   * @return The OrderItemResponseDto object for the newly created order item.
   */
  OrderItemResponseDto create(OrderItemCreationDto orderItemCreationDto);

  /**
   * Updates an existing order item.
   *
   * @param orderItemId The ID of the order item to update.
   * @param orderItemUpdateDto The OrderItemRequestDto object containing the updated order item's details.
   * @return The OrderItemResponseDto object for the updated order item.
   */
  OrderItemResponseDto update(Long orderItemId, OrderItemUpdateDto orderItemUpdateDto);

  /**
   * Deletes an order item by its ID.
   *
   * @param orderItemId The ID of the order item to delete.
   */
  void delete(Long orderItemId);
}
