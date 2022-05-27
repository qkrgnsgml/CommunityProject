package com.spa.springCommuProject.domain.post.entity;

import com.spa.springCommuProject.domain.user.User;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class PhotoPost extends Post{

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<Image> photo = new ArrayList<>();

    public PhotoPost() {

    }

    public PhotoPost(User user, String title, String content, List<Image> photo) {
        super(user, title, content);
        this.photo = photo;
    }

    public PhotoPost(User user, String title, String content) {
        super(user, title, content);
    }
}
