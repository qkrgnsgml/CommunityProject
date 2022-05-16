package com.spa.springCommuProject.domain.post;

import lombok.Getter;

import javax.persistence.Entity;

@Getter
@Entity
public class PhotoPost extends Post{

    private String uploadFileName;
    private String storeFileName;

}
