package com.semtrio.TestTask.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "album_id",referencedColumnName = "id", foreignKey = @ForeignKey(name = "album_fk"))
    private Album album;

    @Column(length = 50)
    private String title;
    @Column(length = 100)
    private String url;
    @Column(length = 100)
    private String thumbnailUrl;
}
