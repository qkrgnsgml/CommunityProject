package com.spa.springCommuProject.domain.post.freepost;

import com.spa.springCommuProject.domain.post.Post;
import com.spa.springCommuProject.domain.user.User;
import lombok.Getter;

import javax.persistence.*;


@Getter
public class FreePost extends Post {

    public FreePost() {

    }

    public FreePost(User user, String title, String content) {
        super(user, title, content);
    }
}
