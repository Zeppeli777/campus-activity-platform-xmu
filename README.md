# 校园活动报名平台
## 功能描述
- 活动发布：管理员发布活动（标题、时间、地点、人数限制），支持富文本编辑。
- 在线报名：学生查看活动详情并提交报名（需检测名额是否已满）。
- 报名管理：管理员导出报名名单，支持批量发送通知（如活动变更提醒，此为可选功能）。
- 数据统计：按活动类型生成参与人数柱状图。
- 其中，富文本编辑和发送活动变更提醒为非必选项
## 技术描述
- 采用maven包管理，直接把根目录下的pom.xml丢给ai就会告诉你我们使用什么插件和依赖和具体版本
- 粗略概括，主要是
  - spring boot
  - spring security
  - maven
  - thymeleaf #前端
  - H2 #数据库
  - 后续添加：如char.js建立柱形统计图
## 开发阶段调试方法
- 使用mvn spring-boot:run
- 在浏览器输入localhost:端口号（8081）查看运行结果
`本项目使用该调试方法暂时有bug`
## 测试阶段构建方法
- gitclone后，用IDEA打开仓库根目录
- 在终端输入mvn clean install（如果失败就在后面再加个 -U）
- 然后，在终端输入java -jar .\target\campus-activity-platform-1.0-SNAPSHOT.jar
- 项目即启动。在任意浏览器网址输入localhost:8081就能进入首页
