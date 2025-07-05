package com.campusactivity.config;

import com.campusactivity.entity.User;
import com.campusactivity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 安全配置类
 * 负责配置用户认证、授权、密码加密等安全相关功能
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    /**
     * 密码编码器Bean
     * 临时使用NoOp编码器支持明文密码（仅用于开发测试）
     * 生产环境应使用BCrypt算法对密码进行加密存储
     * @return PasswordEncoder实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 临时使用明文密码编码器，方便开发测试
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
        // 生产环境应使用：return new BCryptPasswordEncoder();
    }

    /**
     * 用户详情服务Bean
     * 实现Spring Security的UserDetailsService接口
     * 用于从数据库加载用户信息进行认证
     * @return UserDetailsService实例
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // 从数据库查找用户
                User user = userRepository.findByUsername(username);
                if (user == null) {
                    throw new UsernameNotFoundException("用户不存在: " + username);
                }
                // 构建Spring Security的UserDetails对象
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword()) // 密码已经是加密后的
                        .roles(user.getRole()) // 用户角色（USER或ADMIN）
                        .build();
            }
        };
    }

    /**
     * 安全过滤器链配置
     * 配置URL访问权限、登录登出、CSRF等安全策略
     * @param http HttpSecurity配置对象
     * @return SecurityFilterChain安全过滤器链
     * @throws Exception 配置异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 配置URL访问权限
            .authorizeRequests()
                // 允许所有人访问的页面（无需登录）
                .antMatchers("/", "/login", "/register", "/welcome", "/hall", "/debug/**", "/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/user/activities/page", "/user/activities/page/**").permitAll() // 活动浏览页面允许匿名访问
                .antMatchers("/h2-console/**").permitAll() // 允许访问H2数据库控制台
                // 需要特定角色才能访问的页面
                .antMatchers("/admin/**").hasAnyRole("ADMIN") // 管理员页面
                .antMatchers("/user/my-registrations", "/user/registrations/**").hasAnyRole("USER", "ADMIN") // 用户报名相关页面
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")   // 其他用户页面
                .anyRequest().authenticated() // 其他所有请求都需要认证
                .and()
            // 配置表单登录
            .formLogin()
                .loginPage("/login")           // 自定义登录页面
                .defaultSuccessUrl("/", true)  // 登录成功后跳转到首页
                .permitAll()                   // 登录页面允许所有人访问
                .and()
            // 添加访问拒绝处理
//            .exceptionHandling()
//                .accessDeniedPage("/error/403") // 指向自定义的403错误页面
//                .and()
            // 配置登出
            .logout()
                .logoutSuccessUrl("/?logout=true") // 登出后跳转到首页，显示退出成功信息
                .permitAll()                       // 登出功能允许所有人使用
                .and()
            // 禁用CSRF保护（简化开发，生产环境建议启用）
            .csrf().disable()
            // 禁用X-Frame-Options（支持H2控制台的iframe显示）
            .headers().frameOptions().disable();

        return http.build();
    }
}