package com.campusactivity.repository;

import com.campusactivity.entity.Activity;
import com.campusactivity.entity.Registration;
import com.campusactivity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // 根据用户查找报名记录
    List<Registration> findByUser(User user);

    // 根据活动查找报名记录
    List<Registration> findByActivity(Activity activity);

    // 根据用户ID查找报名记录
    List<Registration> findByUserId(Long userId);

    // 根据活动ID查找报名记录
    List<Registration> findByActivityId(Long activityId);

    // 检查用户是否已报名某活动
    Optional<Registration> findByUserAndActivity(User user, Activity activity);

    // 统计某活动的报名人数
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.activity.id = :activityId")
    Long countByActivityId(@Param("activityId") Long activityId);

    // 统计某用户的报名总数
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);

    // 统计某用户对某活动的报名次数（检查是否已报名）
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.activity.id = :activityId AND r.user.id = :userId")
    Long countByActivityIdAndUserId(@Param("activityId") Long activityId, @Param("userId") Long userId);
}