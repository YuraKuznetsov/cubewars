package org.geekhub.yurii.controller.user;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.exception.ItemNotFoundException;
import org.geekhub.yurii.model.user.Role;
import org.geekhub.yurii.model.user.User;
import org.geekhub.yurii.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public String getProfilePage(@PathVariable String username, Model model) {
        if (userService.findByUsername(username).isEmpty())
            throw new ItemNotFoundException("User not found");

        model.addAttribute("profileOwner", username);

        return "profile";
    }

    @GetMapping("/{username}/role")
    @ResponseBody
    public Role getRole(@PathVariable String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));

        return user.getRole();
    }
}
