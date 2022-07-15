package com.semtrio.TestTask.data.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReqAlbumData {


    @NotNull
    private Integer userId;

    @NotNull
    @NotBlank
    private String title;

}
