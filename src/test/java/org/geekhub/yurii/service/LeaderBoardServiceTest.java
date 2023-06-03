package org.geekhub.yurii.service;

import org.geekhub.yurii.dto.user.UserLeaderBoardDTO;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.page.Page;
import org.geekhub.yurii.model.page.Pageable;
import org.geekhub.yurii.model.solve.Time;
import org.geekhub.yurii.model.user.User;
import org.geekhub.yurii.service.solve.SolveStatisticsService;
import org.geekhub.yurii.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaderBoardServiceTest {

    @Mock
    UserService userService;
    @Mock
    SolveStatisticsService statisticsService;
    @InjectMocks
    LeaderBoardService leaderBoardService;

    @Test
    void testGetLeaderBoardPage() {
        List<User> users = generateUsers(4);

        when(userService.getAll()).thenReturn(users);
        when(userService.getCount()).thenReturn(4);

        String[] aoTimes = new String[]  {"0:10.33", "0:15.56", "0:10.15", "0:13.12"};
        int[] counts = new int[] {19, 26, 7, 5};

        mockStatisticsService(users, aoTimes, counts);

        List<UserLeaderBoardDTO> expectedElements = List.of(
                new UserLeaderBoardDTO("User 2", Time.valueOf("0:10.15"), 7),
                new UserLeaderBoardDTO("User 0", Time.valueOf("0:10.33"), 19),
                new UserLeaderBoardDTO("User 3", Time.valueOf("0:13.12"), 5),
                new UserLeaderBoardDTO("User 1", Time.valueOf("0:15.56"), 26)
        );

        Page<UserLeaderBoardDTO> expected = new Page<>(expectedElements, false);

        Pageable firstPage = new Pageable();
        firstPage.setPageNo(1);
        firstPage.setPageSize(10);

        Page<UserLeaderBoardDTO> actual = leaderBoardService.getLeaderboardPage(Discipline.CUBE_3X3, firstPage);

        assertEquals(expected, actual);
    }

    private List<User> generateUsers(int length) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            User user = new User();
            user.setUsername("User " + i);
            users.add(user);
        }

        return users;
    }

    private void mockStatisticsService(List<User> users, String[] aoTimes, int[] counts) {
        for (int i = 0; i < users.size(); i++) {
            String username = users.get(i).getUsername();
            String time = aoTimes[i];
            int count = counts[i];

            when(statisticsService.findBestAo5(eq(username), eq(Discipline.CUBE_3X3)))
                    .thenReturn(Time.valueOf(time));
            when(statisticsService.getSolvesCount(eq(username), eq(Discipline.CUBE_3X3)))
                    .thenReturn(count);
        }
    }
}