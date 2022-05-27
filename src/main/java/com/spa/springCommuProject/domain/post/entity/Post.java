package com.spa.springCommuProject.domain.post.entity;

import com.spa.springCommuProject.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@DiscriminatorColumn // 하위 테이블의 구분 컬럼 생성(default = DTYPE)
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void createPost(User user) {
        this.user = user;
        //user.getPosts().add(this); //연관관계
    }

    public Post() {
    }

    public Post(User user, String title, String content) {
        this.available = true;
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.createdDate = LocalDateTime.parse(nowTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.view = 0;
        this.user = user;
        this.title = title;
        this.content = content;
    }

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private String title;
    private String content;

    private Boolean available;
    private int view;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.modifiedDate = LocalDateTime.parse(nowTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void delete(){
        this.available = false;
    }

    public void viewIncrease(){
        this.view++;
    }


}
