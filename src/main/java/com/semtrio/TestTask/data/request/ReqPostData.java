package com.semtrio.TestTask.data.request;

import com.semtrio.TestTask.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReqPostData {


    @NotNull
    private Integer userId;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String body;
}
