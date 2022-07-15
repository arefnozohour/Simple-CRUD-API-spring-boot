package com.semtrio.TestTask.exception;

import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExceptionResponseModel {

    private Integer errorNumber;
    private ExceptionData errorCode;
    private String errorMessage;
    private Long timestamp;
    private List<ValidExceptionModel> validationErrors;

    public ExceptionResponseModel(Integer errorNumber, String  errorMessage) {
        this.errorNumber=errorNumber;
        this.errorMessage = errorMessage;
        this.timestamp=System.currentTimeMillis();
    }
    public ExceptionResponseModel(ExceptionData exceptionData) {
        this.errorNumber=exceptionData.number;
        this.errorMessage = exceptionData.message;
        this.errorCode= exceptionData;
        this.timestamp=System.currentTimeMillis();
    }
    public ExceptionResponseModel(BindingResult exception)
    {
        this.errorCode= ExceptionData.invalid_ERROR;
        this.validationErrors=new ArrayList<ValidExceptionModel>();
        for (ObjectError er : exception.getAllErrors()) {
            FieldError fl = (FieldError) er;
            validationErrors.add(new ValidExceptionModel(fl.getField(), fl.getDefaultMessage(),fl.toString()));
        }
        this.timestamp=System.currentTimeMillis();
    }
}
@Data
class ValidExceptionModel
{
    private String name;
    private String summary;
    private String detail;

    public ValidExceptionModel(String name, String summary, String detail) {
        this.name = name;
        this.summary = summary;
        this.detail = detail;
    }
}