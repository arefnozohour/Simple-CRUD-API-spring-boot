package com.semtrio.TestTask.data.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReqPhotoData {

    @NotNull
    private Integer albumId;

    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    @NotNull
    private String url;
    private String thumbnailUrl;
}
