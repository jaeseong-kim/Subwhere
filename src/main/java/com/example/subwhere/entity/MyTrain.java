package com.example.subwhere.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@NoArgsConstructor
@Entity
public class MyTrain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    private String trainNo;
    private String email;

    private String subwayNm;

    @Builder
    public MyTrain(String trainNo, String email, String subwayNm) {
        this.trainNo = trainNo;
        this.email = email;
        this.subwayNm = subwayNm;
    }


    public String getSubwayNm() {
        return subwayNm;
    }

    public String getTrainNumber() {
        return this.trainNo;
    }
}
