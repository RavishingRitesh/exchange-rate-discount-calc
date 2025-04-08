package com.xische.demo.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * The type User.
 */
@Data
@Setter
@Getter
@AllArgsConstructor
public class User {
  private UserType type;

  private Integer tenureYears;
}
