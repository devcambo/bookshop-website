package com.devcambo.springinit.controller;

import com.devcambo.springinit.constant.StatusCode;
import com.devcambo.springinit.model.base.APIResponse;
import com.devcambo.springinit.model.base.ErrorInfo;
import com.devcambo.springinit.model.dto.request.BookCreationDto;
import com.devcambo.springinit.model.dto.request.BookUpdateDto;
import com.devcambo.springinit.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Book Management", description = "APIs for book management")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Validated
@Slf4j
public class BookController {

  private final BookService bookService;

  @Operation(
    summary = "Find All Books REST API",
    description = "REST API to find all books with pagination and sorting options"
  )
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = APIResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid request parameters or body",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Forbidden",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "405",
        description = "This http method is not allowed for this API endpoint",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Internal Server Error",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
    }
  )
  @GetMapping
  public ResponseEntity<APIResponse> findAllBooks(
    @Parameter(description = "Zero-based offset index", example = "0") @RequestParam(
      value = "offset",
      required = false,
      defaultValue = "0"
    ) @Min(0) Integer offset,
    @Parameter(
      description = "Number of items per page (1-100)",
      example = "10"
    ) @RequestParam(value = "pageSize", required = false, defaultValue = "10") @Min(
      1
    ) @Max(100) Integer pageSize,
    @Parameter(description = "Sort by field (id, title)", example = "id") @RequestParam(
      value = "sortBy",
      required = false,
      defaultValue = "id"
    ) String sortBy,
    @Parameter(description = "Sort direction (ASC/DESC)", example = "ASC") @RequestParam(
      value = "sortDir",
      required = false,
      defaultValue = "ASC"
    ) @Pattern(regexp = "ASC|DESC", flags = Pattern.Flag.CASE_INSENSITIVE) String sortDir
  ) {
    return ResponseEntity
      .ok()
      /*.cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS))*/
      .body(
        new APIResponse(
          true,
          StatusCode.OK,
          "Retrieved all books successfully",
          bookService.readAll(offset, pageSize, sortBy, sortDir)
        )
      );
  }

  @Operation(
    summary = "Find Book By BookId REST API",
    description = "REST API to find book by bookId"
  )
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = APIResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid request parameters or body",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Forbidden",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Book not found",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "405",
        description = "This http method is not allowed for this API endpoint",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Internal Server Error",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
    }
  )
  @GetMapping("/{bookId}")
  public ResponseEntity<APIResponse> findBookById(@PathVariable Long bookId) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(
        new APIResponse(
          true,
          StatusCode.OK,
          "Retrieved book successfully",
          bookService.readById(bookId)
        )
      );
  }

  @Operation(
    summary = "Create Book REST API",
    description = "REST API to create a new book"
  )
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "201",
        description = "HTTP Status Created",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = APIResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid request parameters or body",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Forbidden",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "405",
        description = "This http method is not allowed for this API endpoint",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Internal Server Error",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
    }
  )
  @PostMapping
  public ResponseEntity<APIResponse> createBook(
    @Valid @RequestBody BookCreationDto bookCreationDto
  ) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(
        new APIResponse(
          true,
          StatusCode.CREATED,
          "Book created successfully",
          bookService.create(bookCreationDto)
        )
      );
  }

  @Operation(
    summary = "Update Book REST API",
    description = "REST API to update an existing book"
  )
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = APIResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid request parameters or body",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Forbidden",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Book not found",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "405",
        description = "This http method is not allowed for this API endpoint",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Internal Server Error",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
    }
  )
  @PutMapping("/{bookId}")
  public ResponseEntity<APIResponse> updateBook(
    @PathVariable Long bookId,
    @Valid @RequestBody BookUpdateDto bookUpdateDto
  ) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(
        new APIResponse(
          true,
          StatusCode.OK,
          "Book updated successfully",
          bookService.update(bookId, bookUpdateDto)
        )
      );
  }

  @Operation(
    summary = "Delete Book REST API",
    description = "REST API to delete an existing book"
  )
  @ApiResponses(
    {
      @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = APIResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "403",
        description = "Forbidden",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Book not found",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "405",
        description = "This http method is not allowed for this API endpoint",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
      @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Internal Server Error",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorInfo.class)
        )
      ),
    }
  )
  @DeleteMapping("/{bookId}")
  public ResponseEntity<APIResponse> deleteBook(@PathVariable Long bookId) {
    bookService.delete(bookId);
    return ResponseEntity
      .status(HttpStatus.NO_CONTENT)
      .body(new APIResponse(true, StatusCode.NO_CONTENT, "Book deleted successfully"));
  }
}
