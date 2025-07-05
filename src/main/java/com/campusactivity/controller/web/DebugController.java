package com.campusactivity.controller.web;

import com.campusactivity.entity.User;
import com.campusactivity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 调试控制器 - 用于检查用户数据和登录问题
 * 生产环境请删除此控制器
 */
@Controller
public class DebugController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 检查数据库中的所有用户
     */
    @GetMapping("/debug/users")
    @ResponseBody
    public String debugUsers() {
        List<User> users = userRepository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("=== 数据库中的用户信息 ===\n");
        sb.append("用户总数: ").append(users.size()).append("\n\n");
        
        for (User user : users) {
            sb.append("用户ID: ").append(user.getId()).append("\n");
            sb.append("用户名: ").append(user.getUsername()).append("\n");
            sb.append("角色: ").append(user.getRole()).append("\n");
            sb.append("密码(加密): ").append(user.getPassword()).append("\n");
            sb.append("邮箱: ").append(user.getEmail()).append("\n");
            sb.append("真实姓名: ").append(user.getRealName()).append("\n");
            sb.append("-------------------\n");
        }
        
        return sb.toString().replace("\n", "<br>");
    }

    /**
     * 测试密码验证
     */
    @GetMapping("/debug/password-test")
    @ResponseBody
    public String testPassword() {
        User admin = userRepository.findByUsername("admin");
        if (admin == null) {
            return "管理员用户不存在！";
        }
        
        String rawPassword = "admin123";
        String encodedPassword = admin.getPassword();
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== 密码验证测试 ===<br>");
        sb.append("用户名: ").append(admin.getUsername()).append("<br>");
        sb.append("原始密码: ").append(rawPassword).append("<br>");
        sb.append("加密密码: ").append(encodedPassword).append("<br>");
        sb.append("密码匹配: ").append(matches ? "✅ 成功" : "❌ 失败").append("<br>");
        sb.append("角色: ").append(admin.getRole()).append("<br>");
        
        return sb.toString();
    }

    /**
     * 重新创建管理员用户
     */
    @GetMapping("/debug/recreate-admin")
    @ResponseBody
    public String recreateAdmin() {
        // 删除现有的admin用户
        User existingAdmin = userRepository.findByUsername("admin");
        if (existingAdmin != null) {
            userRepository.delete(existingAdmin);
        }

        // 创建新的admin用户
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        admin.setEmail("admin@campus.edu");
        admin.setRealName("系统管理员");
        userRepository.save(admin);

        return "✅ 管理员用户重新创建成功！<br>用户名: admin<br>密码: admin123<br>角色: ADMIN";
    }

    /**
     * 重新创建普通用户
     */
    @GetMapping("/debug/recreate-user")
    @ResponseBody
    public String recreateUser() {
        // 删除现有的user用户
        User existingUser = userRepository.findByUsername("user");
        if (existingUser != null) {
            userRepository.delete(existingUser);
        }

        // 创建新的user用户
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setRole("USER");
        user.setEmail("user@campus.edu");
        user.setRealName("测试用户");
        userRepository.save(user);

        return "✅ 普通用户重新创建成功！<br>用户名: user<br>密码: user123<br>角色: USER";
    }

    /**
     * 重新创建所有测试用户
     */
    @GetMapping("/debug/recreate-all")
    @ResponseBody
    public String recreateAllUsers() {
        // 清空所有用户
        userRepository.deleteAll();

        // 创建管理员用户
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        admin.setEmail("admin@campus.edu");
        admin.setRealName("系统管理员");
        userRepository.save(admin);

        // 创建普通用户
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setRole("USER");
        user.setEmail("user@campus.edu");
        user.setRealName("测试用户");
        userRepository.save(user);

        return "✅ 所有测试用户重新创建成功！<br><br>" +
               "管理员账号: admin / admin123 (角色: ADMIN)<br>" +
               "普通用户账号: user / user123 (角色: USER)<br><br>" +
               "现在可以尝试登录了！";
    }

    /**
     * 测试密码复杂度验证
     */
    @GetMapping("/debug/test-password")
    @ResponseBody
    public String testPasswordValidation() {
        String[] testPasswords = {
            "123456",           // 太短，无字母和特殊符号
            "abcdefgh",         // 无数字和特殊符号
            "12345678",         // 无字母和特殊符号
            "abcd1234",         // 无特殊符号
            "abcd123!",         // 符合要求
            "Password1@",       // 符合要求
            "MyPass123$",       // 符合要求
            "weak",             // 太短
            "VeryLongPasswordButNoNumbersOrSpecialChars", // 无数字和特殊符号
            "Test123@"          // 符合要求
        };

        StringBuilder result = new StringBuilder();
        result.append("<h3>密码复杂度测试</h3>");
        result.append("<p><strong>要求：</strong>至少8位，包含字母、数字和特殊符号（@$!%*?&）</p>");
        result.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");
        result.append("<tr><th>测试密码</th><th>结果</th><th>说明</th></tr>");

        for (String password : testPasswords) {
            String validation = validatePasswordForTest(password);
            boolean isValid = validation == null;
            String status = isValid ? "✅ 通过" : "❌ 失败";
            String explanation = isValid ? "符合所有要求" : validation;

            result.append("<tr>");
            result.append("<td>").append(password).append("</td>");
            result.append("<td>").append(status).append("</td>");
            result.append("<td>").append(explanation).append("</td>");
            result.append("</tr>");
        }

        result.append("</table>");
        return result.toString();
    }

    /**
     * 测试用的密码验证方法（复制自RegisterController的逻辑）
     */
    private String validatePasswordForTest(String password) {
        if (password.length() < 8) {
            return "密码长度至少需要8位";
        }
        if (!password.matches(".*[a-zA-Z].*")) {
            return "密码必须包含至少一个字母";
        }
        if (!password.matches(".*\\d.*")) {
            return "密码必须包含至少一个数字";
        }
        if (!password.matches(".*[@$!%*?&].*")) {
            return "密码必须包含至少一个特殊符号（@$!%*?&）";
        }
        return null; // 密码符合要求
    }
}
