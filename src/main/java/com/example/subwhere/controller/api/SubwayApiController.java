package com.example.subwhere.controller.api;

import com.example.subwhere.config.auth.SessionUser;
import com.example.subwhere.service.SubwayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class SubwayApiController {

    private final SubwayService subwayService;

    @DeleteMapping("/user/train/{trainNo}")
    public String deleteMyTrain(@PathVariable String trainNo, HttpSession httpSession) {

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        return subwayService.deleteTrain(trainNo, user.getEmail());
    }

}
