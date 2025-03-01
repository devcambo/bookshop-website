package com.devcambo.springinit.mapper;

import com.devcambo.springinit.model.dto.request.BookCreationDto;
import com.devcambo.springinit.model.dto.request.BookUpdateDto;
import com.devcambo.springinit.model.dto.response.BookResponseDto;
import com.devcambo.springinit.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {
  BookResponseDto toDto(Book book);

  Book toEntity(BookCreationDto bookCreationDto);

  @Mappings({ @Mapping(target = "id", ignore = true) })
  void updateFromDto(BookUpdateDto dto, @MappingTarget Book entity);
}
