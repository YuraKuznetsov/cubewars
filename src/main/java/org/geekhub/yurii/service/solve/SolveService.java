package org.geekhub.yurii.service.solve;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.solve.SolveCreateDTO;
import org.geekhub.yurii.dto.solve.SolveUpdateDTO;
import org.geekhub.yurii.model.solve.Status;
import org.geekhub.yurii.model.page.Page;
import org.geekhub.yurii.model.page.Pageable;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.solve.Solve;
import org.geekhub.yurii.repository.solve.SolveRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SolveService {

    private final SolveRepository solveRepository;

    public Optional<Solve> find(Integer id) {
        return solveRepository.find(id);
    }

    public Solve create(SolveCreateDTO createDTO) {
        Solve solve = new Solve();

        solve.setUsername(createDTO.getUsername());
        solve.setTime(createDTO.getTime());
        solve.setDiscipline(createDTO.getDiscipline());
        solve.setScramble(createDTO.getScramble());
        solve.setStatus(Status.GOOD);

        solveRepository.save(solve);

        return solve;
    }

    public void update(SolveUpdateDTO updateDTO) {
        solveRepository.update(updateDTO);
    }

    public void delete(int id) {
        solveRepository.delete(id);
    }

    public List<Solve> getSolvesForUser(String username, Discipline discipline) {
        return solveRepository.getSolvesForUser(username, discipline);
    }

    public Page<Solve> getPage(String username, Discipline discipline, Pageable pageable) {
        List<Solve> solves = solveRepository.getSolvesForUser(username, discipline, pageable);

        int count = solveRepository.getCountForUser(username, discipline);
        boolean hasNext = count > pageable.getOffset() + pageable.getLimit();

        return new Page<>(solves, hasNext);
    }

    public int getCount(String username, Discipline discipline) {
        return solveRepository.getCountForUser(username, discipline);
    }
}
