package com.crewmeister.cmcodingchallenge.exchangeRate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    //Example: http://localhost:8080/api/exchangerates/all
    @GetMapping("/exchangerates/all")
    public ResponseEntity<List<ExchangeRate>> getAllEuroFxExchangeRates() {
        List<ExchangeRate> rates = exchangeRateService.getAllEuroFxExchangeRates();
        if(rates == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(rates);
    }

}
