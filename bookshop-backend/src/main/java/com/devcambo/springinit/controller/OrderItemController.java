package com.devcambo.springinit.controller;

import com.devcambo.springinit.constant.StatusCode;
import com.devcambo.springinit.model.base.APIResponse;
import com.devcambo.springinit.model.base.ErrorInfo;
import com.devcambo.springinit.model.dto.request.OrderItemCreationDto;
import com.devcambo.springinit.model.dto.request.OrderItemUpdateDto;
import com.devcambo.springinit.service.OrderItemService;
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

@Tag(name = "Order Item Management", description = "APIs for order item management")
@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
@Validated
@Slf4j
public class OrderItemController {

  private final OrderItemService orderItemService;

  @Operation(
    summary = "Find All Order Items REST API",
    description = "REST API to find all order items with pagination and sorting options"
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
  public ResponseEntity<APIResponse> findAllOrderItems(
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
    @Parameter(
      description = "Sort by field (orderItemId, orderId)",
      example = "orderItemId"
    ) @RequestParam(
      value = "sortBy",
      required = false,
      defaultValue = "orderItemId"
    ) String sortBy,
    @Parameter(description = "Sort direction (ASC/DESC)", example = "ASC") @RequestParam(
      value = "sortDir",
      required = false,
      defaultValue = "ASC"
    ) @Pattern(regexp = "ASC|DESC", flags = Pattern.Flag.CASE_INSENSITIVE) String sortDir
  ) {
    return ResponseEntity
      .ok()
      .cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS))
      .body(
        new APIResponse(
          true,
          StatusCode.OK,
          "Retrieved all order items successfully",
          orderItemService.readAll(offset, pageSize, sortBy, sortDir)
        )
      );
  }

  @Operation(
    summary = "Find Order Item By OrderItemId REST API",
    description = "REST API to find order item by orderItemId"
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
        description = "Order item not found",
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
  @GetMapping("/{orderItemId}")
  public ResponseEntity<APIResponse> findOrderItemById(@PathVariable Long orderItemId) {
    return ResponseEntity
      .status(HttpStatus.FOUND)
      .body(
        new APIResponse(
          true,
          StatusCode.FOUND,
          "Retrieved order item successfully",
          orderItemService.readById(orderItemId)
        )
      );
  }

  @Operation(
    summary = "Create Order Item REST API",
    description = "REST API to create a new order item"
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
  public ResponseEntity<APIResponse> createOrderItem(
    @Valid @RequestBody OrderItemCreationDto orderItemCreationDto
  ) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(
        new APIResponse(
          true,
          StatusCode.CREATED,
          "Order item created successfully",
          orderItemService.create(orderItemCreationDto)
        )
      );
  }

  @Operation(
    summary = "Update Order Item REST API",
    description = "REST API to update an existing order item"
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
        description = "Order item not found",
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
  @PutMapping("/{orderItemId}")
  public ResponseEntity<APIResponse> updateOrderItem(
    @PathVariable Long orderItemId,
    @Valid @RequestBody OrderItemUpdateDto orderItemUpdateDto
  ) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(
        new APIResponse(
          true,
          StatusCode.OK,
          "Order item updated successfully",
          orderItemService.update(orderItemId, orderItemUpdateDto)
        )
      );
  }

  @Operation(
    summary = "Delete Order Item REST API",
    description = "REST API to delete an existing order item"
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
        description = "Order item not found",
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
  @DeleteMapping("/{orderItemId}")
  public ResponseEntity<APIResponse> deleteOrderItem(@PathVariable Long orderItemId) {
    orderItemService.delete(orderItemId);
    return ResponseEntity
      .status(HttpStatus.NO_CONTENT)
      .body(
        new APIResponse(true, StatusCode.NO_CONTENT, "Order item deleted successfully")
      );
  }
}
