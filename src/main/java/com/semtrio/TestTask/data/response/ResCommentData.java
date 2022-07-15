package com.semtrio.TestTask.data.response;

import lombok.Data;

@Data
public class ResCommentData {

    private Integer id;

    private Integer postId;

    private String name;

    private String email;

    private String body;
}
