package com.semtrio.TestTask.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ServiceException extends RuntimeException {

    private Integer status;
    private ExceptionData exceptionData;

    public ServiceException(Integer status) {
        super();
        this.status = status;
    }

    public ServiceException(ExceptionData exceptionData)
    {
        super(exceptionData.message);
        this.status=exceptionData.number;
        this.exceptionData=exceptionData;
    }

    public ServiceException(Integer status, String message) {
        super(message);
        this.status = status;
    }
}
