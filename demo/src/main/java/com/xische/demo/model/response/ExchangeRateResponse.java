package com.xische.demo.model.response;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty("base_code")
  private String baseCode;

  @JsonProperty("conversion_rates")
  private Map<String, Double> conversionRates;
}

