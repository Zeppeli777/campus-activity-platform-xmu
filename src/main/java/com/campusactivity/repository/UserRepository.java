package com.campusactivity.repository;

import com.campusactivity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 用户数据访问接口
 * 继承JpaRepository提供基本的CRUD操作
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户对象，如果不存在返回null
     */
    User findByUsername(String username);

    /**
     * 根据邮箱查找用户
     * @param email 邮箱地址
     * @return Optional包装的用户对象
     */
    Optional<User> findByEmail(String email);

    /**
     * 根据角色查找用户列表
     * @param role 用户角色
     * @return 用户列表
     */
    List<User> findByRole(String role);

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     * @param email 邮箱地址
     * @return 是否存在
     */
    boolean existsByEmail(String email);
}