package com.example.subwhere.controller;

import com.example.subwhere.config.auth.SessionUser;
import com.example.subwhere.dto.JsonDto;
import com.example.subwhere.dto.TrainSaveDto;
import com.example.subwhere.dto.TrainViewDto;
import com.example.subwhere.service.SubwayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SubwayController {


    private final SubwayService subwayService;

    @GetMapping("/subway/line")
    public String lineSelect() {
        return "subway/line-select";
    }

    @GetMapping("/subway/line/{lineName}")
    public String trainList(@PathVariable String lineName, Model model) {

        log.info("lineName = {}", lineName);

        model.addAttribute("trains", subwayService.getTrains(lineName));
        model.addAttribute("subwayNm", lineName);

        return "subway/train-list";
    }

    @PostMapping("/user/train")
    public String saveMyTrain(@ModelAttribute TrainSaveDto train, HttpSession httpSession) {

        log.info("train.trainNo = {}, train.subwayNm = {}, httpSession.user = {}", train.getTrainNo(), train.getSubwayNm(),
                httpSession.getAttribute("user"));

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(!subwayService.alreadySaved(train.getTrainNo(), user.getEmail())){
            subwayService.saveTrain(train, user.getEmail());
        }

        return "redirect:/user/train";
    }

    @GetMapping("/user/train")
    public String myTrainPage(Model model, HttpSession httpSession) {

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        List<String> myLineNames = subwayService.getMyLineName(user.getEmail());

        List<TrainViewDto> allTrains = new ArrayList<>();
        for (String lineName : myLineNames) {
            List<TrainViewDto> trains = subwayService.getTrains(lineName);
            allTrains.addAll(trains);
        }

        List<TrainViewDto> myTrains = subwayService.findMyTrainsFromAllTrains(allTrains, user.getEmail());

        model.addAttribute("myTrains", myTrains);

        return "/user/my-train";
    }
}
