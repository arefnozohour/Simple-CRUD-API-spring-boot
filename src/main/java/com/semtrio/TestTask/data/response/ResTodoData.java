package com.semtrio.TestTask.data.response;

import com.semtrio.TestTask.domain.User;
import lombok.Data;


@Data
public class ResTodoData {

    private Integer id;

    private Integer userId;

    private String title;

    private Boolean completed;
}
