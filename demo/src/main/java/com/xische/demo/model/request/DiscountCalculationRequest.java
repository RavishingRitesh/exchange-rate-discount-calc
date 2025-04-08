package com.xische.demo.model.request;

import java.util.List;
import com.xische.demo.model.Item;
import com.xische.demo.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The type Discount calculation request.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCalculationRequest {

  @NotNull(message = "User information is required")
  private User user;

  @NotEmpty(message = "Items list must not be empty")
  private List<@NotNull Item> items;

  @NotNull(message = "Original currency is required")
  private String originalCurrency;

  @NotNull(message = "Target currency is required")
  private String targetCurrency;
}