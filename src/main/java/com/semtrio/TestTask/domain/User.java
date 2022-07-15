package com.semtrio.TestTask.domain;

import com.semtrio.TestTask.domain.embedded.Address;
import com.semtrio.TestTask.domain.embedded.Company;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "users",
        uniqueConstraints = {
            @UniqueConstraint(name = "username_unique", columnNames = {"username"}),
            @UniqueConstraint(name = "email_unique", columnNames = {"email"}),
            @UniqueConstraint(name = "phone_unique", columnNames = {"phone"}),
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String username;
    @Column(length = 50)
    private String email;
    @Column(length = 30)
    private String phone;

    @Column(length = 15)
    private String website;

    @Embedded
    private Address address;
    @Embedded
    private Company company;

}
