package com.semtrio.TestTask.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ReqLoginData {

    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String password;
}
