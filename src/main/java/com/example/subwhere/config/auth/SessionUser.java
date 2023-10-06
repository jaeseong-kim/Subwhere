package com.example.subwhere.config.auth;

import com.example.subwhere.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String email;
    private String name;

    public SessionUser(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
    }

}
