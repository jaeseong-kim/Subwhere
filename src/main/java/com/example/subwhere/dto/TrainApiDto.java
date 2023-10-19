package com.example.subwhere.dto;

import com.example.subwhere.type.SUBWAY_MAP;
import com.example.subwhere.utils.SubwayUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
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

    public TrainViewDto toTrainViewDto() {

        SUBWAY_MAP LINE = SubwayUtils.getLineMap(subwayNm, statnNm, statnTnm, updnLine);
        List<String> lineMap = LINE.getMap();

        String prevStatnNm = "";
        String curStatnNm = "";
        String nextStatnNm = "";

        int index = lineMap.indexOf(statnNm);

        if (trainSttus == 0 || trainSttus == 1) {
            log.info("index = {}, trainApi.getStatnNm = {}", index, statnNm);

            if (index == 0) {

                if (updnLine == 0) {

                    prevStatnNm = lineMap.get(lineMap.indexOf(statnNm) + 1);
                    curStatnNm = statnNm;
                    nextStatnNm = "종점";

                } else {

                    prevStatnNm = "출발점";
                    curStatnNm = statnNm;
                    nextStatnNm = lineMap.get(lineMap.indexOf(statnNm) + 1);

                }

            } else if (index == lineMap.size() - 1) {

                if (updnLine == 0) {

                    prevStatnNm = "출발점";
                    curStatnNm = statnNm;
                    nextStatnNm = lineMap.get(lineMap.indexOf(statnNm) - 1);

                } else {

                    prevStatnNm = lineMap.get(lineMap.indexOf(statnNm) - 1);
                    curStatnNm = statnNm;
                    nextStatnNm = "종점";

                }

            } else {

                if (updnLine == 0) {

                    prevStatnNm = lineMap.get(lineMap.indexOf(statnNm) + 1);
                    curStatnNm = statnNm;
                    nextStatnNm = lineMap.get(lineMap.indexOf(statnNm) - 1);

                } else {

                    prevStatnNm = lineMap.get(lineMap.indexOf(statnNm) - 1);
                    curStatnNm = statnNm;
                    nextStatnNm = lineMap.get(lineMap.indexOf(statnNm) + 1);

                }

            }

        } else if (trainSttus == 2) {

            if (updnLine == 0) {

                prevStatnNm = statnNm;
                curStatnNm = "이동중";
                nextStatnNm = lineMap.get(lineMap.indexOf(statnNm) - 1 < 0 ?
                        0 : lineMap.indexOf(statnNm) - 1);

            } else {

                prevStatnNm = statnNm;
                curStatnNm = "이동중";
                nextStatnNm = lineMap.get(lineMap.indexOf(statnNm) + 1 >= lineMap.size() ?
                        lineMap.indexOf(statnNm) : lineMap.indexOf(statnNm) + 1);

            }

        } else {

            if (index == 0) {

                if (updnLine == 0) {

                    prevStatnNm = lineMap.get(lineMap.indexOf(statnNm) + 1);
                    curStatnNm = "이동중";
                    nextStatnNm = statnNm;

                } else {

                    prevStatnNm = "출발점";
                    curStatnNm = "이동중";
                    nextStatnNm = statnNm;
                }
            } else if (index == lineMap.size() - 1) {

                if (updnLine == 0) {

                    prevStatnNm = "출발점";
                    curStatnNm = "이동중";
                    nextStatnNm = statnNm;

                } else {

                    prevStatnNm = lineMap.get(lineMap.indexOf(statnNm) - 1);
                    curStatnNm = "이동중";
                    nextStatnNm = statnNm;
                }

            } else {

                if (updnLine == 0) {
                    prevStatnNm = lineMap.get(lineMap.indexOf(statnNm) + 1);
                    curStatnNm = "이동중";
                    nextStatnNm = statnNm;
                } else {
                    prevStatnNm = lineMap.get(lineMap.indexOf(statnNm) - 1);
                    curStatnNm = "이동중";
                    nextStatnNm = statnNm;
                }
            }


        }

        String direct = (directAt == 0 ? "일반" : (directAt == 1 ? "급행" : "특급"));
        String isLast = (lstcarAt == 0 ? " " : "막차");

        return TrainViewDto.builder()
                .trainNo(trainNo)
                .subwayNm(subwayNm)
                .prevStatnNm(prevStatnNm)
                .statnNm(curStatnNm)
                .nextStatnNm(nextStatnNm)
                .statnTnm(statnTnm + "행")
                .directAt(direct)
                .lstcarAt(isLast)
                .build();
    }

}
