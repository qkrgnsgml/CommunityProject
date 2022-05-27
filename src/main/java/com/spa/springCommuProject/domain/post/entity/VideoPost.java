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
public class VideoPost extends Post{

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<Video> videos = new ArrayList<>();

    public VideoPost() {

    }

    public VideoPost(User user, String title, String content, List<Video> videos) {
        super(user, title, content);
        this.videos = videos;
    }

    public VideoPost(User user, String title, String content) {
        super(user, title, content);
    }
}
