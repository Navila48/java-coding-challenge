package com.crewmeister.cmcodingchallenge.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public List<Currency> getAllAvailableCurrencies() {
        return currencyRepository.findAll();
    }

    public void initializeCurrencies() {
        if (currencyRepository.count() == 0) {
            createDefaultCurrencies();
        }
    }

    private void createDefaultCurrencies() {
        List<Currency> currencies = List.of(
                new Currency("USD", "US Dollar"),
                new Currency("GBP", "British Pound"),
                new Currency("JPY", "Japanese Yen"),
                new Currency("CHF", "Swiss Franc"),
                new Currency("CAD", "Canadian Dollar"),
                new Currency("AUD", "Australian Dollar"),
                new Currency("SEK", "Swedish Krona"),
                new Currency("NOK", "Norwegian Krone"),
                new Currency("DKK", "Danish Krone"),
                new Currency("PLN", "Polish Zloty"),
                new Currency("CZK", "Czech Koruna"),
                new Currency("HUF", "Hungarian Forint")
        );

        currencyRepository.saveAll(currencies);
    }
}
