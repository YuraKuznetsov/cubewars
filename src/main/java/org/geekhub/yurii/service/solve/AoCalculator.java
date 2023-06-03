package org.geekhub.yurii.service.solve;

import org.geekhub.yurii.model.solve.AoType;
import org.geekhub.yurii.model.solve.Solve;
import org.geekhub.yurii.model.solve.Status;
import org.geekhub.yurii.model.solve.Time;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AoCalculator {

    public Time calculateCurrent(AoType aoType, List<Solve> solves) {
        if (solves.size() < aoType.getSolvesCount()) return null;
        List<Solve> lastSolves = solves.subList(solves.size() - aoType.getSolvesCount(), solves.size());

        return calculateAo(aoType, lastSolves);
    }

    public List<Time> calculate(AoType aoType, List<Solve> solves) {
        if (solves.size() < aoType.getSolvesCount()) return List.of();

        List<Time> result = new ArrayList<>();

        for (int i = aoType.getSolvesCount(); i <= solves.size(); i++) {
            List<Solve> currentAoSolves = solves.subList(i - aoType.getSolvesCount(), i);
            Time currentAo = calculateAo(aoType, currentAoSolves);
            result.add(currentAo);
        }

        return result;
    }

    private Time calculateAo(AoType aoType, List<Solve> solves) {
        List<Solve> sortedSolves = solves.stream().sorted().collect(Collectors.toList());

        int firstIndex = aoType.getIgnoredBetters();
        int lastIndex = solves.size() - aoType.getIgnoredWorsts() - 1;

        int sum = 0;
        for (int i = firstIndex; i <= lastIndex; i++) {
            Solve solve = sortedSolves.get(i);
            if (solve.getStatus() == Status.DNF) return null;
            sum += solve.getMicroseconds();
        }

        int average = sum / aoType.getMiddleCount();

        return Time.valueOf(average);
    }
}
