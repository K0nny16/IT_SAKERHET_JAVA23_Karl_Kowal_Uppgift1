package org.it_sakerhet_java23_karl_kowal_uppgift1.controller;

import jakarta.servlet.http.HttpSession;
import org.it_sakerhet_java23_karl_kowal_uppgift1.entity.UserEntity;
import org.it_sakerhet_java23_karl_kowal_uppgift1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userServices;

    @GetMapping("/")
    public String homePage() {
        return "login";
    }
    //Mapping för posten som sker efter att formet har fyllts i.
    @PostMapping("/register")
    public String register(Model model, UserEntity user) {
        try {
            userServices.registerUser(user);
            model.addAttribute("message", "User added successfully");
            return "login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
            return "register";
        }
    }
    @GetMapping("/register")
    //Skickar med en tom User till sidan med registeringens formet så att det stämmer övernes i HTML.
    public String registerForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }
    @PostMapping("/login")
    //Loggar in användaren och sätter han i session.
    public String loginUser(String email, String password, Model model, HttpSession session) {
        boolean auth = userServices.loginUser(email, password, session);
        if (auth) {
            return "redirect:/info";
        } else {
            model.addAttribute("message", "Felaktigt email eller lösenord!");
            return "login";
        }
    }
    //Tarbort sessionen och skickar användaren till roten.
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}