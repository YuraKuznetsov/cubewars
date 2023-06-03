package org.geekhub.yurii.controller.solve;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.solve.SolveCreateDTO;
import org.geekhub.yurii.dto.solve.SolveUpdateDTO;
import org.geekhub.yurii.exception.AccessDeniedException;
import org.geekhub.yurii.exception.ItemNotFoundException;
import org.geekhub.yurii.model.page.Page;
import org.geekhub.yurii.model.page.Pageable;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.solve.Solve;
import org.geekhub.yurii.service.user.AuthService;
import org.geekhub.yurii.service.solve.SolveService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users/{username}/solves")
@RequiredArgsConstructor
public class SolveController {

    private final SolveService solveService;
    private final AuthService authService;

    @GetMapping(produces = {"text/html", "application/xhtml+xml"})
    public String index(@PathVariable String username) {
        securityCheck(username);

        return "solves";
    }

    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public Page<Solve> getPage(@PathVariable String username,
                               @RequestParam Discipline discipline,
                               @Valid Pageable pageable) {
        securityCheck(username);

        return solveService.getPage(username, discipline, pageable);
    }

    @PostMapping
    @ResponseBody
    public Solve create(@Valid SolveCreateDTO createDTO) {
        securityCheck(createDTO.getUsername());

        return solveService.create(createDTO);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public void update(@PathVariable String username, @Valid SolveUpdateDTO solveUpdateDTO) {
        securityCheck(username);
        solveService.update(solveUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable String username, @PathVariable Integer id) {
        securityCheck(username);
        solveService.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Solve find(@PathVariable String username, @PathVariable Integer id) {
        securityCheck(username);

        return solveService.find(id)
                .orElseThrow(() -> new ItemNotFoundException("Solve not found"));
    }

    private void securityCheck(String username) {
        if (!authService.isOwnPage(username)) {
            throw new AccessDeniedException("You don't have access to this page");
        }
    }
}
