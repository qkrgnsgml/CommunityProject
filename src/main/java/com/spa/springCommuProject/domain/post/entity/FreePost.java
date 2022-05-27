package com.spa.springCommuProject.domain.post.entity;

import com.spa.springCommuProject.domain.user.User;
import lombok.Getter;

import javax.persistence.*;


@Getter
@Entity
public class FreePost extends Post {

    public FreePost() {

    }

    public FreePost(User user, String title, String content) {
        super(user, title, content);
    }


}
