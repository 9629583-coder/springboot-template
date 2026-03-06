# 问题与解决

## 1. 配置 Maven 和 Git 环境

### 问题
项目开发前需要先配置 Maven 和 Git 环境。

### 解决步骤
1. 配置 Maven 的 `settings.xml`
2. 配置本地仓库路径
3. 安装 Git
4. 配置 Git 用户名和邮箱
5. 在项目文件夹打开 Git Bash
6. 使用 Git 管理项目并连接 GitHub

---

## 2. 项目启动失败：Failed to configure a DataSource

### 问题
项目运行时报错：

`Failed to configure a DataSource`

### 解决步骤
1. 检查项目是否引入了数据库相关依赖
2. 如果暂时不用数据库，删除相关依赖  
   或在启动类排除数据源自动配置
3. 如果需要数据库，补全数据库连接配置：
   - url
   - username
   - password
   - driver-class-name

---

## 3. Git 提交规范

### 问题
提交记录不规范，后面难以查看每次修改内容。

### 解决步骤
1. 每次提交只处理一类内容
2. 提交信息直接写清楚本次改动
3. 常用格式：
   - `init: 初始化项目`
   - `feat: 新增接口`
   - `fix: 修复报错`
   - `docs: 补充笔记`
   - `chore: 配置环境`

---

## 4. “拒绝连接”

### 问题
浏览器访问接口时显示拒绝连接。

### 结论
一般说明：**项目根本没成功启动**。

### 检查点
- 控制台有没有 `Tomcat started on port 8080`
- 地址是不是：  
    `http://localhost:8080/hello`
- 是否真的写了 `HelloController`