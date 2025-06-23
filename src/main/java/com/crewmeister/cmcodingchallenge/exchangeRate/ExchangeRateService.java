package com.crewmeister.cmcodingchallenge.exchangeRate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
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

    public ExchangeRate getEuroFxExchangeRateOnDate(LocalDate date, String toCurrency) {
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Exchange rate is not available for future date: " + date);
        }
        ExchangeRate rateOnDate = exchangeRepository.findByDateAndCurrencyCode(date, toCurrency)
                .orElseThrow(() -> new RuntimeException("No rate found on this date"));
        return rateOnDate;
    }
    public BigDecimal getConvertedAmountonDate(BigDecimal amount, String currencyCode, LocalDate date) {

        if("EUR".equalsIgnoreCase(currencyCode)) {
            return amount;
        }
        ExchangeRate rate = getEuroFxExchangeRateOnDate(date, currencyCode);
        return amount.divide(rate.getRate(), 6, BigDecimal.ROUND_HALF_UP);
    }

    public void initializeExchangeRate(){
        long count = exchangeRepository.count();
        if(count == 0){
            List<ExchangeRate> rates = fetchLatestExchangeRate();
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(7);
            List<ExchangeRate> historicalRates = fetchHistoricalExchangeRate(startDate, endDate);

            if ( historicalRates != null) {
                rates.addAll(historicalRates);
            }

            try {
                exchangeRepository.saveAll(rates);
                System.out.println("Saved " + rates.size() + " rates");
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public List<ExchangeRate> fetchHistoricalExchangeRate(LocalDate startDate, LocalDate endDate) {
        Map<String, BigDecimal> rates = getMockExchangeRates();
        List<ExchangeRate> historicRateList = new ArrayList<>();
        while(startDate.isBefore(endDate)){

            if(startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY){
                startDate = startDate.plusDays(1);
                continue; //skip weekends for more realistic data
            }
            for(Map.Entry<String, BigDecimal> entry : rates.entrySet()){
                double variation = 0.95 + (Math.random() * 0.1); //ranodom() = [0.0,1)
                BigDecimal rate = entry.getValue().multiply(BigDecimal.valueOf(variation));

                historicRateList.add(new ExchangeRate(entry.getKey(),startDate,rate));
            }
            startDate = startDate.plusDays(1);
        }
        return historicRateList;
    }

}
