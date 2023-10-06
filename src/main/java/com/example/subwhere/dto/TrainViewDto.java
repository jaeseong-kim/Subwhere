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

    public TrainViewDto toTrainViewDto(TrainApiDto trainApi) {

        String lineName = trainApi.getSubwayNm();
        String statnNm = trainApi.getStatnNm();
        String statnTnm = trainApi.getStatnTnm();
        int updnLine = trainApi.getUpdnLine();

        SUBWAY_MAP LINE = SubwayUtils.getLineMap(lineName, statnNm, statnTnm, updnLine);
        List<String> lineMap = LINE.getMap();

        String prevStatnNm = "";
        String curStatnNm = "";
        String nextStatnNm = "";

        int index = lineMap.indexOf(trainApi.getStatnNm());

        if (trainApi.getTrainSttus() == 0 || trainApi.getTrainSttus() == 1) {
            log.info("index = {}, trainApi.getStatnNm = {}", index, trainApi.getStatnNm());

            if (index == 0) {

                if (trainApi.getUpdnLine() == 0) {

                    prevStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) + 1);
                    curStatnNm = trainApi.getStatnNm();
                    nextStatnNm = "종점";

                } else {

                    prevStatnNm = "출발점";
                    curStatnNm = trainApi.getStatnNm();
                    nextStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) + 1);

                }

            } else if (index == lineMap.size() - 1) {

                if (trainApi.getUpdnLine() == 0) {

                    prevStatnNm = "출발점";
                    curStatnNm = trainApi.getStatnNm();
                    nextStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) - 1);

                } else {

                    prevStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) - 1);
                    curStatnNm = trainApi.getStatnNm();
                    nextStatnNm = "종점";

                }

            } else {

                if (trainApi.getUpdnLine() == 0) {

                    prevStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) + 1);
                    curStatnNm = trainApi.getStatnNm();
                    nextStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) - 1);

                } else {

                    prevStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) - 1);
                    curStatnNm = trainApi.getStatnNm();
                    nextStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) + 1);

                }

            }

        } else if (trainApi.getTrainSttus() == 2) {

            if (trainApi.getUpdnLine() == 0) {

                prevStatnNm = trainApi.getStatnNm();
                curStatnNm = "이동중";
                nextStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) - 1 < 0 ?
                        0 : lineMap.indexOf(trainApi.getStatnNm()) - 1);

            } else {

                prevStatnNm = trainApi.getStatnNm();
                curStatnNm = "이동중";
                nextStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) + 1 >= lineMap.size() ?
                        lineMap.indexOf(trainApi.getStatnNm()) : lineMap.indexOf(trainApi.getStatnNm()) + 1);

            }

        } else {

            if (index == 0) {

                if (trainApi.getUpdnLine() == 0) {

                    prevStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) + 1);
                    curStatnNm = "이동중";
                    nextStatnNm = trainApi.getStatnNm();

                } else {

                    prevStatnNm = "출발점";
                    curStatnNm = "이동중";
                    nextStatnNm = trainApi.getStatnNm();
                }
            } else if (index == lineMap.size() - 1) {

                if (trainApi.getUpdnLine() == 0) {

                    prevStatnNm = "출발점";
                    curStatnNm = "이동중";
                    nextStatnNm = trainApi.getStatnNm();

                } else {

                    prevStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) - 1);
                    curStatnNm = "이동중";
                    nextStatnNm = trainApi.getStatnNm();
                }

            } else {

                if (trainApi.getUpdnLine() == 0) {
                    prevStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) + 1);
                    curStatnNm = "이동중";
                    nextStatnNm = trainApi.getStatnNm();
                } else {
                    prevStatnNm = lineMap.get(lineMap.indexOf(trainApi.getStatnNm()) - 1);
                    curStatnNm = "이동중";
                    nextStatnNm = trainApi.getStatnNm();
                }
            }


        }

        String directAt = (trainApi.getDirectAt() == 0 ? "일반" : (trainApi.getDirectAt() == 1 ? "급행" : "특급"));
        String lstcarAt = (trainApi.getLstcarAt() == 0 ? " " : "막차");

        return TrainViewDto.builder()
                .trainNo(trainApi.getTrainNo())
                .subwayNm(trainApi.getSubwayNm())
                .prevStatnNm(prevStatnNm)
                .statnNm(curStatnNm)
                .nextStatnNm(nextStatnNm)
                .statnTnm(trainApi.getStatnTnm() + "행")
                .directAt(directAt)
                .lstcarAt(lstcarAt)
                .build();
    }


}
