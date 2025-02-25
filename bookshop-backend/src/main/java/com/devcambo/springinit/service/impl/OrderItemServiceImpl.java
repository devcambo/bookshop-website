package com.devcambo.springinit.service.impl;

import com.devcambo.springinit.exception.InvalidSortFieldException;
import com.devcambo.springinit.exception.ResourceNotFoundException;
import com.devcambo.springinit.mapper.OrderItemMapper;
import com.devcambo.springinit.model.dto.request.OrderItemCreationDto;
import com.devcambo.springinit.model.dto.request.OrderItemUpdateDto;
import com.devcambo.springinit.model.dto.response.OrderItemResponseDto;
import com.devcambo.springinit.model.entity.OrderItem;
import com.devcambo.springinit.repo.OrderItemRepository;
import com.devcambo.springinit.service.OrderItemService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

  private final OrderItemRepository orderItemRepo;
  private final OrderItemMapper orderItemMapper;

  @Override
  public Page<OrderItemResponseDto> readAll(
    Integer offset,
    Integer pageSize,
    String sortBy,
    String sortDir
  ) {
    if (!isValidSortField(sortBy)) {
      throw new InvalidSortFieldException("Invalid sort field");
    }
    Sort.Direction direction = sortDir.equalsIgnoreCase("ASC")
      ? Sort.Direction.ASC
      : Sort.Direction.DESC;
    PageRequest pageRequest = PageRequest.of(
      offset,
      pageSize,
      Sort.by(direction, sortBy)
    );
    Page<OrderItem> orderItems = orderItemRepo.findAll(pageRequest);
    return orderItems.map(orderItemMapper::toDto);
  }

  @Override
  public OrderItemResponseDto readById(Long orderItemId) {
    OrderItem orderItem = getOrderItemById(orderItemId);
    return orderItemMapper.toDto(orderItem);
  }

  @Override
  public OrderItemResponseDto create(OrderItemCreationDto orderItemCreationDto) {
    OrderItem orderItem = orderItemMapper.toEntity(orderItemCreationDto);
    OrderItem savedOrderItem = orderItemRepo.save(orderItem);
    return orderItemMapper.toDto(savedOrderItem);
  }

  @Override
  public OrderItemResponseDto update(Long orderItemId, OrderItemUpdateDto orderItemUpdateDto) {
    OrderItem existingOrderItem = getOrderItemById(orderItemId);
    orderItemMapper.updateFromDto(orderItemUpdateDto, existingOrderItem);
    OrderItem savedOrderItem = orderItemRepo.save(existingOrderItem);
    return orderItemMapper.toDto(savedOrderItem);
  }

  @Override
  public void delete(Long orderItemId) {
    OrderItem idToDelete = getOrderItemById(orderItemId);
    orderItemRepo.deleteById(idToDelete.getId());
  }

  private OrderItem getOrderItemById(Long orderItemId) {
    return orderItemRepo
      .findById(orderItemId)
      .orElseThrow(() -> new ResourceNotFoundException("orderItem", orderItemId));
  }

  private boolean isValidSortField(String field) {
    return Arrays.asList("orderItemId", "orderId", "productId", "quantity").contains(field);
  }
}