package com.devcambo.springinit.mapper;

import com.devcambo.springinit.model.dto.request.OrderCreationDto;
import com.devcambo.springinit.model.dto.request.OrderUpdateDto;
import com.devcambo.springinit.model.dto.response.OrderResponseDto;
import com.devcambo.springinit.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  OrderResponseDto toDto(Order order);


  Order toEntity(OrderCreationDto orderCreationDto);

  @Mappings(
    {
      @Mapping(target = "id", ignore = true),
    }
  )
  void updateFromDto(OrderUpdateDto dto, @MappingTarget Order entity);
}