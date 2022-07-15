package com.semtrio.TestTask.data.response;

import lombok.Data;

import java.util.List;

@Data
public class ResLoginData {

    private String accessToken;
    private List<String> roles;

    public ResLoginData(String accessToken) {
        this.accessToken = accessToken;
    }
}
