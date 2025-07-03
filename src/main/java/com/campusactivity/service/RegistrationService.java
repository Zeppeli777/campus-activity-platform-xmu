package com.campusactivity.service;

import com.campusactivity.entity.Registration;
import java.util.List;

public interface RegistrationService {
    List<Registration> getAllRegistrations();
    Registration getRegistrationById(Long id);
    Registration createRegistration(Registration registration);
    Registration updateRegistration(Long id, Registration registration);
    void deleteRegistration(Long id);

    // 新增方法
    Registration registerUserForActivity(Long userId, Long activityId);
    List<Registration> getUserRegistrations(Long userId);
    boolean isUserRegistered(Long userId, Long activityId);
}