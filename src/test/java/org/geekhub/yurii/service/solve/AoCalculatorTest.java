package org.geekhub.yurii.service.solve;

import org.geekhub.yurii.model.solve.AoType;
import org.geekhub.yurii.model.solve.Solve;
import org.geekhub.yurii.model.solve.Status;
import org.geekhub.yurii.model.solve.Time;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AoCalculatorTest {

    AoCalculator aoCalculator = new AoCalculator();

    @Test
    void testCalculateLastAo_whenNotEnoughSolves() {
        List<Solve> twoSolves = List.of(new Solve(), new Solve());

        assertNull(aoCalculator.calculateCurrent(AoType.AO_5, twoSolves));
        assertNull(aoCalculator.calculateCurrent(AoType.AO_12, twoSolves));
        assertNull(aoCalculator.calculateCurrent(AoType.AO_50, twoSolves));
        assertNull(aoCalculator.calculateCurrent(AoType.AO_100, twoSolves));
    }

    @Test
    void testCalculateAll_whenNotEnoughSolves() {
        List<Solve> twoSolves = List.of(new Solve(), new Solve());

        assertEquals(List.of(), aoCalculator.calculate(AoType.AO_5, twoSolves));
        assertEquals(List.of(), aoCalculator.calculate(AoType.AO_12, twoSolves));
        assertEquals(List.of(), aoCalculator.calculate(AoType.AO_50, twoSolves));
        assertEquals(List.of(), aoCalculator.calculate(AoType.AO_100, twoSolves));
    }

    @Test
    void testCalculateLast_whenAllStatusesAreGood() {
        Solve solve1 = new Solve();
        Solve solve2 = new Solve();

        Solve solve3 = new Solve();
        solve3.setId(3);
        solve3.setTime(Time.valueOf(1000));
        solve3.setStatus(Status.GOOD);

        Solve solve4 = new Solve();
        solve4.setId(4);
        solve4.setTime(Time.valueOf(600));
        solve4.setStatus(Status.GOOD);

        Solve solve5 = new Solve();
        solve5.setId(5);
        solve5.setTime(Time.valueOf(3650)); // max
        solve5.setStatus(Status.GOOD);

        Solve solve6 = new Solve();
        solve6.setId(6);
        solve6.setTime(Time.valueOf(500));  // min
        solve6.setStatus(Status.GOOD);

        Solve solve7 = new Solve();
        solve7.setId(7);
        solve7.setTime(Time.valueOf(1100));
        solve7.setStatus(Status.GOOD);

        List<Solve> solves = List.of(solve1, solve2, solve3, solve4, solve5, solve6, solve7);

        assertEquals(Time.valueOf(900), aoCalculator.calculateCurrent(AoType.AO_5, solves));
    }

    @Test
    void testCalculateLast_whenOneStatusIsDNF() {
        Solve solve1 = new Solve();
        solve1.setId(1);
        solve1.setTime(Time.valueOf(400));  // max because DNF
        solve1.setStatus(Status.DNF);

        Solve solve2 = new Solve();
        solve2.setId(2);
        solve2.setTime(Time.valueOf(800));
        solve2.setStatus(Status.GOOD);

        Solve solve3 = new Solve();
        solve3.setId(3);
        solve3.setTime(Time.valueOf(500));  // min
        solve3.setStatus(Status.GOOD);

        Solve solve4 = new Solve();
        solve4.setId(4);
        solve4.setTime(Time.valueOf(650));
        solve4.setStatus(Status.GOOD);

        Solve solve5 = new Solve();
        solve5.setId(5);
        solve5.setTime(Time.valueOf(1100));
        solve5.setStatus(Status.GOOD);

        List<Solve> solves = List.of(solve1, solve2, solve3, solve4, solve5);

        assertEquals(Time.valueOf(850), aoCalculator.calculateCurrent(AoType.AO_5, solves));
    }

    @Test
    void testCalculateLast_whenTwoStatusesAreDNF() {
        Solve solve1 = new Solve();
        solve1.setId(1);
        solve1.setTime(Time.valueOf(400));
        solve1.setStatus(Status.DNF);

        Solve solve2 = new Solve();
        solve2.setId(2);
        solve2.setTime(Time.valueOf(800));
        solve2.setStatus(Status.GOOD);

        Solve solve3 = new Solve();
        solve3.setId(3);
        solve3.setTime(Time.valueOf(500));
        solve3.setStatus(Status.DNF);

        Solve solve4 = new Solve();
        solve4.setId(4);
        solve4.setTime(Time.valueOf(650));
        solve4.setStatus(Status.GOOD);

        Solve solve5 = new Solve();
        solve5.setId(5);
        solve5.setTime(Time.valueOf(1100));
        solve5.setStatus(Status.GOOD);

        List<Solve> solves = List.of(solve1, solve2, solve3, solve4, solve5);

        assertNull(aoCalculator.calculateCurrent(AoType.AO_5, solves));
    }

    @Test
    void testCalculateAll() {
        Solve solve1 = new Solve();
        solve1.setId(1);
        solve1.setTime(Time.valueOf(1000));
        solve1.setStatus(Status.GOOD);

        Solve solve2 = new Solve();
        solve2.setId(2);
        solve2.setTime(Time.valueOf(1200));
        solve2.setStatus(Status.GOOD);

        Solve solve3 = new Solve();
        solve3.setId(3);
        solve3.setTime(Time.valueOf(1400));
        solve3.setStatus(Status.GOOD);

        Solve solve4 = new Solve();
        solve4.setId(4);
        solve4.setTime(Time.valueOf(1700));
        solve4.setStatus(Status.GOOD);

        Solve solve5 = new Solve();
        solve5.setId(5);
        solve5.setTime(Time.valueOf(1900));
        solve5.setStatus(Status.GOOD);

        Solve solve6 = new Solve();
        solve6.setId(6);
        solve6.setTime(Time.valueOf(1100));
        solve6.setStatus(Status.GOOD);

        Solve solve7 = new Solve();
        solve7.setId(7);
        solve7.setTime(Time.valueOf(900));
        solve7.setStatus(Status.GOOD);

        List<Solve> solves = List.of(solve1, solve2, solve3, solve4, solve5, solve6, solve7);

        List<Time> expectedAos5 = List.of(
                Time.valueOf(1433),
                Time.valueOf(1433),
                Time.valueOf(1400)
        );

        List<Time> actualAos5 = aoCalculator.calculate(AoType.AO_5, solves);

        assertEquals(expectedAos5, actualAos5);
    }
}