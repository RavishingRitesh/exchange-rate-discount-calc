package com.xische.demo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * The type Item.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class Item {
  private String name;

  private String category;

  private double price;
}