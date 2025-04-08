package com.xische.demo.util;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.xische.demo.model.Item;
import com.xische.demo.model.User;
import com.xische.demo.model.UserType;
import lombok.RequiredArgsConstructor;
/**
 * The type Discount calculator.
 */
@Component
public class DiscountCalculator {

  @Value("${discount.employee.percentage:0.30}")
  private Double employeeDiscount;

  @Value("${discount.affiliate.percentage:0.10}")
  private Double affDiscount;

  @Value("${discount.loyal-customer.percentage:0.05}")
  private Double loyalCustomerDiscount;

  @Value("${discount.loyal-customer.years:2}")
  private Integer loyalCustomerYears;

  @Value("${discount.bill.amount:100}")
  private Integer billAmount;

  @Value("${discount.bill.discount:5}")
  private Integer billDiscount;

  @Value("${app.exempted.item:grocery}")
  private String exemptedItem;



  /**
   * Calculate discount double.
   *
   * @param user        the user
   * @param items       the items
   * @param totalAmount the total amount
   * @return the double
   */
  public double calculateDiscount(User user, List<Item> items, double totalAmount) {
    double percentageDiscount = 0.0;

    double nonGroceryTotal = items.stream()
        .filter(i -> !i.getCategory().equalsIgnoreCase(exemptedItem))
        .mapToDouble(Item::getPrice)
        .sum();

    if (nonGroceryTotal > 0) {
      if (user.getType() == UserType.EMPLOYEE) {
        percentageDiscount = employeeDiscount;
      } else if (user.getType() == UserType.AFFILIATE) {
        percentageDiscount = affDiscount;
      } else if (user.getTenureYears() > loyalCustomerYears) {
        percentageDiscount = loyalCustomerDiscount;
      }
    }

    double percentageDiscountAmount = nonGroceryTotal * percentageDiscount;
    double amountBasedDiscount = ((int) (totalAmount / billAmount)) * billDiscount;

    return percentageDiscountAmount + amountBasedDiscount;
  }
}
