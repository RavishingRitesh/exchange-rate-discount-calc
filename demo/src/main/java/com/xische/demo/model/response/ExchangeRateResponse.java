package com.xische.demo.model.response;

import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Exchange rate response.
 */
@Data
@Getter
@Setter
@Builder
public class ExchangeRateResponse {
  private String baseCode;

  private Map<String, Double> conversionRates;
}

