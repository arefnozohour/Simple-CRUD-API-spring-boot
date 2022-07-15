package com.semtrio.TestTask.domain.embedded;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Geo {

    private Float lat;
    private Float lng;
}
