package com.campusactivity.repository;

import com.campusactivity.entity.Activity;
import com.campusactivity.entity.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    // 根据活动类型查找活动
    List<Activity> findByActivityType(ActivityType activityType);

    // 根据活动类型ID查找活动
    List<Activity> findByActivityTypeId(Long activityTypeId);

    // 查找指定时间范围内的活动
    List<Activity> findByStartTimeBetween(Date startDate, Date endDate);

    // 查找还有名额的活动
    @Query("SELECT a FROM Activity a WHERE SIZE(a.registrations) < a.capacity")
    List<Activity> findActivitiesWithAvailableSpots();

    // 根据标题模糊查询
    List<Activity> findByTitleContainingIgnoreCase(String title);

    // 查找即将开始的活动（未来7天内）
    @Query("SELECT a FROM Activity a WHERE a.startTime BETWEEN :now AND :futureDate ORDER BY a.startTime")
    List<Activity> findUpcomingActivities(@Param("now") Date now, @Param("futureDate") Date futureDate);
}