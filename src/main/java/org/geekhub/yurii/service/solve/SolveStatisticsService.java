package org.geekhub.yurii.service.solve;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.model.solve.Statistics;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.solve.AoType;
import org.geekhub.yurii.model.solve.Solve;
import org.geekhub.yurii.model.solve.Status;
import org.geekhub.yurii.model.solve.Time;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolveStatisticsService {

    private final SolveService solveService;
    private final AoCalculator aoCalculator;

    public Statistics calculateStatistics(String username, Discipline discipline) {
        List<Solve> solves = solveService.getSolvesForUser(username, discipline);
        Statistics statistics = new Statistics();

        statistics.setTimes(mapToTime(solves));
        statistics.setAo5(aoCalculator.calculate(AoType.AO_5, solves));
        statistics.setAo12(aoCalculator.calculate(AoType.AO_12, solves));
        statistics.setAo50(aoCalculator.calculate(AoType.AO_50, solves));
        statistics.setAo100(aoCalculator.calculate(AoType.AO_100, solves));
        statistics.setBest(findBest(solves));
        statistics.setWorst(findWorst(solves));
        statistics.setMean(findMean(solves));
        statistics.setCount(solves.size());

        return statistics;
    }

    public Time findBestAo5(String username, Discipline discipline) {
        List<Solve> solves = solveService.getSolvesForUser(username, discipline);
        List<Time> ao5List = aoCalculator.calculate(AoType.AO_5, solves);

        return ao5List.stream()
                .min((time1, time2) -> {
                    if (time1 == null) return 1;
                    if (time2 == null) return -1;
                    return time1.compareTo(time2);
                })
                .orElse(null);
    }

    public int getSolvesCount(String username, Discipline discipline) {
        return solveService.getCount(username, discipline);
    }

    private List<Time> mapToTime(List<Solve> solves) {
        return solves.stream()
                .map((solve -> solve.getStatus() != Status.DNF ? solve.getTime() : null))
                .collect(Collectors.toList());
    }

    private Time findBest(List<Solve> solves) {
        return solves.stream()
                .filter(solve -> solve.getStatus() != Status.DNF)
                .min(Solve::compareTo)
                .map(Solve::getTime)
                .orElse(null);
    }

    private Time findWorst(List<Solve> solves) {
        return solves.stream()
                .filter(solve -> solve.getStatus() != Status.DNF)
                .max(Solve::compareTo)
                .map(Solve::getTime)
                .orElse(null);
    }

    private Time findMean(List<Solve> solves) {
        List<Solve> filteredSolves = solves.stream()
                .filter(solve -> solve.getStatus() != Status.DNF)
                .collect(Collectors.toList());

        if (filteredSolves.isEmpty()) return null;

        long sum = filteredSolves.stream()
                .map(Solve::getMicroseconds)
                .reduce(0, Integer::sum);

        return Time.valueOf((int) (sum / filteredSolves.size()));
    }
}
