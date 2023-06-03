package org.geekhub.yurii.controller.solve;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.model.solve.Statistics;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.service.solve.SolveStatisticsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{username}/solves/statistics")
@RequiredArgsConstructor
public class SolveStatisticsController {

    private final SolveStatisticsService solveStatisticsService;

    @GetMapping
    public Statistics getStatistics(@PathVariable String username,
                                    @RequestParam Discipline discipline) {
        return solveStatisticsService.calculateStatistics(username, discipline);
    }
}
