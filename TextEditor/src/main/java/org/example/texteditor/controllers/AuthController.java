package org.example.texteditor.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.texteditor.repo.UserRepository;
import org.example.texteditor.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // --- головна сторінка (логін) ---
    @GetMapping("/")
    public String loginPage(Model model) {
        model.addAttribute("user", new User(null, "", "", ""));
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {
        User user = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user != null) {
            session.setAttribute("currentUser", user);
            return "redirect:/files";
        } else {
            model.addAttribute("errorMessage", "Невірний username або пароль");
            return "login";
        }
    }

    // --- реєстрація ---
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User(null, "", "", ""));
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        userRepository.save(user);
        model.addAttribute("message", "Користувача зареєстровано!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
