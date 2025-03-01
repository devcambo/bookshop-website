package com.devcambo.springinit.service;

import com.devcambo.springinit.model.dto.request.BookCreationDto;
import com.devcambo.springinit.model.dto.request.BookUpdateDto;
import com.devcambo.springinit.model.dto.response.BookResponseDto;
import org.springframework.data.domain.Page;

public interface BookService {
  /**
   * Retrieves a paginated list of all books.
   *
   * @param offset The offset from which to start retrieving books.
   * @param pageSize The number of books to retrieve per page.
   * @param sortBy The field to sort the books by.
   * @param sortDir The direction to sort the books (ASC or DESC).
   * @return A paginated list of BookResponseDto objects.
   */
  Page<BookResponseDto> readAll(
    Integer offset,
    Integer pageSize,
    String sortBy,
    String sortDir
  );

  /**
   * Retrieves a book by its ID.
   *
   * @param bookId The ID of the book to retrieve.
   * @return The BookResponseDto object for the specified book.
   */
  BookResponseDto readById(Long bookId);

  /**
   * Creates a new book.
   *
   * @param bookCreationDto The BookRequestDto object containing the book's details.
   * @return The BookResponseDto object for the newly created book.
   */
  BookResponseDto create(BookCreationDto bookCreationDto);

  /**
   * Updates an existing book.
   *
   * @param bookId The ID of the book to update.
   * @param bookUpdateDto The BookRequestDto object containing the updated book's details.
   * @return The BookResponseDto object for the updated book.
   */
  BookResponseDto update(Long bookId, BookUpdateDto bookUpdateDto);

  /**
   * Deletes a book by its ID.
   *
   * @param bookId The ID of the book to delete.
   */
  void delete(Long bookId);
}
