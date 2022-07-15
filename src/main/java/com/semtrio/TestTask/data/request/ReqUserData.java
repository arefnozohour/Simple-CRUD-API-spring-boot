package com.semtrio.TestTask.data.request;

import com.semtrio.TestTask.domain.embedded.Address;
import com.semtrio.TestTask.domain.embedded.Company;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.*;

@Data
public class ReqUserData {

    private String name;

    @NotBlank
    @NotNull
    private String username;
    @Email
    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    @Length(min = 6)
    private String phone;

    private String website;

    private Address address;
    private Company company;
}
