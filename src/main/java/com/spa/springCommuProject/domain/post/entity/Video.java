package com.spa.springCommuProject.domain.post.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Video {

    @Id
    @GeneratedValue
    @Column(name = "video_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String uploadFileName;
    private String storeFileName;

    public Video(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public Video(Post post, String uploadFileName, String storeFileName) {
        this.post = post;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }


    public Video() {
    }
}
