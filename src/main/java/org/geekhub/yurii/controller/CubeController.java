package org.geekhub.yurii.controller;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.model.cube.Cube;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.service.cube.CubeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cube")
@RequiredArgsConstructor
public class CubeController {

    private final CubeService cubeService;

    @GetMapping("/scramble")
    public String generateScramble(@RequestParam Discipline discipline) {
        return cubeService.generateScramble(discipline);
    }

    @GetMapping("/scrambled-by-scramble")
    public Cube generateScrambledCube(@RequestParam Discipline discipline,
                                      @RequestParam String scramble) {
        return cubeService.generateScrambledCube(discipline, scramble);
    }

    @GetMapping("/scrambled-cube")
    public Map<String, Object> scrambleCube(@RequestParam Discipline discipline) {
        String scramble = cubeService.generateScramble(discipline);
        Cube cube = cubeService.generateScrambledCube(discipline, scramble);

        return Map.of(
                "scramble", scramble,
                "cube", cube
        );
    }
}
