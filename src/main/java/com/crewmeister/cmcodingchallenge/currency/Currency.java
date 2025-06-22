package com.crewmeister.cmcodingchallenge.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @Column(name = "code", nullable = false, unique = true, updatable = false, length = 3 )
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
}
