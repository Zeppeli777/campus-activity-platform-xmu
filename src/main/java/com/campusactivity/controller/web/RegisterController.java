package com.campusactivity.controller.web;

import com.campusactivity.entity.User;
import com.campusactivity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 * 用户注册控制器
 * 处理用户注册相关的请求，包括密码复杂度验证
 */
@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 密码复杂度验证的正则表达式
    // 至少8位，包含字母、数字和特殊符号
    private static final String PASSWORD_PATTERN =
        "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    /**
     * 显示注册表单
     */
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    /**
     * 处理用户注册
     * 包含用户名重复检查和密码复杂度验证
     */
    @PostMapping("/register")
    public String processRegistration(@RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam(required = false) String confirmPassword,
                                      Model model) {

        // 保留用户输入的用户名，避免注册失败后需要重新输入
        model.addAttribute("username", username);

        // 1. 检查用户名是否为空
        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("error", "用户名不能为空！");
            return "register";
        }

        // 2. 检查用户名长度
        if (username.trim().length() < 3) {
            model.addAttribute("error", "用户名至少需要3个字符！");
            return "register";
        }

        // 3. 检查用户名是否已存在
        if (userRepository.findByUsername(username.trim()) != null) {
            model.addAttribute("error", "用户名已存在！请选择其他用户名。");
            return "register";
        }

        // 4. 检查密码是否为空
        if (password == null || password.isEmpty()) {
            model.addAttribute("error", "密码不能为空！");
            return "register";
        }

        // 5. 验证密码复杂度
        String passwordValidationResult = validatePassword(password);
        if (passwordValidationResult != null) {
            model.addAttribute("error", passwordValidationResult);
            return "register";
        }

        // 6. 检查确认密码（如果提供了的话）
        if (confirmPassword != null && !password.equals(confirmPassword)) {
            model.addAttribute("error", "两次输入的密码不一致！");
            return "register";
        }

        // 7. 创建用户
        try {
            User user = new User();
            user.setUsername(username.trim());
            user.setPassword(passwordEncoder.encode(password)); // 加密密码
            user.setRole("USER");
            userRepository.save(user);

            model.addAttribute("success", "注册成功！请使用您的账号密码登录。");
            // 清除用户名，避免成功后还显示在输入框中
            model.addAttribute("username", "");
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", "注册失败，请稍后重试！");
            return "register";
        }
    }

    /**
     * 验证密码复杂度
     * @param password 待验证的密码
     * @return 如果密码符合要求返回null，否则返回错误信息
     */
    private String validatePassword(String password) {
        // 检查密码长度
        if (password.length() < 8) {
            return "密码长度至少需要8位！";
        }

        // 检查是否包含字母
        if (!password.matches(".*[a-zA-Z].*")) {
            return "密码必须包含至少一个字母！";
        }

        // 检查是否包含数字
        if (!password.matches(".*\\d.*")) {
            return "密码必须包含至少一个数字！";
        }

        // 检查是否包含特殊符号
        if (!password.matches(".*[@$!%*?&].*")) {
            return "密码必须包含至少一个特殊符号（@$!%*?&）！";
        }

        // 使用正则表达式进行综合验证
        if (!pattern.matcher(password).matches()) {
            return "密码格式不正确！请确保密码至少8位，包含字母、数字和特殊符号（@$!%*?&）。";
        }

        return null; // 密码符合要求
    }
}
