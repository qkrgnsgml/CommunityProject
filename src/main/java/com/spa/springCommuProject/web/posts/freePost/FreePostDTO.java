package com.spa.springCommuProject.web.posts.freePost;

import com.spa.springCommuProject.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class FreePostDTO {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private LocalDateTime createdDate;
    private User user;
    public FreePostDTO() {
    }

    public FreePostDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public FreePostDTO(String title, String content, LocalDateTime createdDate, User user) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.user = user;
    }
}
