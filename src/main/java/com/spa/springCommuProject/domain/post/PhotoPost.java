package com.spa.springCommuProject.domain.post;

import com.spa.springCommuProject.domain.user.User;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class PhotoPost extends Post{

    @OneToMany(mappedBy = "post")
    private List<Image> photo = new ArrayList<>();

    public PhotoPost() {

    }

    public PhotoPost(User user, String title, String content) {
        super(user, title, content);
    }
}
