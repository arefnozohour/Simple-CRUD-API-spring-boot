package com.semtrio.TestTask.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_fk"))
    private User user;

    private String title;

}
