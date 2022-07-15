package com.semtrio.TestTask.data.request;

import com.semtrio.TestTask.domain.embedded.Geo;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReqAddressData {

    @NotBlank
    @NotNull
    private String street;
    @NotBlank
    @NotNull
    private String suite;
    @NotBlank
    @NotNull
    private String city;
    @NotBlank
    @NotNull
    private String zipcode;

    private Geo geo;
}
