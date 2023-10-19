package com.example.subwhere.service;

import com.example.subwhere.dto.JsonDto;
import com.example.subwhere.dto.TrainApiDto;
import com.example.subwhere.dto.TrainSaveDto;
import com.example.subwhere.dto.TrainViewDto;
import com.example.subwhere.entity.MyTrain;
import com.example.subwhere.repository.MyTrainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubwayService {

    @Value("${api.key}")
    private String key;


    private final MyTrainRepository myTrainRepository;

    public List<TrainViewDto> getTrains(String lineName) {

        List<TrainApiDto> trainsApi = callTrainsFromOpenAPI(lineName);

        if (trainsApi == null) {
            return null;
        }

        return trainsApi.stream()
                .map(trainApi -> {
                        trainApi.removeStatnNmParenthesis();
                        return trainApi.toTrainViewDto();
                    })
                .collect(Collectors.toList());
    }


    public void saveTrain(TrainSaveDto train, String email) {

        myTrainRepository.save(MyTrain.builder()
                .trainNo(train.getTrainNo())
                .email(email)
                .subwayNm(train.getSubwayNm())
                .build());
    }

    public boolean alreadySaved(String trainNo, String email) {
        return myTrainRepository.existsByTrainNoAndEmail(trainNo, email);
    }

    public List<String> getMyLineName(String email) {
        List<MyTrain> myTrains = myTrainRepository.findAllByEmail(email);

        List<String> lineNames = new ArrayList<>();
        for (MyTrain myTrain : myTrains) {
            String subwayNm = myTrain.getSubwayNm();

            if (lineNames.contains(subwayNm)) {
                continue;
            }

            lineNames.add(subwayNm);
        }

        return lineNames;
    }

    public List<TrainViewDto> findMyTrainsFromAllTrains(List<TrainViewDto> allTrains, String email) {

        // List<MyTrain> -> List<String>, MyTrain 리스트를 TrainNo만 뽑은 리스트로
        List<String> myTrainNos = myTrainRepository.findAllByEmail(email)
                .stream().map(MyTrain::getTrainNumber).collect(Collectors.toList());

        List<TrainViewDto> result = new ArrayList<>();
        for (String myTrainNo : myTrainNos) {
            for (TrainViewDto train : allTrains) {
                if (myTrainNo.equals(train.getTrainNo())) {
                    result.add(train);
                }
            }
        }

        return result;
    }

    private List<TrainApiDto> callTrainsFromOpenAPI(String lineName) {

        RestTemplate restTemplate = new RestTemplate();
        String host = "http://swopenAPI.seoul.go.kr";
        String path = "/api/subway/" + key + "/json/realtimePosition/1/150/";

        URI uri = UriComponentsBuilder
                .fromUriString(host)
                .path(path + lineName)
                .encode()
                .build()
                .toUri();

        ResponseEntity<JsonDto> responseEntity = restTemplate.getForEntity(uri, JsonDto.class);
        List<TrainApiDto> trains = responseEntity.getBody().getRealtimePositionList();

        for (TrainApiDto train : trains) {
            log.info("train.trainNo = {}, train.statnNm = {}, train.trainSttus = {}, train.statnTnm = {}행, train.updnLine = {}",
                    train.getTrainNo(), train.getStatnNm(), train.getTrainSttus(), train.getStatnTnm(), train.getUpdnLine());
        }


        if (!responseEntity.getBody().isOk()) {
            return null;
        }

        return responseEntity.getBody().getRealtimePositionList();
    }


    public String deleteTrain(String trainNo, String email) {
        Optional<MyTrain> optMyTrain = myTrainRepository.findByTrainNoAndEmail(trainNo, email);
        if (optMyTrain.isEmpty()) {
            return "선택한 지하철이 없습니다.";
        }

        myTrainRepository.delete(optMyTrain.get());

        return "선택한 지하철에서 내렸습니다.";
    }
}
