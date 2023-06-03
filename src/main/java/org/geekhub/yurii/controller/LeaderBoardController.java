package org.geekhub.yurii.controller;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.user.UserLeaderBoardDTO;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.page.Page;
import org.geekhub.yurii.model.page.Pageable;
import org.geekhub.yurii.service.LeaderBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/leader-board")
@RequiredArgsConstructor
public class LeaderBoardController {

    private final LeaderBoardService leaderBoardService;

    @GetMapping(produces = {"text/html", "application/xhtml+xml"})
    public String getPage() {
        return "leader-board";
    }

    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public Page<UserLeaderBoardDTO> getLeaderboardPage(@RequestParam Discipline discipline,
                                                       @Valid Pageable pageable) {
        return leaderBoardService.getLeaderboardPage(discipline, pageable);
    }
}
