package com.devcambo.springinit.service.impl;

import com.devcambo.springinit.exception.InvalidSortFieldException;
import com.devcambo.springinit.exception.ResourceNotFoundException;
import com.devcambo.springinit.mapper.BookMapper;
import com.devcambo.springinit.model.dto.request.BookCreationDto;
import com.devcambo.springinit.model.dto.request.BookUpdateDto;
import com.devcambo.springinit.model.dto.response.BookResponseDto;
import com.devcambo.springinit.model.entity.Book;
import com.devcambo.springinit.repo.BookRepository;
import com.devcambo.springinit.service.BookService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepo;
  private final BookMapper bookMapper;

  @Override
  public Page<BookResponseDto> readAll(
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
    Page<Book> books = bookRepo.findAll(pageRequest);
    return books.map(bookMapper::toDto);
  }

  @Override
  public BookResponseDto readById(Long bookId) {
    Book book = getBookById(bookId);
    return bookMapper.toDto(book);
  }

  @Override
  public BookResponseDto create(BookCreationDto bookCreationDto) {
    Book book = bookMapper.toEntity(bookCreationDto);
    Book savedBook = bookRepo.save(book);
    return bookMapper.toDto(savedBook);
  }

  @Override
  public BookResponseDto update(Long bookId, BookUpdateDto bookUpdateDto) {
    Book existingBook = getBookById(bookId);
    bookMapper.updateFromDto(bookUpdateDto, existingBook);
    Book savedBook = bookRepo.save(existingBook);
    return bookMapper.toDto(savedBook);
  }

  @Override
  public void delete(Long bookId) {
    Book idToDelete = getBookById(bookId);
    bookRepo.deleteById(idToDelete.getId());
  }

  private Book getBookById(Long bookId) {
    return bookRepo
      .findById(bookId)
      .orElseThrow(() -> new ResourceNotFoundException("book", bookId));
  }

  private boolean isValidSortField(String field) {
    return Arrays.asList("id", "title", "author").contains(field);
  }
}
