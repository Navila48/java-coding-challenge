package com.crewmeister.cmcodingchallenge.exchangeRate;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    //Example:  http://localhost:8080/api/exchangerate/2025-06-23/USD
    @GetMapping("/exchangerate/{date}/{toCurrencyCode}")
    public ResponseEntity<ExchangeRate> getEuroFxExchangeRateOnDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @PathVariable String toCurrencyCode) {
        ExchangeRate toEuroRate = exchangeRateService.getEuroFxExchangeRateOnDate(date,toCurrencyCode);
        if(toEuroRate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(toEuroRate);
    }

//Example :  http://localhost:8080/api/convert?amount=100&currencyCode=USD&date=2025-06-23
    @GetMapping("/convert")
    public ResponseEntity<BigDecimal> convertAmount(@RequestParam BigDecimal amount,@RequestParam String currencyCode, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        try{
            BigDecimal convertedAmount = exchangeRateService.getConvertedAmountonDate(amount, currencyCode, date);
            return ResponseEntity.ok(convertedAmount);
        } catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch(Exception exp) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,exp.getMessage(),exp);
        }
    }

}
