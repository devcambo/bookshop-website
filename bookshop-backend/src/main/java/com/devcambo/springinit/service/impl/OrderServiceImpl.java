package com.devcambo.springinit.service.impl;

import com.devcambo.springinit.exception.InvalidSortFieldException;
import com.devcambo.springinit.exception.ResourceNotFoundException;
import com.devcambo.springinit.mapper.OrderMapper;
import com.devcambo.springinit.model.dto.request.OrderCreationDto;
import com.devcambo.springinit.model.dto.request.OrderUpdateDto;
import com.devcambo.springinit.model.dto.response.OrderResponseDto;
import com.devcambo.springinit.model.entity.Order;
import com.devcambo.springinit.repo.OrderRepository;
import com.devcambo.springinit.service.OrderService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepo;
  private final OrderMapper orderMapper;

  @Override
  public Page<OrderResponseDto> readAll(
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
    Page<Order> orders = orderRepo.findAll(pageRequest);
    return orders.map(orderMapper::toDto);
  }

  @Override
  public OrderResponseDto readById(Long orderId) {
    Order order = getOrderById(orderId);
    return orderMapper.toDto(order);
  }

  @Override
  public OrderResponseDto create(OrderCreationDto orderCreationDto) {
    Order order = orderMapper.toEntity(orderCreationDto);
    Order savedOrder = orderRepo.save(order);
    return orderMapper.toDto(savedOrder);
  }

  @Override
  public OrderResponseDto update(Long orderId, OrderUpdateDto orderUpdateDto) {
    Order existingOrder = getOrderById(orderId);
    orderMapper.updateFromDto(orderUpdateDto, existingOrder);
    Order savedOrder = orderRepo.save(existingOrder);
    return orderMapper.toDto(savedOrder);
  }

  @Override
  public void delete(Long orderId) {
    Order idToDelete = getOrderById(orderId);
    orderRepo.deleteById(idToDelete.getId());
  }

  private Order getOrderById(Long orderId) {
    return orderRepo
      .findById(orderId)
      .orElseThrow(() -> new ResourceNotFoundException("order", orderId));
  }

  private boolean isValidSortField(String field) {
    return Arrays.asList("orderId", "customerId", "orderDate").contains(field);
  }
}
