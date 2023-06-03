package org.geekhub.yurii.service.cube;

import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.cube.Cube;
import org.geekhub.yurii.model.cube.move.CubeMove;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CubeService {

    private final ScrambleGenerator scrambleGenerator = new ScrambleGenerator();
    private final CubeScrambler cubeScrambler = new CubeScrambler();
    private final ScrambleConverter scrambleConverter = new ScrambleConverter();

    public String generateScramble(Discipline discipline) {
        final List<CubeMove> scramble = scrambleGenerator.generateScramble(discipline);

        return scrambleConverter.toString(scramble);
    }

    public Cube generateScrambledCube(Discipline discipline, String scrambleAsString) {
        final List<CubeMove> scramble = scrambleConverter.toList(scrambleAsString);

        return cubeScrambler.scrambleCube(discipline, scramble);
    }
}
