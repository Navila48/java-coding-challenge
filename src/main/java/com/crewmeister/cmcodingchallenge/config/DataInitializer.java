package com.crewmeister.cmcodingchallenge.config;

import com.crewmeister.cmcodingchallenge.currency.CurrencyService;
import com.crewmeister.cmcodingchallenge.exchangeRate.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private ExchangeRateService exchangeRateService;

    @Override
    public void run(String... args) throws Exception {
        currencyService.initializeCurrencies();
        exchangeRateService.initializeExchangeRate();
    }
}
