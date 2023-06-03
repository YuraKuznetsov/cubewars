package org.geekhub.yurii.service.solve;

import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.solve.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SolveStatisticsServiceTest {

    @Mock
    SolveService solveService;
    @Mock
    AoCalculator aoCalculator;
    @InjectMocks
    SolveStatisticsService statisticsService;

    @Test
    void testCalculateStatistics_whenZeroSolves() {
        when(solveService.getSolvesForUser(any(String.class), any(Discipline.class))).thenReturn(List.of());
        when(aoCalculator.calculate(any(AoType.class), anyList())).thenReturn(List.of());

        Statistics expected = new Statistics();
        expected.setTimes(List.of());
        expected.setAo5(List.of());
        expected.setAo12(List.of());
        expected.setAo50(List.of());
        expected.setAo100(List.of());
        expected.setBest(null);
        expected.setWorst(null);
        expected.setMean(null);
        expected.setCount(0);

        Statistics actual = statisticsService.calculateStatistics("Cubewars", Discipline.CUBE_3X3);

        assertEquals(expected, actual);
    }

    @Test
    void testCalculateStatistics() {
        List<Solve> testSolves = getTestSolves();

        when(solveService.getSolvesForUser(any(String.class), any(Discipline.class)))
                .thenReturn(testSolves);
        when(aoCalculator.calculate(eq(AoType.AO_5), anyList()))
                .thenReturn(List.of(Time.valueOf("0:15.56"), Time.valueOf("0:15.72"), Time.valueOf("0:14.20")));
        when(aoCalculator.calculate(eq(AoType.AO_12), anyList()))
                .thenReturn(List.of());
        when(aoCalculator.calculate(eq(AoType.AO_50), anyList()))
                .thenReturn(List.of());
        when(aoCalculator.calculate(eq(AoType.AO_100), anyList()))
                .thenReturn(List.of());

        Statistics expected = new Statistics();
        List<Time> times = Arrays.asList(Time.valueOf("0:15.76"), Time.valueOf("0:16.89"),
                                         Time.valueOf("0:14.04"), null, Time.valueOf("0:12.33"),
                                         Time.valueOf("0:16.24"), Time.valueOf("0:10.15"));
        expected.setTimes(times);
        expected.setAo5(List.of(Time.valueOf("0:15.56"), Time.valueOf("0:15.72"), Time.valueOf("0:14.20")));
        expected.setAo12(List.of());
        expected.setAo50(List.of());
        expected.setAo100(List.of());
        expected.setBest(Time.valueOf("0:10.15"));
        expected.setWorst(Time.valueOf("0:16.89"));
        expected.setMean(Time.valueOf("0:14.23"));
        expected.setCount(7);

        Statistics actual = statisticsService.calculateStatistics("Username", Discipline.CUBE_3X3);

        assertEquals(expected, actual);
    }

    private List<Solve> getTestSolves() {
        Solve solve1 = new Solve();
        solve1.setTime(Time.valueOf("0:15.76"));
        solve1.setStatus(Status.GOOD);

        Solve solve2 = new Solve();
        solve2.setTime(Time.valueOf("0:16.89"));
        solve2.setStatus(Status.PLUS_TWO);

        Solve solve3 = new Solve();
        solve3.setTime(Time.valueOf("0:14.04"));
        solve3.setStatus(Status.GOOD);

        Solve solve4 = new Solve();
        solve4.setTime(Time.valueOf("0:5.00"));
        solve4.setStatus(Status.DNF);

        Solve solve5 = new Solve();
        solve5.setTime(Time.valueOf("0:12.33"));
        solve5.setStatus(Status.GOOD);

        Solve solve6 = new Solve();
        solve6.setTime(Time.valueOf("0:16.24"));
        solve6.setStatus(Status.GOOD);

        Solve solve7 = new Solve();
        solve7.setTime(Time.valueOf("0:10.15"));
        solve7.setStatus(Status.GOOD);

        return List.of(solve1, solve2, solve3, solve4, solve5, solve6, solve7);
    }

    @Test
    void testFindBestAo5() {
        List<Solve> testSolves = getTestSolves();

        when(solveService.getSolvesForUser(any(String.class), any(Discipline.class)))
                .thenReturn(testSolves);
        when(aoCalculator.calculate(eq(AoType.AO_5), anyList()))
                .thenReturn(List.of(Time.valueOf("0:15.56"), Time.valueOf("0:15.72"), Time.valueOf("0:14.20")));

        Time expected = Time.valueOf("0:14.20");

        assertEquals(expected, statisticsService.findBestAo5("Cubewars", Discipline.CUBE_3X3));
    }
}