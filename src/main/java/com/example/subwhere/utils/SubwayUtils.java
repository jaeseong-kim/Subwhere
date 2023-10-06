package com.example.subwhere.utils;

import com.example.subwhere.type.SUBWAY_MAP;

import java.util.ArrayList;
import java.util.List;

import static com.example.subwhere.type.SUBWAY_MAP.*;


public class SubwayUtils {
    public static SUBWAY_MAP getLineMap(String lineName, String statnNm, String statnTnm, int updnLine) {

        if (lineName.equals("1호선")) {

            if (updnLine == 1) {

                if (statnTnm.equals("천안") || statnTnm.equals("신창")) {
                    return LINE_1_SINCHANG;
                } else if (statnTnm.equals("광명")) {
                    return LINE_1_KWANGMYEONG;
                } else if (statnTnm.equals("병점") || statnTnm.equals("서동탄")) {
                    return LINE_1_SEODONGTAN;
                } else {
                    return LINE_1_INCHEON;
                }

            } else {
                // 상행선인 경우 출발지가 어디인지 모르니 1호선 노선 모두 브루트 포스
                List<SUBWAY_MAP> lines = new ArrayList<>(List.of(LINE_1_INCHEON, LINE_1_KWANGMYEONG, LINE_1_SEODONGTAN, LINE_1_SINCHANG));

                for (SUBWAY_MAP line : lines) {
                    SUBWAY_MAP line1 = findMap(statnNm, line);

                    if (line1 != null) {
                        return line1;
                    }
                }
            }

        } else if (lineName.equals("2호선")) {

            if (statnTnm.equals("신설동") || statnTnm.equals("성수지선")) {
                return LINE_2_SEONGSUJISEON_SINSEOLDONG;
            } else if (statnTnm.equals("까치산") || statnTnm.equals("신도림지선")) {
                return LINE_2_SINDORIM_KKACHISAN;
            } else {
                return LINE_2_SEONGSU_END;
            }

        } else if (lineName.equals("3호선")) {
            return LINE_3;
        } else if (lineName.equals("4호선")) {
            return LINE_4;
        } else if (lineName.equals("5호선")) {

            if (updnLine == 0) {

                List<SUBWAY_MAP> lines = new ArrayList<>(List.of(LINE_5_HANAMGEOMDANSAN, LINE_5_MACHEON));
                for (SUBWAY_MAP line : lines) {
                    SUBWAY_MAP line5 = findMap(statnNm, line);
                    if (line5 != null) {
                        return line5;
                    }
                }

            } else {

                if (statnTnm.equals("하남검단산") || statnTnm.equals("상일동")) {
                    return LINE_5_HANAMGEOMDANSAN;
                } else {
                    return LINE_5_MACHEON;
                }
            }

        } else if (lineName.equals("6호선")) {
            return LINE_6;
        } else if (lineName.equals("7호선")) {
            return LINE_7;
        } else if (lineName.equals("8호선")) {
            return LINE_8;
        } else if (lineName.equals("9호선")) {
            return LINE_9;
        } else if (lineName.equals("경의중앙선")) {

            if (updnLine == 0) {

                List<SUBWAY_MAP> lines = new ArrayList<>(List.of(LINE_KYEONGUI_SEOUL, LINE_KYEONGUI_JIPYEONG));
                for (SUBWAY_MAP line : lines) {
                    SUBWAY_MAP lineKyeongui = findMap(statnNm, line);
                    if (lineKyeongui != null) {
                        return lineKyeongui;
                    }
                }
            } else {

                if (statnTnm.equals("서울")) {
                    return LINE_KYEONGUI_SEOUL;
                }

                return LINE_KYEONGUI_JIPYEONG;
            }
        } else if (lineName.equals("경춘선")) {

            if (updnLine == 0) {

                if (statnTnm.equals("광운대")) {
                    return LINE_KYEONGCHUN_KWANGWOONDAE;
                }
                return LINE_KYEONGCHUN_CHEONGNYANGNI;

            } else {

                List<SUBWAY_MAP> lines = new ArrayList<>(List.of(LINE_KYEONGCHUN_KWANGWOONDAE, LINE_KYEONGCHUN_CHEONGNYANGNI));
                for (SUBWAY_MAP line : lines) {
                    SUBWAY_MAP lineKyeongchun = findMap(statnNm, line);
                    if (lineKyeongchun != null) {
                        return lineKyeongchun;
                    }
                }
            }

        } else if (lineName.equals("수인분당선")) {
            return LINE_SUINBUNDANG;
        } else if (lineName.equals("신분당선")) {
            return LINE_SINBUNDANG;
        } else if (lineName.equals("경강선")) {
            return LINE_GYEONGGANG;
        } else if (lineName.equals("우이신설선")) {
            return LINE_UISINSEOL;
        } else if (lineName.equals("서해선")) {
            return LINE_SEOHAE;
        } else {
            return LINE_AREX;
        }

        throw new RuntimeException("노선을 발견하지 못했습니다.");
    }

    private static SUBWAY_MAP findMap(String statnNm, SUBWAY_MAP LINE) {

        for (String station : LINE.getMap()) {
            if (statnNm.equals(station)) {
                return LINE;
            }
        }

        return null;
    }

}
