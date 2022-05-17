package com.spa.springCommuProject.domain.post;

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

    private String originalFileName;
    private String saveFileName;
    private String filePath;
    private Long fileSize;

    public Image(String originalFileName, String saveFileName, String filePath, Long fileSize) {
        this.originalFileName = originalFileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public Image() {

    }
}
