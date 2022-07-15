package com.semtrio.TestTask.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "post_id",referencedColumnName = "id", foreignKey = @ForeignKey(name = "post_fk"))
    private Post post;

    @Column(length = 100)
    private String name;

    @Column(length = 50)
    private String email;

    @Column(columnDefinition="TEXT")
    private String body;
}
