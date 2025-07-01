package com.campusactivity.config;

import com.campusactivity.entity.User;
import com.campusactivity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 数据初始化器
 * 实现CommandLineRunner接口，在应用启动时自动执行
 * 用于创建初始的测试用户数据
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 应用启动时执行的方法
     * 检查数据库中是否有用户数据，如果没有则创建默认的测试用户
     * @param args 命令行参数
     * @throws Exception 执行异常
     */
    @Override
    public void run(String... args) throws Exception {
        // 检查是否已有用户数据，避免重复创建
        if (userRepository.count() == 0) {
            // 创建管理员用户
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // 密码加密存储
            admin.setRole("ADMIN"); // 设置管理员角色
            userRepository.save(admin);

            // 创建普通用户
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123")); // 密码加密存储
            user.setRole("USER"); // 设置普通用户角色
            userRepository.save(user);

            // 输出初始化信息，方便开发测试
            System.out.println("=== 初始化用户数据完成 ===");
            System.out.println("管理员账号: admin / admin123");
            System.out.println("普通用户账号: user / user123");
            System.out.println("========================");
        }
    }
}
