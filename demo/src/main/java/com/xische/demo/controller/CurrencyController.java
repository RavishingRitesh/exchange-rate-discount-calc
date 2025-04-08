package com.xische.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xische.demo.model.request.DiscountCalculationRequest;
import com.xische.demo.model.response.NetPayableResponse;
import com.xische.demo.service.CalculateBillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * The type Currency controller.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CurrencyController {

  private final CalculateBillService billService;

  /**
   * Calculate payable response entity.
   *
   * @param request the request
   * @return the response entity
   */
  @PostMapping("/calculatePayable")
  public ResponseEntity<NetPayableResponse> calculatePayable(@Valid @RequestBody DiscountCalculationRequest request) {
    return ResponseEntity.ok(billService.calculatePayableAmount(request));
  }
}
