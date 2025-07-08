package com.campusactivity.service.impl;

import com.campusactivity.entity.Activity;
import com.campusactivity.entity.Registration;
import com.campusactivity.entity.User;
import com.campusactivity.repository.ActivityRepository;
import com.campusactivity.repository.RegistrationRepository;
import com.campusactivity.repository.UserRepository;
import com.campusactivity.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    @Override
    public Registration getRegistrationById(Long id) {
        return registrationRepository.findById(id).orElse(null);
    }

    @Override
    public Registration createRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    public Registration updateRegistration(Long id, Registration registration) {
        if (registrationRepository.existsById(id)) {
            registration.setId(id);
            return registrationRepository.save(registration);
        }
        return null;
    }

    @Override
    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }

    @Override
    public Registration registerUserForActivity(Long userId, Long activityId) {
        // 检查用户和活动是否存在
        User user = userRepository.findById(userId).orElse(null);
        Activity activity = activityRepository.findById(activityId).orElse(null);

        if (user == null || activity == null) {
            throw new RuntimeException("用户或活动不存在");
        }

        // 检查是否已经报名
        if (isUserRegistered(userId, activityId)) {
            throw new RuntimeException("您已经报名过此活动");
        }

        // 检查活动是否已满额
        Long currentCount = registrationRepository.countByActivityId(activityId);
        if (activity.getCapacity() != null && currentCount >= activity.getCapacity()) {
            throw new RuntimeException("活动已满额");
        }

        // 检查活动是否已结束
        Date now = new Date();
        if (activity.getEndTime() != null && activity.getEndTime().before(now)) {
            throw new RuntimeException("活动已结束，无法报名");
        }

        // 创建报名记录
        Registration registration = new Registration();
        registration.setUser(user);
        registration.setActivity(activity);
        registration.setRegistrationTime(now);

        return registrationRepository.save(registration);
    }

    @Override
    public List<Registration> getUserRegistrations(Long userId) {
        return registrationRepository.findByUserId(userId);
    }

    @Override
    public boolean isUserRegistered(Long userId, Long activityId) {
        return registrationRepository.countByActivityIdAndUserId(activityId, userId) > 0;
    }

    @Override
    public void cancelUserRegistration(Long userId, Long activityId) {
        // 检查用户和活动是否存在
        User user = userRepository.findById(userId).orElse(null);
        Activity activity = activityRepository.findById(activityId).orElse(null);

        if (user == null || activity == null) {
            throw new RuntimeException("用户或活动不存在");
        }

        // 检查用户是否已报名
        if (!isUserRegistered(userId, activityId)) {
            throw new RuntimeException("您尚未报名此活动");
        }

        // 检查活动是否已开始（可选：根据业务需求决定是否允许活动开始后取消报名）
        Date now = new Date();
        if (activity.getStartTime() != null && activity.getStartTime().before(now)) {
            throw new RuntimeException("活动已开始，无法取消报名");
        }

        // 查找并删除报名记录
        Registration registration = registrationRepository.findByUserAndActivity(user, activity).orElse(null);
        if (registration != null) {
            registrationRepository.delete(registration);
        }
    }
    
    
    @Override
    public List<Registration> getRegistrationsByActivityId(Long activityId) {
        return registrationRepository.findByActivityId(activityId);
    }

    @Override
    public long countRegistrationsByActivityId(Long activityId) {
        return registrationRepository.countByActivityId(activityId);
    }

    @Override
    public Map<Long, Long> countRegistrationsForAllActivities() {
        List<Registration> all = getAllRegistrations();
        Map<Long, Long> map = new HashMap<>();
        for (Registration reg : all) {
            Long actId = reg.getActivity().getId();
            map.put(actId, map.getOrDefault(actId, 0L) + 1);
        }
        return map;
    }
}