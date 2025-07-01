package com.campusactivity.controller.web;

import com.campusactivity.entity.Activity;
import com.campusactivity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class WebController {
    
    @Autowired
    private ActivityService activityService;
    
    /**
     * 首页 - 显示欢迎页面和快速导航
     * 可以在这里添加数据，传递给前端模板
     */
    @GetMapping("/")
    public String index(Model model) {
        // 可以添加一些数据传递给前端
        model.addAttribute("welcomeMessage", "欢迎来到厦门大学校园活动平台");
        model.addAttribute("totalActivities", activityService.getAllActivities().size());

        return "index"; // 返回 templates/index.html 模板
    }
    
    /**
     * 用户活动页面
     */
    @GetMapping("/user/activities/page")
    public String userActivities(Model model) {
        List<Activity> activities = activityService.getAllActivities();
        model.addAttribute("activities", activities);
        model.addAttribute("title", "用户活动列表");
        return "user/activity-list";
    }

    /**
     * 用户活动详情页面
     */
    @GetMapping("/user/activities/page/{id}")
    public String userActivityDetail(@PathVariable Long id, Model model) {
        Activity activity = activityService.getActivityById(id);
        model.addAttribute("activity", activity);
        model.addAttribute("title", "活动详情");
        return "user/activity-detail";
    }
    
    /**
     * 管理员活动页面
     */
    @GetMapping("/admin/activities/page")
    public String adminActivities(Model model) {
        List<Activity> activities = activityService.getAllActivities();
        model.addAttribute("activities", activities);
        model.addAttribute("title", "管理员活动列表");
        return "admin/activity-list";
    }
    
    /**
     * 我的报名页面
     */
    @GetMapping("/user/my-registrations")
    public String myRegistrations(Model model) {
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
}
