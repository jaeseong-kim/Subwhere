package com.example.subwhere.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
@NoArgsConstructor
public class JsonDto {
    private ErrorMessage errorMessage;
    private ArrayList<TrainApiDto> realtimePositionList;

    public boolean isOk(){
        return errorMessage.getCode().equals("INFO-000");
    }
}
