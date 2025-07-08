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
('人工智能前沿技术讲座', '邀请知名专家分享人工智能最新发展趋势和应用案例，探讨AI技术在各行业的创新应用。', '2025-12-15 14:00:00', '2025-12-15 16:00:00', 100, '学术报告厅A101', 1);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('校园歌手大赛', '展示同学们的音乐才华，为热爱音乐的同学提供展示平台。比赛分为初赛、复赛和决赛三个阶段。', '2025-12-20 19:00:00', '2025-12-20 21:30:00', 10, '大礼堂', 2);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('篮球友谊赛', '各学院篮球队友谊比赛，增进学院间的交流与友谊，提高同学们的团队协作能力。', '2024-12-18 15:30:00', '2024-12-18 17:30:00', 50, '体育馆篮球场', 3);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('编程马拉松大赛', '24小时编程挑战赛，参赛者需要在规定时间内完成指定的编程任务，考验编程技能和创新思维。', '2025-12-22 09:00:00', '2025-12-23 09:00:00', 80, '计算机实验楼B区', 6);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('环保志愿服务活动', '组织同学们参与校园环保活动，清理校园垃圾，宣传环保理念，共建美丽校园。', '2025-12-16 08:00:00', '2025-12-16 12:00:00', 60, '校园各区域', 5);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('创业分享会', '邀请成功创业的校友分享创业经验，为有创业想法的同学提供指导和建议。', '2025-12-25 14:30:00', '2025-12-25 17:00:00', 120, '创新创业中心', 6);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('书法艺术展览', '展示师生优秀书法作品，传承中华传统文化，提高同学们的艺术修养和文化素质。', '2024-12-28 10:00:00', '2024-12-30 18:00:00', 150, '艺术展览馆', 2);

INSERT INTO activity (title, description, start_time, end_time, capacity, location, activity_type_id) VALUES
('科技创新竞赛', '鼓励同学们发挥创新精神，展示科技创新成果，培养实践能力和创新思维。', '2025-12-26 13:00:00', '2025-12-26 18:00:00', 90, '科技楼展示厅', 6);

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

-- ========== 数据扩展部分开始 ==========
-- ----------------------------

-- Part 1: 扩展学生用户至100个 (新增97名学生)
-- 假设新用户的ID从6开始自增
-- ----------------------------
INSERT INTO user (username, password, role, email, real_name) VALUES
('student4', 'student123', 'USER', 'student4@stu.xmu.edu.cn', '陈浩'),
('student5', 'student123', 'USER', 'student5@stu.xmu.edu.cn', '刘静'),
('student6', 'student123', 'USER', 'student6@stu.xmu.edu.cn', '黄磊'),
('student7', 'student123', 'USER', 'student7@stu.xmu.edu.cn', '吴越'),
('student8', 'student123', 'USER', 'student8@stu.xmu.edu.cn', '周敏'),
('student9', 'student123', 'USER', 'student9@stu.xmu.edu.cn', '杨帆'),
('student10', 'student123', 'USER', 'student10@stu.xmu.edu.cn', '赵娜'),
('student11', 'student123', 'USER', 'student11@stu.xmu.edu.cn', '孙强'),
('student12', 'student123', 'USER', 'student12@stu.xmu.edu.cn', '马林'),
('student13', 'student123', 'USER', 'student13@stu.xmu.edu.cn', '胡伟'),
('student14', 'student123', 'USER', 'student14@stu.xmu.edu.cn', '朱珠'),
('student15', 'student123', 'USER', 'student15@stu.xmu.edu.cn', '郑爽'),
('student16', 'student123', 'USER', 'student16@stu.xmu.edu.cn', '林杰'),
('student17', 'student123', 'USER', 'student17@stu.xmu.edu.cn', '何俊'),
('student18', 'student123', 'USER', 'student18@stu.xmu.edu.cn', '高翔'),
('student19', 'student123', 'USER', 'student19@stu.xmu.edu.cn', '梁思'),
('student20', 'student123', 'USER', 'student20@stu.xmu.edu.cn', '宋超'),
('student21', 'student123', 'USER', 'student21@stu.xmu.edu.cn', '唐娟'),
('student22', 'student123', 'USER', 'student22@stu.xmu.edu.cn', '许文'),
('student23', 'student123', 'USER', 'student23@stu.xmu.edu.cn', '邓宇'),
('student24', 'student123', 'USER', 'student24@stu.xmu.edu.cn', '冯雷'),
('student25', 'student123', 'USER', 'student25@stu.xmu.edu.cn', '曹阳'),
('student26', 'student123', 'USER', 'student26@stu.xmu.edu.cn', '彭涛'),
('student27', 'student123', 'USER', 'student27@stu.xmu.edu.cn', '程琳'),
('student28', 'student123', 'USER', 'student28@stu.xmu.edu.cn', '袁媛'),
('student29', 'student123', 'USER', 'student29@stu.xmu.edu.cn', '苏珊'),
('student30', 'student123', 'USER', 'student30@stu.xmu.edu.cn', '董雪'),
('student31', 'student123', 'USER', 'student31@stu.xmu.edu.cn', '蒋欣'),
('student32', 'student123', 'USER', 'student32@stu.xmu.edu.cn', '范明'),
('student33', 'student123', 'USER', 'student33@stu.xmu.edu.cn', '丁一'),
('student34', 'student123', 'USER', 'student34@stu.xmu.edu.cn', '田甜'),
('student35', 'student123', 'USER', 'student35@stu.xmu.edu.cn', '任远'),
('student36', 'student123', 'USER', 'student36@stu.xmu.edu.cn', '夏雨'),
('student37', 'student123', 'USER', 'student37@stu.xmu.edu.cn', '钟华'),
('student38', 'student123', 'USER', 'student38@stu.xmu.edu.cn', '姜维'),
('student39', 'student123', 'USER', 'student39@stu.xmu.edu.cn', '姚晨'),
('student40', 'student123', 'USER', 'student40@stu.xmu.edu.cn', '叶静'),
('student41', 'student123', 'USER', 'student41@stu.xmu.edu.cn', '秦朗'),
('student42', 'student123', 'USER', 'student42@stu.xmu.edu.cn', '薛晴'),
('student43', 'student123', 'USER', 'student43@stu.xmu.edu.cn', '常乐'),
('student44', 'student123', 'USER', 'student44@stu.xmu.edu.cn', '孟非'),
('student45', 'student123', 'USER', 'student45@stu.xmu.edu.cn', '万山'),
('student46', 'student123', 'USER', 'student46@stu.xmu.edu.cn', '雷军'),
('student47', 'student123', 'USER', 'student47@stu.xmu.edu.cn', '方圆'),
('student48', 'student123', 'USER', 'student48@stu.xmu.edu.cn', '段誉'),
('student49', 'student123', 'USER', 'student49@stu.xmu.edu.cn', '石磊'),
('student50', 'student123', 'USER', 'student50@stu.xmu.edu.cn', '熊壮'),
('student51', 'student123', 'USER', 'student51@stu.xmu.edu.cn', '崔健'),
('student52', 'student123', 'USER', 'student52@stu.xmu.edu.cn', '潘安'),
('student53', 'student123', 'USER', 'student53@stu.xmu.edu.cn', '白露'),
('student54', 'student123', 'USER', 'student54@stu.xmu.edu.cn', '毛宁'),
('student55', 'student123', 'USER', 'student55@stu.xmu.edu.cn', '尹平'),
('student56', 'student123', 'USER', 'student56@stu.xmu.edu.cn', '孔明'),
('student57', 'student123', 'USER', 'student57@stu.xmu.edu.cn', '邵伟'),
('student58', 'student123', 'USER', 'student58@stu.xmu.edu.cn', '严宽'),
('student59', 'student123', 'USER', 'student59@stu.xmu.edu.cn', '华生'),
('student60', 'student123', 'USER', 'student60@stu.xmu.edu.cn', '金鑫'),
('student61', 'student123', 'USER', 'student61@stu.xmu.edu.cn', '魏晨'),
('student62', 'student123', 'USER', 'student62@stu.xmu.edu.cn', '陶然'),
('student63', 'student123', 'USER', 'student63@stu.xmu.edu.cn', '戚薇'),
('student64', 'student123', 'USER', 'student64@stu.xmu.edu.cn', '谢娜'),
('student65', 'student123', 'USER', 'student65@stu.xmu.edu.cn', '施南'),
('student66', 'student123', 'USER', 'student66@stu.xmu.edu.cn', '章天'),
('student67', 'student123', 'USER', 'student67@stu.xmu.edu.cn', '龙飞'),
('student68', 'student123', 'USER', 'student68@stu.xmu.edu.cn', '侯亮'),
('student69', 'student123', 'USER', 'student69@stu.xmu.edu.cn', '路遥'),
('student70', 'student123', 'USER', 'student70@stu.xmu.edu.cn', '牛犇'),
('student71', 'student123', 'USER', 'student71@stu.xmu.edu.cn', '盖伦'),
('student72', 'student123', 'USER', 'student72@stu.xmu.edu.cn', '焦恩俊'),
('student73', 'student123', 'USER', 'student73@stu.xmu.edu.cn', '耿耿'),
('student74', 'student123', 'USER', 'student74@stu.xmu.edu.cn', '米雪'),
('student75', 'student123', 'USER', 'student75@stu.xmu.edu.cn', '屈原'),
('student76', 'student123', 'USER', 'student76@stu.xmu.edu.cn', '景甜'),
('student77', 'student123', 'USER', 'student77@stu.xmu.edu.cn', '官鸿'),
('student78', 'student123', 'USER', 'student78@stu.xmu.edu.cn', '艾伦'),
('student79', 'student123', 'USER', 'student79@stu.xmu.edu.cn', '庞统'),
('student80', 'student123', 'USER', 'student80@stu.xmu.edu.cn', '安琪'),
('student81', 'student123', 'USER', 'student81@stu.xmu.edu.cn', '欧阳娜娜'),
('student82', 'student123', 'USER', 'student82@stu.xmu.edu.cn', '上官婉儿'),
('student83', 'student123', 'USER', 'student83@stu.xmu.edu.cn', '司马懿'),
('student84', 'student123', 'USER', 'student84@stu.xmu.edu.cn', '诸葛亮'),
('student85', 'student123', 'USER', 'student85@stu.xmu.edu.cn', '夏侯惇'),
('student86', 'student123', 'USER', 'student86@stu.xmu.edu.cn', '公孙离'),
('student87', 'student123', 'USER', 'student87@stu.xmu.edu.cn', '东方曜'),
('student88', 'student123', 'USER', 'student88@stu.xmu.edu.cn', '南宫月'),
('student89', 'student123', 'USER', 'student89@stu.xmu.edu.cn', '西门吹雪'),
('student90', 'student123', 'USER', 'student90@stu.xmu.edu.cn', '北堂墨'),
('student91', 'student123', 'USER', 'student91@stu.xmu.edu.cn', '第五嫣'),
('student92', 'student123', 'USER', 'student92@stu.xmu.edu.cn', '端木蓉'),
('student93', 'student123', 'USER', 'student93@stu.xmu.edu.cn', '赫连勃勃'),
('student94', 'student123', 'USER', 'student94@stu.xmu.edu.cn', '皇甫嵩'),
('student95', 'student123', 'USER', 'student95@stu.xmu.edu.cn', '尉迟恭'),
('student96', 'student123', 'USER', 'student96@stu.xmu.edu.cn', '长孙无忌'),
('student97', 'student123', 'USER', 'student97@stu.xmu.edu.cn', '慕容复'),
('student98', 'student123', 'USER', 'student98@stu.xmu.edu.cn', '宇文成都'),
('student99', 'student123', 'USER', 'student99@stu.xmu.edu.cn', '令狐冲'),
('student100', 'student123', 'USER', 'student100@stu.xmu.edu.cn', '轩辕剑');


-- ----------------------------
-- Part 2: 扩展报名信息至200条
-- 原始数据有9条，需要新增191条
-- 假设原始用户的ID为1-5，新用户的ID为6-102
-- 学生用户的ID范围是: 2,3,4, 6-102
-- ----------------------------

-- 首先，将ID为2的活动“校园歌手大赛”（容量10）报满
-- 原始数据中 user_id=4 已报名，还需9个
INSERT INTO registration (user_id, activity_id, registration_time) VALUES
(2, 2, '2024-12-11 09:25:11'),
(3, 2, '2024-12-11 09:30:23'),
(6, 2, '2024-12-11 10:05:00'),
(7, 2, '2024-12-11 10:11:34'),
(8, 2, '2024-12-11 11:23:19'),
(9, 2, '2024-12-11 13:45:56'),
(10, 2, '2024-12-11 14:01:02'),
(11, 2, '2024-12-11 14:02:15'),
(12, 2, '2024-12-11 15:33:07');

-- 其次，随机生成剩余的182条报名信息
-- 确保活动不超员，且用户不重复报名
INSERT INTO registration (user_id, activity_id, registration_time) VALUES
(13, 1, '2024-12-10 11:20:00'),
(14, 1, '2024-12-10 11:25:00'),
(15, 1, '2024-12-10 11:30:00'),
(16, 1, '2024-12-10 11:35:00'),
(17, 1, '2024-12-10 11:40:00'),
(18, 1, '2024-12-10 11:45:00'),
(19, 1, '2024-12-10 11:50:00'),
(20, 1, '2024-12-10 11:55:00'),
(21, 1, '2024-12-10 12:00:00'),
(22, 1, '2024-12-10 12:05:00'),
(6, 3, '2024-12-11 15:00:00'),
(7, 3, '2024-12-11 15:05:00'),
(8, 3, '2024-12-11 15:10:00'),
(9, 3, '2024-12-11 15:15:00'),
(10, 3, '2024-12-11 15:20:00'),
(11, 3, '2024-12-11 15:25:00'),
(12, 3, '2024-12-11 15:30:00'),
(13, 3, '2024-12-11 15:35:00'),
(14, 3, '2024-12-11 15:40:00'),
(15, 3, '2024-12-11 15:45:00'),
(3, 4, '2024-12-12 16:35:00'),
(4, 4, '2024-12-12 16:40:00'),
(6, 4, '2024-12-12 16:45:00'),
(7, 4, '2024-12-12 16:50:00'),
(8, 4, '2024-12-12 16:55:00'),
(9, 4, '2024-12-12 17:00:00'),
(10, 4, '2024-12-12 17:05:00'),
(11, 4, '2024-12-12 17:10:00'),
(12, 4, '2024-12-12 17:15:00'),
(13, 4, '2024-12-12 17:20:00'),
(14, 4, '2024-12-12 17:25:00'),
(15, 4, '2024-12-12 17:30:00'),
(16, 4, '2024-12-12 17:35:00'),
(17, 4, '2024-12-12 17:40:00'),
(18, 4, '2024-12-12 17:45:00'),
(4, 5, '2024-12-12 08:20:00'),
(6, 5, '2024-12-12 08:25:00'),
(7, 5, '2024-12-12 08:30:00'),
(8, 5, '2024-12-12 08:35:00'),
(9, 5, '2024-12-12 08:40:00'),
(10, 5, '2024-12-12 08:45:00'),
(11, 5, '2024-12-12 08:50:00'),
(12, 5, '2024-12-12 08:55:00'),
(13, 5, '2024-12-12 09:00:00'),
(14, 5, '2024-12-12 09:05:00'),
(15, 5, '2024-12-12 09:10:00'),
(16, 5, '2024-12-12 09:15:00'),
(17, 5, '2024-12-12 09:20:00'),
(18, 5, '2024-12-12 09:25:00'),
(19, 5, '2024-12-12 09:30:00'),
(20, 5, '2024-12-12 09:35:00'),
(2, 6, '2024-12-13 13:25:00'),
(3, 6, '2024-12-13 13:30:00'),
(6, 6, '2024-12-13 13:35:00'),
(7, 6, '2024-12-13 13:40:00'),
(8, 6, '2024-12-13 13:45:00'),
(9, 6, '2024-12-13 13:50:00'),
(10, 6, '2024-12-13 13:55:00'),
(11, 6, '2024-12-13 14:00:00'),
(12, 6, '2024-12-13 14:05:00'),
(13, 6, '2024-12-13 14:10:00'),
(14, 6, '2024-12-13 14:15:00'),
(15, 6, '2024-12-13 14:20:00'),
(16, 6, '2024-12-13 14:25:00'),
(17, 6, '2024-12-13 14:30:00'),
(18, 6, '2024-12-13 14:35:00'),
(19, 6, '2024-12-13 14:40:00'),
(20, 6, '2024-12-13 14:45:00'),
(21, 6, '2024-12-13 14:50:00'),
(22, 6, '2024-12-13 14:55:00'),
(23, 6, '2024-12-13 15:00:00'),
(2, 7, '2024-12-13 15:50:00'),
(3, 7, '2024-12-13 15:55:00'),
(4, 7, '2024-12-13 16:00:00'),
(6, 7, '2024-12-13 16:05:00'),
(7, 7, '2024-12-13 16:10:00'),
(8, 7, '2024-12-13 16:15:00'),
(9, 7, '2024-12-13 16:20:00'),
(10, 7, '2024-12-13 16:25:00'),
(11, 7, '2024-12-13 16:30:00'),
(12, 7, '2024-12-13 16:35:00'),
(13, 7, '2024-12-13 16:40:00'),
(14, 7, '2024-12-13 16:45:00'),
(15, 7, '2024-12-13 16:50:00'),
(16, 7, '2024-12-13 16:55:00'),
(17, 7, '2024-12-13 17:00:00'),
(18, 7, '2024-12-13 17:05:00'),
(19, 7, '2024-12-13 17:10:00'),
(20, 7, '2024-12-13 17:15:00'),
(21, 7, '2024-12-13 17:20:00'),
(22, 7, '2024-12-13 17:25:00'),
(23, 7, '2024-12-13 17:30:00'),
(24, 7, '2024-12-13 17:35:00'),
(25, 7, '2024-12-13 17:40:00'),
(26, 7, '2024-12-13 17:45:00'),
(27, 7, '2024-12-13 17:50:00'),
(3, 8, '2024-12-14 11:35:00'),
(4, 8, '2024-12-14 11:40:00'),
(6, 8, '2024-12-14 11:45:00'),
(7, 8, '2024-12-14 11:50:00'),
(8, 8, '2024-12-14 11:55:00'),
(9, 8, '2024-12-14 12:00:00'),
(10, 8, '2024-12-14 12:05:00'),
(11, 8, '2024-12-14 12:10:00'),
(12, 8, '2024-12-14 12:15:00'),
(13, 8, '2024-12-14 12:20:00'),
(14, 8, '2024-12-14 12:25:00'),
(15, 8, '2024-12-14 12:30:00'),
(16, 8, '2024-12-14 12:35:00'),
(17, 8, '2024-12-14 12:40:00'),
(18, 8, '2024-12-14 12:45:00'),
(19, 8, '2024-12-14 12:50:00'),
(20, 8, '2024-12-14 12:55:00'),
(21, 8, '2024-12-14 13:00:00'),
(22, 8, '2024-12-14 13:05:00'),
(23, 8, '2024-12-14 13:10:00'),
(24, 8, '2024-12-14 13:15:00'),
(25, 8, '2024-12-14 13:20:00'),
(26, 8, '2024-12-14 13:25:00'),
(27, 8, '2024-12-14 13:30:00'),
(28, 8, '2024-12-14 13:35:00'),
(29, 8, '2024-12-14 13:40:00'),
(30, 8, '2024-12-14 13:45:00'),
(31, 8, '2024-12-14 13:50:00'),
(32, 8, '2024-12-14 13:55:00'),
(33, 8, '2024-12-14 14:00:00'),
(34, 1, '2024-12-10 12:10:00'),
(35, 1, '2024-12-10 12:15:00'),
(36, 1, '2024-12-10 12:20:00'),
(37, 1, '2024-12-10 12:25:00'),
(38, 1, '2024-12-10 12:30:00'),
(39, 1, '2024-12-10 12:35:00'),
(40, 1, '2024-12-10 12:40:00'),
(41, 1, '2024-12-10 12:45:00'),
(42, 1, '2024-12-10 12:50:00'),
(43, 1, '2024-12-10 12:55:00'),
(44, 1, '2024-12-10 13:00:00'),
(23, 1, '2024-12-10 12:06:00'),
(24, 1, '2024-12-10 12:07:00'),
(25, 1, '2024-12-10 12:08:00'),
(26, 3, '2024-12-11 15:46:00'),
(27, 3, '2024-12-11 15:47:00'),
(28, 3, '2024-12-11 15:48:00'),
(29, 4, '2024-12-12 17:46:00'),
(30, 4, '2024-12-12 17:47:00'),
(31, 4, '2024-12-12 17:48:00'),
(32, 5, '2024-12-12 09:36:00'),
(33, 5, '2024-12-12 09:37:00'),
(34, 5, '2024-12-12 09:38:00'),
(35, 6, '2024-12-13 15:01:00'),
(36, 6, '2024-12-13 15:02:00'),
(37, 6, '2024-12-13 15:03:00'),
(38, 7, '2024-12-13 17:51:00'),
(39, 7, '2024-12-13 17:52:00'),
(40, 7, '2024-12-13 17:53:00'),
(41, 8, '2024-12-14 14:01:00'),
(42, 8, '2024-12-14 14:02:00'),
(43, 8, '2024-12-14 14:03:00'),
(44, 8, '2024-12-14 14:04:00'),
(45, 1, '2024-12-14 14:05:00'),
(46, 3, '2024-12-14 14:06:00'),
(47, 4, '2024-12-14 14:07:00'),
(48, 5, '2024-12-14 14:08:00'),
(49, 6, '2024-12-14 14:09:00'),
(50, 7, '2024-12-14 14:10:00'),
(51, 8, '2024-12-14 14:11:00'),
(52, 1, '2024-12-14 14:12:00'),
(53, 3, '2024-12-14 14:13:00'),
(54, 4, '2024-12-14 14:14:00');

-- ----------------------------
-- ========== 数据扩展部分结束 ==========
-- ----------------------------