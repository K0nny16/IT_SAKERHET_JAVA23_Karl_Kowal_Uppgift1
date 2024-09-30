package org.it_sakerhet_java23_karl_kowal_uppgift1.controller;

import jakarta.servlet.http.HttpSession;
import org.it_sakerhet_java23_karl_kowal_uppgift1.dto.UserDTO;
import org.it_sakerhet_java23_karl_kowal_uppgift1.entity.UserEntity;
import org.it_sakerhet_java23_karl_kowal_uppgift1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InfopageController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public String showUserInfo(HttpSession session, Model model) {
        UserDTO loggedInUser = (UserDTO) session.getAttribute("user");
        if (loggedInUser == null) {
            return "redirect:/";
        }
        UserEntity userEntity = userService.findByEmail(loggedInUser.getEmail());
        model.addAttribute("user", userEntity);
        return "info";
    }

    @PostMapping("/update-info")
    public String updateUserInfo(UserEntity updatedUser, HttpSession session, Model model) {
        UserDTO loggedInUser = (UserDTO) session.getAttribute("user");
        if (loggedInUser == null) {
            return "redirect:/";
        }

        UserEntity existingUser = userService.findByEmail(loggedInUser.getEmail());
        existingUser.setName(updatedUser.getName());
        existingUser.setAdress(updatedUser.getAdress());

        userService.updateUser(existingUser);
        session.setAttribute("user", new UserDTO((long) existingUser.getId(), existingUser.getEmail(), existingUser.getName(), existingUser.getAdress()));

        model.addAttribute("user", existingUser);
        model.addAttribute("message", "Din information har uppdaterats!");
        return "info";
    }

    @PostMapping("/delete-user")
    public String deleteUser(HttpSession session) {
        UserDTO loggedInUser = (UserDTO) session.getAttribute("user");
        if (loggedInUser == null) {
            return "redirect:/";
        }
        userService.deleteUserByEmail(loggedInUser.getEmail());
        session.invalidate();
        return "redirect:/";
    }
}