package com.semtrio.TestTask.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_fk"))
    private User user;

    private String title;

    @Column(columnDefinition="TEXT")
    private String body;
}
