package com.semtrio.TestTask.data.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class ReqCommentData {

    @NotNull
    private Integer postId;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String body;
}
