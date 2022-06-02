package com.spa.springCommuProject.web.posts.photopost;

import com.spa.springCommuProject.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PhotoPostDTO {


    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private LocalDateTime createdDate;
    private User user;

    private List<MultipartFile> imageFiles;

    public PhotoPostDTO() {
    }

    public PhotoPostDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PhotoPostDTO(String title, String content, LocalDateTime createdDate, User user) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.user = user;
    }
}
