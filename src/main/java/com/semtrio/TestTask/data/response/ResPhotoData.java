package com.semtrio.TestTask.data.response;

import lombok.Data;

@Data
public class ResPhotoData {

    private Integer id;
    private Integer albumId;

    private String title;
    private String url;
    private String thumbnailUrl;
}
