package com.example.subwhere.utils;

import com.example.subwhere.dto.JsonDto;
import com.example.subwhere.dto.TrainApiDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
public class ApiUtils {
    private static final RestTemplate restTemplate = new RestTemplate();

    public static JsonDto callTrainsApi(URI uri) {

        ResponseEntity<JsonDto> responseEntity = restTemplate.getForEntity(uri, JsonDto.class);
        List<TrainApiDto> trains = responseEntity.getBody().getRealtimePositionList();

        for (TrainApiDto train : trains) {
            log.info("train.statnNm = {}, train.trainSttus = {}, train.statnTnm = {}행, train.updnLine = {}",
                    train.getStatnNm(), train.getTrainSttus(), train.getStatnTnm(), train.getUpdnLine());
        }

        System.out.println();
        return responseEntity.getBody();
    }

}
