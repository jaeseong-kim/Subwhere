package com.example.subwhere.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class ErrorMessage {
    private int status;
    private String code;
    private String message;
    private int total;
}
