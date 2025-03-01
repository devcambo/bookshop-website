package com.devcambo.springinit.controller;

import com.devcambo.springinit.constant.StatusCode;
import com.devcambo.springinit.model.base.APIResponse;
import com.devcambo.springinit.model.base.ErrorInfo;
import com.devcambo.springinit.model.dto.request.OrderCreationDto;
import com.devcambo.springinit.model.dto.request.OrderUpdateDto;
import com.devcambo.springinit.service.OrderService;
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

@Tag(name = "Order Management", description = "APIs for order management")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
@Slf4j
public class OrderController {

  private final OrderService orderService;

  @Operation(
    summary = "Find All Orders REST API",
    description = "REST API to find all orders with pagination and sorting options"
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
  public ResponseEntity<APIResponse> findAllOrders(
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
      description = "Sort by field (orderId, orderDate)",
      example = "orderId"
    ) @RequestParam(
      value = "sortBy",
      required = false,
      defaultValue = "orderId"
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
          "Retrieved all orders successfully",
          orderService.readAll(offset, pageSize, sortBy, sortDir)
        )
      );
  }

  @Operation(
    summary = "Find Order By OrderId REST API",
    description = "REST API to find order by orderId"
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
        description = "Order not found",
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
  @GetMapping("/{orderId}")
  public ResponseEntity<APIResponse> findOrderById(@PathVariable Long orderId) {
    return ResponseEntity
      .status(HttpStatus.FOUND)
      .body(
        new APIResponse(
          true,
          StatusCode.FOUND,
          "Retrieved order successfully",
          orderService.readById(orderId)
        )
      );
  }

  @Operation(
    summary = "Create Order REST API",
    description = "REST API to create a new order"
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
  public ResponseEntity<APIResponse> createOrder(
    @Valid @RequestBody OrderCreationDto orderCreationDto
  ) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(
        new APIResponse(
          true,
          StatusCode.CREATED,
          "Order created successfully",
          orderService.create(orderCreationDto)
        )
      );
  }

  @Operation(
    summary = "Update Order REST API",
    description = "REST API to update an existing order"
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
        description = "Order not found",
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
  @PutMapping("/{orderId}")
  public ResponseEntity<APIResponse> updateOrder(
    @PathVariable Long orderId,
    @Valid @RequestBody OrderUpdateDto orderUpdateDto
  ) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(
        new APIResponse(
          true,
          StatusCode.OK,
          "Order updated successfully",
          orderService.update(orderId, orderUpdateDto)
        )
      );
  }

  @Operation(
    summary = "Delete Order REST API",
    description = "REST API to delete an existing order"
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
        description = "Order not found",
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
  @DeleteMapping("/{orderId}")
  public ResponseEntity<APIResponse> deleteOrder(@PathVariable Long orderId) {
    orderService.delete(orderId);
    return ResponseEntity
      .status(HttpStatus.NO_CONTENT)
      .body(new APIResponse(true, StatusCode.NO_CONTENT, "Order deleted successfully"));
  }
}
