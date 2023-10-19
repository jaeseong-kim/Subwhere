package com.example.subwhere.dto;

import com.example.subwhere.type.SUBWAY_MAP;
import com.example.subwhere.utils.SubwayUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TrainViewDto {

    // 열차번호
    private String trainNo;

    // 지하철 호선명
    private String subwayNm;

    // 이전 역명
    private String prevStatnNm;

    // 현재 지하철역명
    private String statnNm;

    // 다음 역명
    private String nextStatnNm;

    // 종착 지하철명
    private String statnTnm;

    // 급행 여부 1:급행, 0:아님, 7:특급
    private String directAt;

    // 막차여부 1:막차, 0:아님
    private String lstcarAt;

}
