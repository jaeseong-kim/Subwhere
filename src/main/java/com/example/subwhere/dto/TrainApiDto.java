package com.example.subwhere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TrainApiDto {

    // 지하철 호선 ID
    private int subwayId;

    // 지하철 호선명
    private String subwayNm;

    // 지하철역 ID
    private int statnId;

    // 지하철역명
    private String statnNm;

    // 열차번호
    private String trainNo;

    // 최종수신날짜
    private String lastRecptnDt;

    // 최종수신시간
    private String recptnDt;

    // 상하행선구분 0 : 상행/내선, 1 : 하행/외선
    private int updnLine;

    // 종착 지하철역 ID
    private int statnTid;

    // 종착지하철명
    private String statnTnm;

    // 열차상태구분 0:진입, 1:도착, 2:출발, 3:전역출발
    private int trainSttus;

    // 급행 여부 1:급행, 0:아님, 7:특급
    private int directAt;

    // 막차여부 1:막차, 0:아님
    private int lstcarAt;

    public void removeStatnNmParenthesis() {

        if (!statnNm.contains("(")) {
            return;
        }

        statnNm = statnNm.substring(0, statnNm.indexOf("("));
    }

}
