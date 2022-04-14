package com.spa.springCommuProject.web.user;

import lombok.Data;

@Data
public class UserJoinDTO {
    private String nickName;

    private String loginId;
    private String password;
}
