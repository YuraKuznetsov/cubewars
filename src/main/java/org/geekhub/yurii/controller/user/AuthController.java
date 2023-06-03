package org.geekhub.yurii.controller.user;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.user.UserAuthDTO;
import org.geekhub.yurii.exception.UsernameIsNotAvailableException;
import org.geekhub.yurii.service.user.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/log-in")
    public String logInPage() {
        return "auth/log-in";
    }

    @GetMapping("/sign-up")
    public String signUpPage(@RequestParam(required = false, defaultValue = "false") boolean error, Model model) {
        model.addAttribute("error", error);

        return "auth/sigh-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid UserAuthDTO authDTO) {
        try {
            authService.signUp(authDTO);
        } catch (UsernameIsNotAvailableException e) {
            return "redirect:/users/sign-up?error=true";
        }

        return "redirect:/timer";
    }
}
