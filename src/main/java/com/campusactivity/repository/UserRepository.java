package com.campusactivity.repository;

import com.campusactivity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // ✅ 添加这一行即可
    User findByUsername(String username);
}