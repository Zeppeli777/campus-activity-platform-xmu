package com.campusactivity.controller.web;

import com.campusactivity.entity.User;
import com.campusactivity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam String username,
                                      @RequestParam String password,
                                      Model model) {
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "用户名已存在！");
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // 加密密码
        user.setRole("USER");
        userRepository.save(user);
        model.addAttribute("success", "注册成功！请登录");
        return "register";
    }
}
