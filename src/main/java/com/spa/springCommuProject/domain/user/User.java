package com.spa.springCommuProject.domain.user;

import com.spa.springCommuProject.domain.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String nickName;

    private String loginId;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private bigThreePower bigThreePower;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @Override
    public String toString() {
        return super.toString();
    }
}
