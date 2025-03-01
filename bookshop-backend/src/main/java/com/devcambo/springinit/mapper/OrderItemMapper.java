package com.devcambo.springinit.mapper;

import com.devcambo.springinit.model.dto.request.OrderItemCreationDto;
import com.devcambo.springinit.model.dto.request.OrderItemUpdateDto;
import com.devcambo.springinit.model.dto.response.OrderItemResponseDto;
import com.devcambo.springinit.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
  OrderItemResponseDto toDto(OrderItem orderItem);

  OrderItem toEntity(OrderItemCreationDto orderItemCreationDto);

  @Mappings({ @Mapping(target = "id", ignore = true) })
  void updateFromDto(OrderItemUpdateDto dto, @MappingTarget OrderItem entity);
}
