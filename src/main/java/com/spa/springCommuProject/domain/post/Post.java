package com.spa.springCommuProject.domain.post;

import com.spa.springCommuProject.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private String title;
    private String content;

    private Boolean available;
    private int view;
}
