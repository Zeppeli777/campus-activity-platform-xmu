package com.campusactivity.controller.web;

import com.campusactivity.entity.Activity;
import com.campusactivity.entity.ActivityType;
import com.campusactivity.entity.Registration;
import com.campusactivity.entity.User;
import com.campusactivity.repository.UserRepository;
import com.campusactivity.service.ActivityService;
import com.campusactivity.service.ActivityTypeService;
import com.campusactivity.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.campusactivity.entity.Notification;
import com.campusactivity.service.NotificationService;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {
    
    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityTypeService activityTypeService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private NotificationService notificationService;
    /**
     * 首页 - 显示欢迎页面和快速导航
     * 根据用户登录状态显示不同的导航栏
     */
    @GetMapping("/")
    public String index(Model model, Authentication authentication,
                       @RequestParam(value = "logout", required = false) String logout) {
        // 添加基本数据
        model.addAttribute("welcomeMessage", "欢迎来到厦门大学校园活动平台");
        model.addAttribute("totalActivities", activityService.getAllActivities().size());

        // 检查是否是退出登录后的跳转
        if ("true".equals(logout)) {
            model.addAttribute("logoutSuccess", true);
        }
        
        // 检查用户登录状态
        boolean isLoggedIn = false;
        boolean isAdmin = false;
        String username = "";
        long unreadCount = 0;
        
        if (authentication != null && authentication.isAuthenticated()) {
            isLoggedIn = true;
            username = authentication.getName();
            
            // 检查是否是管理员
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            
            // 获取未读通知数量
            User user = userRepository.findByUsername(username);
            if (user != null) {
                unreadCount = notificationService.getUnreadNotificationCount(user);
            }
        }
        
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("username", username);
        model.addAttribute("unreadCount", unreadCount);
        
        return "index";
    }
    
    /**
     * 用户活动页面
     * 自动获取当前登录用户信息，不再依赖URL参数
     */
    @GetMapping("/user/activities/page")
    public String userActivities(Model model, Authentication authentication) {
        List<Activity> activities = activityService.getAllActivities();

        // 检查用户登录状态
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() &&
                           !authentication.getName().equals("anonymousUser");

        Long currentUserId = null;
        String currentUsername = null;

        if (isLoggedIn) {
            // 获取当前登录用户信息
            currentUsername = authentication.getName();
            User currentUser = userRepository.findByUsername(currentUsername);
            if (currentUser != null) {
                currentUserId = currentUser.getId();
            }
        }

        // 为每个活动添加状态信息
        Map<Long, String> activityStatuses = new HashMap<>();
        Map<Long, Long> registrationCounts = new HashMap<>();
        Map<Long, Boolean> canRegister = new HashMap<>();

        for (Activity activity : activities) {
            String status = activityService.getActivityStatus(activity.getId(), currentUserId);
            Long count = activityService.getRegistrationCount(activity.getId());
            boolean canReg = activityService.canUserRegister(activity.getId(), currentUserId);

            activityStatuses.put(activity.getId(), status);
            registrationCounts.put(activity.getId(), count);
            canRegister.put(activity.getId(), canReg);
        }

        model.addAttribute("activities", activities);
        model.addAttribute("activityStatuses", activityStatuses);
        model.addAttribute("registrationCounts", registrationCounts);
        model.addAttribute("canRegister", canRegister);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("currentUsername", currentUsername);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("title", "用户活动列表");
        return "user/activity-list";
    }

    /**
     * 用户活动详情页面
     */
    @GetMapping("/user/activities/page/{id}")
    public String userActivityDetail(@PathVariable Long id, Model model, Authentication authentication) {
        Activity activity = activityService.getActivityById(id);
        if (activity == null) {
            model.addAttribute("errorMessage", "活动不存在！");
            return "error/404";
        }

        // 获取报名人数
        Long registrationCount = activityService.getRegistrationCount(id);

        // 检查用户登录状态
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() &&
                           !authentication.getName().equals("anonymousUser");

        Long currentUserId = null;
        boolean isUserRegistered = false;
        boolean canRegister = false;
        String activityStatus = "未知";

        if (isLoggedIn) {
            // 获取当前用户信息
            String username = authentication.getName();
            User currentUser = userRepository.findByUsername(username);
            if (currentUser != null) {
                currentUserId = currentUser.getId();

                // 检查用户是否已报名
                isUserRegistered = registrationService.isUserRegistered(currentUserId, id);

                // 检查是否可以报名
                canRegister = activityService.canUserRegister(id, currentUserId);

                // 获取活动状态
                activityStatus = activityService.getActivityStatus(id, currentUserId);
            }
        }

        // 检查活动是否已结束
        Date now = new Date();
        boolean isActivityEnded = activity.getEndTime() != null && activity.getEndTime().before(now);

        // 检查活动是否已满额
        boolean isActivityFull = activity.getCapacity() != null && registrationCount >= activity.getCapacity();

        model.addAttribute("activity", activity);
        model.addAttribute("registrationCount", registrationCount);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("isUserRegistered", isUserRegistered);
        model.addAttribute("canRegister", canRegister);
        model.addAttribute("activityStatus", activityStatus);
        model.addAttribute("isActivityEnded", isActivityEnded);
        model.addAttribute("isActivityFull", isActivityFull);
        model.addAttribute("title", "活动详情 - " + activity.getTitle());
        return "user/activity-detail";
    }
    
    /**
     * 活动管理页面
     */
    @GetMapping("/admin/activities/page")
    public String adminActivities(Model model) {
        List<Activity> activities = activityService.getAllActivities();
        model.addAttribute("activities", activities);
        model.addAttribute("title", "活动管理列表");
        return "admin/activity-list";
    }

    /**
     * 活动管理详情页面
     */
    @GetMapping("/admin/activities/page/{id}")
    public String adminActivityDetail(@PathVariable Long id, Model model) {
        Activity activity = activityService.getActivityById(id);
        if (activity == null) {
            return "redirect:/admin/activities/page";
        }
        model.addAttribute("activity", activity);
        model.addAttribute("title", "活动详情 - " + activity.getTitle());
        return "admin/activity-detail";
    }

    /**
     * 显示新增活动表单页面
     */
    @GetMapping("/admin/activities/new")
    public String showCreateActivityForm(Model model) {
        List<ActivityType> activityTypes = activityTypeService.getAllActivityTypes();
        model.addAttribute("activityTypes", activityTypes);
        model.addAttribute("activity", new Activity());
        model.addAttribute("title", "新增活动");
        model.addAttribute("isEdit", false);//修复了增加活动页面失效的bug
        return "admin/activity-form";
    }

    /**
     * 处理新增活动表单提交
     */
    @PostMapping("/admin/activities/new")
    public String createActivity(@RequestParam String title,
                                @RequestParam String description,
                                @RequestParam String startTime,
                                @RequestParam String endTime,
                                @RequestParam String location,
                                @RequestParam(required = false) Integer capacity,
                                @RequestParam(required = false) Long activityTypeId,
                                RedirectAttributes redirectAttributes) {
        try {
            // 创建新活动对象
            Activity activity = new Activity();
            activity.setTitle(title);
            activity.setDescription(description);
            activity.setLocation(location);
            activity.setCapacity(capacity);

            // 解析时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            if (startTime != null && !startTime.isEmpty()) {
                activity.setStartTime(dateFormat.parse(startTime));
            }
            if (endTime != null && !endTime.isEmpty()) {
                activity.setEndTime(dateFormat.parse(endTime));
            }

            // 设置活动类型
            if (activityTypeId != null) {
                ActivityType activityType = activityTypeService.getActivityTypeById(activityTypeId);
                activity.setActivityType(activityType);
            }

            // 保存活动
            activityService.createActivity(activity);
            
            redirectAttributes.addFlashAttribute("successMessage", "活动创建成功！");
            return "redirect:/admin/activities/page";

        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "时间格式错误，请重新输入！");
            return "redirect:/admin/activities/new";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "创建活动失败：" + e.getMessage());
            return "redirect:/admin/activities/new";
        }
    }

    /**
     * 显示编辑活动表单页面
     */
    @GetMapping("/admin/activities/edit/{id}")
    public String showEditActivityForm(@PathVariable Long id, Model model) {
        Activity activity = activityService.getActivityById(id);
        if (activity == null) {
            return "redirect:/admin/activities/page";
        }

        List<ActivityType> activityTypes = activityTypeService.getAllActivityTypes();
        model.addAttribute("activityTypes", activityTypes);
        model.addAttribute("activity", activity);
        model.addAttribute("title", "编辑活动 - " + activity.getTitle());
        model.addAttribute("isEdit", true);
        return "admin/activity-form";
    }

    /**
     * 处理编辑活动表单提交
     */
    @PostMapping("/admin/activities/edit/{id}")
    public String updateActivity(@PathVariable Long id,
                                @RequestParam String title,
                                @RequestParam String description,
                                @RequestParam String startTime,
                                @RequestParam String endTime,
                                @RequestParam String location,
                                @RequestParam(required = false) Integer capacity,
                                @RequestParam(required = false) Long activityTypeId,
                                RedirectAttributes redirectAttributes) {
        try {
            Activity activity = activityService.getActivityById(id);
            if (activity == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "活动不存在！");
                return "redirect:/admin/activities/page";
            }

            // 更新活动信息
            activity.setTitle(title);
            activity.setDescription(description);
            activity.setLocation(location);
            activity.setCapacity(capacity);

            // 解析时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            if (startTime != null && !startTime.isEmpty()) {
                activity.setStartTime(dateFormat.parse(startTime));
            }
            if (endTime != null && !endTime.isEmpty()) {
                activity.setEndTime(dateFormat.parse(endTime));
            }

            // 设置活动类型
            if (activityTypeId != null) {
                ActivityType activityType = activityTypeService.getActivityTypeById(activityTypeId);
                activity.setActivityType(activityType);
            }

            // 保存活动
            activityService.updateActivity(id, activity);
            List<Registration> registrations = registrationService.getRegistrationsByActivityId(id);
            String message = String.format("活动 '%s' 已被更新，请查看最新信息。", title);
            for (Registration registration : registrations) {
               User user = registration.getUser();
               notificationService.sendNotification(user, message);
            }

            redirectAttributes.addFlashAttribute("successMessage", "活动更新成功！");
            return "redirect:/admin/activities/page";

        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "时间格式错误，请重新输入！");
            return "redirect:/admin/activities/edit/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "更新活动失败：" + e.getMessage());
            return "redirect:/admin/activities/edit/" + id;
        }
    }
    
    
    /**
     * 报名活动 - GET方式处理（从活动详情页面跳转）
     */
    @GetMapping("/user/registrations/enroll")
    public String enrollActivity(@RequestParam Long activityId,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        try {
            // 检查用户是否登录
            if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getName().equals("anonymousUser")) {
                redirectAttributes.addFlashAttribute("error", "请先登录后再报名！");
                return "redirect:/login";
            }

            // 获取当前用户
            String username = authentication.getName();
            User currentUser = userRepository.findByUsername(username);
            if (currentUser == null) {
                redirectAttributes.addFlashAttribute("error", "用户信息不存在！");
                return "redirect:/login";
            }

            // 执行报名
            Registration registration = registrationService.registerUserForActivity(currentUser.getId(), activityId);
            redirectAttributes.addFlashAttribute("success", "报名成功！");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        // 重定向回活动详情页面
        return "redirect:/user/activities/page/" + activityId;
    }

    /**
     * 报名活动 - POST方式处理（AJAX调用）
     */
    @PostMapping("/user/activities/register")
    @ResponseBody
    public Map<String, Object> registerActivity(@RequestParam Long activityId,
                                               @RequestParam Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            Registration registration = registrationService.registerUserForActivity(userId, activityId);
            result.put("success", true);
            result.put("message", "报名成功！");
            result.put("registrationId", registration.getId());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 取消报名活动 - POST方式处理（AJAX调用）
     */
    @PostMapping("/user/activities/cancel")
    @ResponseBody
    public Map<String, Object> cancelActivity(@RequestParam Long activityId,
                                             @RequestParam Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            registrationService.cancelUserRegistration(userId, activityId);
            result.put("success", true);
            result.put("message", "取消报名成功！");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 取消报名活动 - GET方式处理（从活动详情页面跳转）
     */
    @GetMapping("/user/registrations/cancel")
    public String cancelActivityRegistration(@RequestParam Long activityId,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        try {
            // 检查用户是否登录
            if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getName().equals("anonymousUser")) {
                redirectAttributes.addFlashAttribute("error", "请先登录后再操作！");
                return "redirect:/login";
            }

            // 获取当前用户
            String username = authentication.getName();
            User currentUser = userRepository.findByUsername(username);
            if (currentUser == null) {
                redirectAttributes.addFlashAttribute("error", "用户信息不存在！");
                return "redirect:/login";
            }

            // 执行取消报名
            registrationService.cancelUserRegistration(currentUser.getId(), activityId);
            redirectAttributes.addFlashAttribute("success", "取消报名成功！");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        // 重定向回活动详情页面
        return "redirect:/user/activities/page/" + activityId;
    }

    /**
     * 我的报名页面
     */
    @GetMapping("/user/my-registrations")
    public String myRegistrations(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        List<Registration> registrations = registrationService.getUserRegistrations(user.getId());
        model.addAttribute("registrations", registrations);
        model.addAttribute("currentUserRealname", user.getRealName()); // 传递真实姓名
        model.addAttribute("currentUsername", username); // 传递用户名
        model.addAttribute("title", "我的报名");
        return "user/my-registrations";
    }



    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 欢迎页面 - 可作为备选首页
     */
    @GetMapping("/welcome")
    public String welcome(Model model) {
        List<Activity> recentActivities = activityService.getAllActivities();
        model.addAttribute("recentActivities", recentActivities);
        model.addAttribute("title", "欢迎页面");
        return "welcome";
    }

    /**
     * 活动大厅 - 可作为备选首页
     */
    @GetMapping("/hall")
    public String activityHall(Model model) {
        List<Activity> activities = activityService.getAllActivities();
        model.addAttribute("activities", activities);
        model.addAttribute("title", "活动大厅");
        return "activity-hall";
    }
    /**
     * 单活动报名信息
     */

    @GetMapping("/admin/activities/{id}/registrations")
    public String registrations(@PathVariable Long id, Model model) {
        Activity activity = activityService.getActivityById(id);
        if (activity == null) {
            model.addAttribute("errorMessage", "活动不存在！");
            return "error/404"; // 或自定义一个友好错误页面
        }
        List<Registration> registrations = registrationService.getRegistrationsByActivityId(id);
        model.addAttribute("activity", activity);
        model.addAttribute("registrations", registrations);
        return "admin/activity-registrations";
    }
    /**
     * 统计报名信息
     */
    
    /**
     * 统计所有活动报名人数页面
     */
    @GetMapping("/admin/statistics")
    public String activityStatistics(Model model) {
        List<Activity> activities = activityService.getAllActivities();
        // 获取每个活动的报名人数统计
        java.util.Map<Long, Long> registrationCountMap = registrationService.countRegistrationsForAllActivities();
        model.addAttribute("activities", activities != null ? activities : new ArrayList<>());
        model.addAttribute("registrationCountMap", registrationCountMap != null ? registrationCountMap : new HashMap<>());
        model.addAttribute("title", "活动报名统计");

        return "admin/statistics";
    }
        /**
     * 通知界面
     */
    @GetMapping("/notifications")
    public String notifications(Model model, Authentication authentication) {
        // 检查 authentication 是否为 null
        if (authentication == null) {
            // 重定向到登录页面，可根据实际需求修改
            return "redirect:/login";
        }
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        // 检查 user 是否为 null
        if (user == null) {
            // 添加错误信息
            model.addAttribute("errorMessage", "未找到对应的用户信息，请重新登录。");
            // 重定向到登录页面，可根据实际需求修改
            return "redirect:/login";
        }

        List<Notification> unreadNotifications = notificationService.getUserUnreadNotifications(user);
        List<Notification> allNotifications = notificationService.getUserNotifications(user);
        long unreadCount = notificationService.getUnreadNotificationCount(user);

        // 处理通知列表为空的情况
        model.addAttribute("unreadNotifications", unreadNotifications != null ? unreadNotifications : new ArrayList<>());
        model.addAttribute("allNotifications", allNotifications != null ? allNotifications : new ArrayList<>());
        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("title", "通知中心");
        return "user/notification";
    }

    /**
     * 标记单个通知为已读
     */
    @PostMapping("/markAsRead")
    public String markAsRead(@RequestParam Long notificationId, RedirectAttributes redirectAttributes) {
        try {
            notificationService.markAsRead(notificationId);
            redirectAttributes.addFlashAttribute("successMessage", "通知已标记为已读");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "标记通知失败: " + e.getMessage());
        }
        return "redirect:/notifications";
    }

    /**
     * 标记所有通知为已读
     */
    @PostMapping("/markAllAsRead")
    public String markAllAsRead(Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            if (authentication == null) {
                return "redirect:/login";
            }
            
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            
            if (user == null) {
                return "redirect:/login";
            }
            
            List<Notification> unreadNotifications = notificationService.getUserUnreadNotifications(user);
            for (Notification notification : unreadNotifications) {
                notificationService.markAsRead(notification.getId());
            }
            
            redirectAttributes.addFlashAttribute("successMessage", "所有通知已标记为已读");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "标记通知失败: " + e.getMessage());
        }
        return "redirect:/notifications";
    }

}


