package com.spa.springCommuProject.web.freePost;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FreePostDTO {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public FreePostDTO() {
    }

    public FreePostDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
