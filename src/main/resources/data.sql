-- 初始化活动类型数据
INSERT INTO activity_type (name, description) VALUES ('学术讲座', '各类学术研讨会、专家讲座等学术交流活动');
INSERT INTO activity_type (name, description) VALUES ('文艺演出', '音乐会、话剧、舞蹈等文艺表演活动');
INSERT INTO activity_type (name, description) VALUES ('体育竞赛', '各类体育比赛和运动会活动');
INSERT INTO activity_type (name, description) VALUES ('社团活动', '学生社团组织的各类活动');
INSERT INTO activity_type (name, description) VALUES ('志愿服务', '公益志愿服务和社会实践活动');
INSERT INTO activity_type (name, description) VALUES ('实践活动', '实习实训、创新创业等实践类活动');

-- 初始化用户数据
INSERT INTO user (username, password, role, email, real_name) VALUES ('admin', 'admin123', 'ADMIN', 'admin@xmu.edu.cn', '系统管理员');
INSERT INTO user (username, password, role, email, real_name) VALUES ('student1', 'student123', 'USER', 'student1@stu.xmu.edu.cn', '张三');
INSERT INTO user (username, password, role, email, real_name) VALUES ('student2', 'student123', 'USER', 'student2@stu.xmu.edu.cn', '李四');
INSERT INTO user (username, password, role, email, real_name) VALUES ('student3', 'student123', 'USER', 'student3@stu.xmu.edu.cn', '王五');
INSERT INTO user (username, password, role, email, real_name) VALUES ('teacher1', 'teacher123', 'ADMIN', 'teacher1@xmu.edu.cn', '赵老师');

-- 初始化活动数据
INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('人工智能前沿技术讲座', '邀请知名专家分享人工智能最新发展趋势和应用案例，探讨AI技术在各行业的创新应用。', '2024-12-15 14:00:00', '2024-12-15 16:00:00', 100, '学术报告厅A101', 1);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('校园歌手大赛', '展示同学们的音乐才华，为热爱音乐的同学提供展示平台。比赛分为初赛、复赛和决赛三个阶段。', '2024-12-20 19:00:00', '2024-12-20 21:30:00', 200, '大礼堂', 2);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('篮球友谊赛', '各学院篮球队友谊比赛，增进学院间的交流与友谊，提高同学们的团队协作能力。', '2024-12-18 15:30:00', '2024-12-18 17:30:00', 50, '体育馆篮球场', 3);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('编程马拉松大赛', '24小时编程挑战赛，参赛者需要在规定时间内完成指定的编程任务，考验编程技能和创新思维。', '2024-12-22 09:00:00', '2024-12-23 09:00:00', 80, '计算机实验楼B区', 6);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('环保志愿服务活动', '组织同学们参与校园环保活动，清理校园垃圾，宣传环保理念，共建美丽校园。', '2024-12-16 08:00:00', '2024-12-16 12:00:00', 60, '校园各区域', 5);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('创业分享会', '邀请成功创业的校友分享创业经验，为有创业想法的同学提供指导和建议。', '2024-12-25 14:30:00', '2024-12-25 17:00:00', 120, '创新创业中心', 6);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('书法艺术展览', '展示师生优秀书法作品，传承中华传统文化，提高同学们的艺术修养和文化素质。', '2024-12-28 10:00:00', '2024-12-30 18:00:00', 150, '艺术展览馆', 2);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('科技创新竞赛', '鼓励同学们发挥创新精神，展示科技创新成果，培养实践能力和创新思维。', '2024-12-26 13:00:00', '2024-12-26 18:00:00', 90, '科技楼展示厅', 6);

-- 初始化报名数据
INSERT INTO registration (user_id, activity_id, registration_time) VALUES (2, 1, '2024-12-10 10:30:00');
INSERT INTO registration (user_id, activity_id, registration_time) VALUES (3, 1, '2024-12-10 11:15:00');
INSERT INTO registration (user_id, activity_id, registration_time) VALUES (4, 2, '2024-12-11 09:20:00');
INSERT INTO registration (user_id, activity_id, registration_time) VALUES (5, 3, '2024-12-11 14:45:00');
INSERT INTO registration (user_id, activity_id, registration_time) VALUES (2, 4, '2024-12-12 16:30:00');
INSERT INTO registration (user_id, activity_id, registration_time) VALUES (3, 5, '2024-12-12 08:15:00');
INSERT INTO registration (user_id, activity_id, registration_time) VALUES (4, 6, '2024-12-13 13:20:00');
INSERT INTO registration (user_id, activity_id, registration_time) VALUES (5, 7, '2024-12-13 15:45:00');
INSERT INTO registration (user_id, activity_id, registration_time) VALUES (2, 8, '2024-12-14 11:30:00');
