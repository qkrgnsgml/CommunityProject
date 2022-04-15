package com.spa.springCommuProject.domain.user;

import com.spa.springCommuProject.domain.post.Post;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String nickName;

    @Column(unique = true)
    private String loginId;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private bigThreePower bigThreePower;

    //@OneToMany(mappedBy = "user")
    //private List<Post> posts = new ArrayList<>();


    @Override
    public String toString() {
        return super.toString();
    }

    protected User() {
    }

    public User(String nickName, String loginId, String password) {
        this.nickName = nickName;
        this.loginId = loginId;
        this.password = password;
        this.role = Role.USER; //default값
        this.bigThreePower = new bigThreePower(0,0,0); //default값
    }

}
