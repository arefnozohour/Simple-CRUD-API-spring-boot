package com.semtrio.TestTask.domain.embedded;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class Company {
    @Column(length = 20,name = "company_name")
    private String name;
    private String catchPhrase;
    private String bs;
}
