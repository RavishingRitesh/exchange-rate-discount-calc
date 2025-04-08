package com.xische.demo.util;

import java.util.List;
import com.xische.demo.model.Item;
import com.xische.demo.model.User;
import com.xische.demo.model.UserType;
/**
 * The type Discount calculator.
 */
public class DiscountCalculator {

  /**
   * Calculate discount double.
   *
   * @param user        the user
   * @param items       the items
   * @param totalAmount the total amount
   * @return the double
   */
  public static double calculateDiscount(User user, List<Item> items, double totalAmount) {
    double percentageDiscount = 0.0;
    boolean hasNonGroceryItems = items.stream().anyMatch(i -> !i.getCategory().equalsIgnoreCase("grocery"));

    if (hasNonGroceryItems) {
      if (user.getType() == UserType.EMPLOYEE) {
        percentageDiscount = 0.30;
      } else if (user.getType() == UserType.AFFILIATE) {
        percentageDiscount = 0.10;
      } else if (user.getTenureYears() > 2) {
        percentageDiscount = 0.05;
      }
    }

    double percentageDiscountAmount = totalAmount * percentageDiscount;
    double amountBasedDiscount = ((int) (totalAmount / 100)) * 5;

    return percentageDiscountAmount + amountBasedDiscount;
  }
}
