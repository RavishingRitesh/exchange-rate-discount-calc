package com.xische.demo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.xische.demo.exception.CurrencyExchangeException;
import com.xische.demo.model.response.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Currency exchange client.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeClient {

  private final RestTemplate restTemplate;

  @Value("${exchange.api.key}")
  private String apiKey;

  @Value("${exchange.api.url}")
  private String apiUrl;

  /**
   * Gets exchange rate.
   *
   * @param from the from
   * @param to   the to
   * @return the exchange rate
   */
  public double getExchangeRate(String from, String to) {
    String url = String.format(apiUrl, apiKey, from);
    ExchangeRateResponse response;
    try {
      response = restTemplate.getForObject(url, ExchangeRateResponse.class);
    }catch (Exception e){
      log.error("Exchange API failed for conversion from {} to {}", from, to);
      throw new CurrencyExchangeException("Exchange API failed for conversion", e.getCause());

    }
    assert response != null;
    return response.getConversionRates().get(to);
  }
}
