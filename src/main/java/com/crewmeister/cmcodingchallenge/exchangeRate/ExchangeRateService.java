package com.crewmeister.cmcodingchallenge.exchangeRate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ExchangeRateService {

    private final ExchangeRepository exchangeRepository;
    public List<ExchangeRate> getAllEuroFxExchangeRates() {
        List<ExchangeRate> rates = exchangeRepository.findAll();
        return rates;
    }

    public void initializeExchangeRate(){
        if(exchangeRepository.count() == 0){
            exchangeRepository.saveAll(fetchLatestExchangeRate());
        }
    }
    private Map<String, BigDecimal> getMockExchangeRates() {
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("USD", new BigDecimal("1.0842"));
        rates.put("GBP", new BigDecimal("0.8610"));
        rates.put("JPY", new BigDecimal("156.25"));
        rates.put("CHF", new BigDecimal("0.9565"));
        rates.put("CAD", new BigDecimal("1.4623"));
        rates.put("AUD", new BigDecimal("1.6234"));
        rates.put("SEK", new BigDecimal("11.2156"));
        rates.put("NOK", new BigDecimal("11.4578"));
        rates.put("DKK", new BigDecimal("7.4598"));
        rates.put("PLN", new BigDecimal("4.3421"));
        rates.put("CZK", new BigDecimal("24.6789"));
        rates.put("HUF", new BigDecimal("390.45"));
        return rates;
    }
    public List<ExchangeRate> fetchLatestExchangeRate(){
        Map<String, BigDecimal> rates = getMockExchangeRates();
        List<ExchangeRate> rateList = new ArrayList<>();
        LocalDate currentCycle = LocalDate.now();
        for(Map.Entry<String, BigDecimal> entry : rates.entrySet()){
            rateList.add(new ExchangeRate(entry.getKey(),currentCycle,entry.getValue()));
        }
        return rateList;
    }
}
