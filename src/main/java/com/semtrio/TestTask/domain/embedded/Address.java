package com.semtrio.TestTask.domain.embedded;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Data
@Embeddable
public class Address {

    @Column(length = 20)
    private String street;
    @Column(length = 20)
    private String suite;
    @Column(length = 20)
    private String city;
    @Column(length = 20)
    private String zipcode;

    @Embedded
    private Geo geo;



}
