package com.xische.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xische.demo.client.CurrencyExchangeClient;
import com.xische.demo.model.Item;
import com.xische.demo.model.request.DiscountCalculationRequest;
import com.xische.demo.model.response.NetPayableResponse;
import com.xische.demo.util.DiscountCalculator;
import lombok.RequiredArgsConstructor;

/**
 * The type Calculate bill service.
 */
@Service
@RequiredArgsConstructor
public class CalculateBillService {

  private final CurrencyExchangeClient currencyExchangeClient;

  private final DiscountCalculator discountCalculator;

  /**
   * Calculate payable amount net payable response.
   *
   * @param request the request
   * @return the net payable response
   */
  public NetPayableResponse calculatePayableAmount(DiscountCalculationRequest request) {
    double totalAmount = request.getItems().stream().mapToDouble(Item::getPrice).sum();

    double discount = discountCalculator.calculateDiscount(request.getUser(), request.getItems(), totalAmount);
    double amountAfterDiscount = totalAmount - discount;

    double exchangeRate = currencyExchangeClient.getExchangeRate(request.getOriginalCurrency(),
        request.getTargetCurrency());
    double convertedAmount = amountAfterDiscount * exchangeRate;

    return new NetPayableResponse(convertedAmount, request.getTargetCurrency());
  }
}
