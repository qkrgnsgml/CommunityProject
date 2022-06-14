package com.spa.springCommuProject.domain.post.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Long getPostId(){
        if(this.post==null){
            return 1L;
        }
        return this.post.getId();
    }

    private String uploadFileName;
    private String storeFileName;

    public Image(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public Image(Post post, String uploadFileName, String storeFileName) {
        this.post = post;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }


    public Image() {
    }
}
