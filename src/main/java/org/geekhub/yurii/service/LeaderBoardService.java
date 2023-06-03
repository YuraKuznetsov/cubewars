package org.geekhub.yurii.service;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.user.UserLeaderBoardDTO;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.page.Page;
import org.geekhub.yurii.model.page.Pageable;
import org.geekhub.yurii.model.solve.Time;
import org.geekhub.yurii.service.solve.SolveStatisticsService;
import org.geekhub.yurii.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LeaderBoardService {

    private final UserService userService;
    private final SolveStatisticsService solveStatisticsService;

    public Page<UserLeaderBoardDTO> getLeaderboardPage(Discipline discipline, Pageable pageable) {
        List<UserLeaderBoardDTO> pageElements = getLeaderBoardDTOs(discipline)
                .sorted((o1, o2) -> {
                    if (o1.getAo5() == null) return 1;
                    if (o2.getAo5() == null) return -1;

                    return o1.getAo5().compareTo(o2.getAo5());
                })
                .skip(pageable.getOffset())
                .limit(pageable.getLimit())
                .collect(Collectors.toList());

        boolean hasNextPage = userService.getCount() > pageable.getOffset() + pageable.getLimit();

        return new Page<>(pageElements, hasNextPage);
    }

    private Stream<UserLeaderBoardDTO> getLeaderBoardDTOs(Discipline discipline) {
        return userService.getAll().stream()
                .map(user -> {
                    String username = user.getUsername();
                    Time bestAo5 = solveStatisticsService.findBestAo5(username, discipline);
                    int count = solveStatisticsService.getSolvesCount(username, discipline);

                    return new UserLeaderBoardDTO(username, bestAo5, count);
                });
    }
}
