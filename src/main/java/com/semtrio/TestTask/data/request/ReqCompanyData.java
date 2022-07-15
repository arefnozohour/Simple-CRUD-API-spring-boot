package com.semtrio.TestTask.data.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReqCompanyData {

    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String catchPhrase;
    @NotBlank
    @NotNull
    private String bs;
}
