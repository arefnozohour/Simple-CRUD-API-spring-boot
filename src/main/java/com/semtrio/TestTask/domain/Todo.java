package com.semtrio.TestTask.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_fk"))
    private User user;

    @Column(length = 70)
    private String title;

    private Boolean completed;
}
