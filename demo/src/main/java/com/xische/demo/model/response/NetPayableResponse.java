package com.xische.demo.model.response;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * The type Net payable response.
 */
@Data
@Getter
@Setter
@Builder
public class NetPayableResponse {
  private double amount;

  private String currency;

  /**
   * Instantiates a new Net payable response.
   *
   * @param amount   the amount
   * @param currency the currency
   */
  public NetPayableResponse(double amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }
}



