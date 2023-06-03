package org.geekhub.yurii.model.solve;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolveTest {

    @Test
    void testGetMicroseconds_whenStatusIsGood() {
        Solve solve = new Solve();
        solve.setTime(Time.valueOf(3050));
        solve.setStatus(Status.GOOD);

        assertEquals(3050, solve.getMicroseconds());
    }

    @Test
    void testGetMicroseconds_whenStatusIsPlusTwo() {
        Solve solve = new Solve();
        solve.setTime(Time.valueOf(3050));
        solve.setStatus(Status.PLUS_TWO);

        assertEquals(3050, solve.getMicroseconds());
    }

    @Test
    void testCompareTo_whenBothStatusesAreDNF() {
        Solve quicker = new Solve();
        quicker.setStatus(Status.DNF);
        quicker.setTime(Time.valueOf(1000));

        Solve slower = new Solve();
        slower.setStatus(Status.DNF);
        slower.setTime(Time.valueOf(1100));

        assertEquals(-1, quicker.compareTo(slower));
        assertEquals(1, slower.compareTo(quicker));
    }

    @Test
    void testCompareTo_whenOneStatusIsDNF() {
        Solve solveDNF = new Solve();
        solveDNF.setStatus(Status.DNF);

        Solve solveGood = new Solve();
        solveGood.setStatus(Status.GOOD);

        assertEquals(1, solveDNF.compareTo(solveGood));
        assertEquals(-1, solveGood.compareTo(solveDNF));
    }

    @Test
    void testCompareTo_whenBothStatusesAreGood() {
        Solve quicker = new Solve();
        quicker.setStatus(Status.GOOD);
        quicker.setTime(Time.valueOf(1000));

        Solve slower = new Solve();
        slower.setStatus(Status.GOOD);
        slower.setTime(Time.valueOf(1100));

        assertEquals(-1, quicker.compareTo(slower));
        assertEquals(1, slower.compareTo(quicker));
    }

    @Test
    void testCompareTo_whenOneStatusIsPlusTwo() {
        Solve punishedSolve = new Solve();
        punishedSolve.setStatus(Status.PLUS_TWO);
        punishedSolve.setTime(Time.valueOf(1000));

        Solve goodSolve = new Solve();
        goodSolve.setStatus(Status.GOOD);
        goodSolve.setTime(Time.valueOf(1100));

        assertEquals(-1, punishedSolve.compareTo(goodSolve));
        assertEquals(1, goodSolve.compareTo(punishedSolve));
    }
}