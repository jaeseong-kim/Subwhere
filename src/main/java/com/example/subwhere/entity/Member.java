package com.example.subwhere.entity;


import com.example.subwhere.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member {


    @Id
    private String email;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime regDt;

    public Member update(String email, String name){
        this.email = email;
        this.name = name;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
