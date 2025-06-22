package com.crewmeister.cmcodingchallenge.exchangeRate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "exchange_rate")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @Column(name = "rate_date", nullable = false)
    private LocalDate date;

    @Column(name = "rate", nullable = false, precision = 10, scale = 6)
    private BigDecimal rate;

    public ExchangeRate(String currencyCode, LocalDate date, BigDecimal rate) {
        this.currencyCode = currencyCode;
        this.date = date;
        this.rate = rate;
    }
}
