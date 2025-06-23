package com.crewmeister.cmcodingchallenge.exchangeRate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRepository extends JpaRepository<ExchangeRate, Integer> {

    List<ExchangeRate> findExchangeRateByCurrencyCode(String currencyCode);

    Optional<ExchangeRate> findByDateAndCurrencyCode(LocalDate date, String currencyCode);
}
