package com.xische.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import com.xische.demo.client.CurrencyExchangeClient;
import com.xische.demo.model.Item;
import com.xische.demo.model.User;
import com.xische.demo.model.UserType;
import com.xische.demo.model.request.DiscountCalculationRequest;
import com.xische.demo.model.response.NetPayableResponse;
@SpringBootTest
public class BillServiceTest {

  @Mock
  private CurrencyExchangeClient exchangeClient;

  @InjectMocks
  private CalculateBillService billService;

  @Mock
  RestTemplate restTemplate;

  @Test
  public void testCalculatePayableAmount() {
    when(exchangeClient.getExchangeRate("USD", "EUR")).thenReturn(0.85);

    DiscountCalculationRequest request = new DiscountCalculationRequest();
    request.setOriginalCurrency("USD");
    request.setTargetCurrency("EUR");
    request.setUser(new User(UserType.EMPLOYEE, 3));
    request.setItems(List.of(new Item("TV", "electronics", 1000)));

    NetPayableResponse response = billService.calculatePayableAmount(request);
    assertNotNull(response);
    assertEquals("EUR", response.getCurrency());
  }

  @Test
  void shouldApplyEmployeeDiscountAndConvertCurrency() {
    User user = new User(UserType.EMPLOYEE, 3);
    List<Item> items = List.of(new Item("TV", "electronics", 1000), new Item("Banana", "groceries", 100));
    DiscountCalculationRequest request = new DiscountCalculationRequest(items, user, "USD", "EUR");

    when(exchangeClient.getExchangeRate("USD", "EUR")).thenReturn(0.9);

    NetPayableResponse response = billService.calculatePayableAmount(request);

    assertEquals(643.5, response.getAmount());
    assertEquals("EUR", response.getCurrency());
  }

  @Test
  void shouldApplyAffiliateDiscount() {
    User user = new User(UserType.AFFILIATE, 1);
    List<Item> items = List.of(new Item("Chair", "furniture", 500), new Item("Apples", "groceries", 200));
    DiscountCalculationRequest request = new DiscountCalculationRequest(items, user, "USD", "EUR");

    when(exchangeClient.getExchangeRate("USD", "EUR")).thenReturn(1.0);

    NetPayableResponse response = billService.calculatePayableAmount(request);

    assertEquals(595.0, response.getAmount());
  }

  @Test
  void shouldApplyLoyalCustomerDiscount() {
    User user = new User(UserType.CUSTOMER, 3);
    List<Item> items = List.of(new Item("Laptop", "electronics", 1000));
    DiscountCalculationRequest request = new DiscountCalculationRequest(items, user, "USD", "EUR");

    when(exchangeClient.getExchangeRate("USD", "EUR")).thenReturn(1.0);

    NetPayableResponse response = billService.calculatePayableAmount(request);

    assertEquals(900.0, response.getAmount());
  }

  @Test
  void shouldApplyOnlyFlatDiscountWhenUserNotEligibleForPercent() {
    User user = new User(UserType.CUSTOMER, 1);
    List<Item> items = List.of(new Item("TV", "electronics", 990));
    DiscountCalculationRequest request = new DiscountCalculationRequest(items, user, "USD", "EUR");

    when(exchangeClient.getExchangeRate("USD", "EUR")).thenReturn(1.0);

    NetPayableResponse response = billService.calculatePayableAmount(request);

    assertEquals(945.0, response.getAmount());
  }

  @Test
  void shouldThrowExceptionForInvalidCurrency() {
    User user = new User(UserType.EMPLOYEE, 2);
    List<Item> items = List.of(new Item("TV", "electronics", 1000));
    DiscountCalculationRequest request = new DiscountCalculationRequest(items, user, "XYZ", "EUR");

    when(exchangeClient.getExchangeRate("XYZ", "EUR")).thenThrow(new RuntimeException("Invalid currency"));

    assertThrows(RuntimeException.class, () -> billService.calculatePayableAmount(request));
  }

  private CalculateBillService injectExchangeClient(CalculateBillService service, CurrencyExchangeClient mockClient) {
    try {
      var field = CalculateBillService.class.getDeclaredField("currencyExchangeClient");
      field.setAccessible(true);
      field.set(service, mockClient);
      return service;
    } catch (Exception e) {
      throw new RuntimeException("Failed to inject mock exchange client", e);
    }
  }


}
