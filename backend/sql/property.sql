-- =============================================
-- 物业管理系统 数据库脚本
-- 包含：建表 + 初始数据
-- =============================================

-- =============================================
-- 一、RBAC权限管理（已有）
-- =============================================

DROP TABLE IF EXISTS sys_permission_info;
CREATE TABLE sys_permission_info(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `permission_code` VARCHAR(128) NOT NULL COMMENT '权限编码' ,
    `permission_name` VARCHAR(255) NOT NULL COMMENT '权限名称' ,
    `permission_type` INT NOT NULL COMMENT '权限类型：1.目录 2.菜单 3.按钮' ,
    `parent_id` BIGINT NOT NULL COMMENT '父级节点ID' ,
    `permission_icon` VARCHAR(255) COMMENT '图标' ,
    `permission_str` VARCHAR(255) COMMENT '权限字串' ,
    `permission_component` VARCHAR(255) COMMENT '组件地址' ,
    `sort` INT NOT NULL COMMENT '排序字段' ,
    PRIMARY KEY (id)
) COMMENT = '权限信息表';

DROP TABLE IF EXISTS sys_role_info;
CREATE TABLE sys_role_info(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `role_code` VARCHAR(128) NOT NULL COMMENT '角色代码' ,
    `role_name` VARCHAR(255) NOT NULL COMMENT '角色名称' ,
    PRIMARY KEY (id)
) COMMENT = '角色信息表';

DROP TABLE IF EXISTS sys_role_permission;
CREATE TABLE sys_role_permission(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `role_info_id` BIGINT NOT NULL COMMENT '角色ID' ,
    `permission_info_id` BIGINT NOT NULL COMMENT '权限ID' ,
    PRIMARY KEY (id)
) COMMENT = '角色权限关联表';
CREATE INDEX permission_info_id ON sys_role_permission(permission_info_id);
CREATE INDEX role_info_id ON sys_role_permission(role_info_id);

DROP TABLE IF EXISTS sys_user_info;
CREATE TABLE sys_user_info(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `user_name` VARCHAR(64) NOT NULL COMMENT '用户名，登录用' ,
    `password` VARCHAR(128) NOT NULL COMMENT '密码' ,
    `phone_number` VARCHAR(16) COMMENT '电话号码' ,
    `avatar_address` VARCHAR(128) COMMENT '头像地址' ,
    `full_name` VARCHAR(128) COMMENT '姓名' ,
    PRIMARY KEY (id)
) COMMENT = '用户信息表';

DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `user_info_id` BIGINT NOT NULL COMMENT '用户ID' ,
    `role_info_id` BIGINT NOT NULL COMMENT '角色ID' ,
    PRIMARY KEY (id)
) COMMENT = '用户角色关联表';
CREATE INDEX role_info_id ON sys_user_role(role_info_id);
CREATE INDEX user_info_id ON sys_user_role(user_info_id);

-- =============================================
-- 二、基础信息
-- =============================================

-- 2.1 小区表
DROP TABLE IF EXISTS community;
CREATE TABLE community(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `community_name` VARCHAR(128) NOT NULL COMMENT '小区名称' ,
    `community_address` VARCHAR(255) NOT NULL COMMENT '小区地址' ,
    `area` DECIMAL(10,2) COMMENT '小区面积(平方米)' ,
    `total_buildings` INT DEFAULT 0 COMMENT '总楼栋数' ,
    `developer` VARCHAR(128) COMMENT '开发商' ,
    `property_company` VARCHAR(128) COMMENT '物业公司' ,
    `contact_phone` VARCHAR(16) COMMENT '联系电话' ,
    `status` INT NOT NULL DEFAULT 1 COMMENT '状态：1.正常 0.停用' ,
    PRIMARY KEY (id)
) COMMENT = '小区信息表';

-- 2.2 楼栋表
DROP TABLE IF EXISTS building;
CREATE TABLE building(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `community_id` BIGINT NOT NULL COMMENT '所属小区ID' ,
    `building_name` VARCHAR(64) NOT NULL COMMENT '楼栋名称' ,
    `total_floors` INT NOT NULL DEFAULT 1 COMMENT '总层数' ,
    `units_per_floor` INT NOT NULL DEFAULT 1 COMMENT '每层户数' ,
    `status` INT NOT NULL DEFAULT 1 COMMENT '状态：1.正常 0.停用' ,
    PRIMARY KEY (id)
) COMMENT = '楼栋信息表';
CREATE INDEX idx_building_community_id ON building(community_id);

-- 2.3 房屋表
DROP TABLE IF EXISTS house;
CREATE TABLE house(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `building_id` BIGINT NOT NULL COMMENT '所属楼栋ID' ,
    `house_number` VARCHAR(32) NOT NULL COMMENT '房屋编号' ,
    `floor` INT NOT NULL COMMENT '所在楼层' ,
    `unit_number` INT COMMENT '单元号' ,
    `area` DECIMAL(10,2) COMMENT '房屋面积(平方米)' ,
    `house_type` VARCHAR(32) COMMENT '户型(如：一室一厅)' ,
    `status` INT NOT NULL DEFAULT 0 COMMENT '状态：0.空置 1.已入住 2.已出租' ,
    PRIMARY KEY (id)
) COMMENT = '房屋信息表';
CREATE INDEX idx_house_building_id ON house(building_id);

-- 2.4 业主表
DROP TABLE IF EXISTS owner;
CREATE TABLE owner(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `owner_name` VARCHAR(64) NOT NULL COMMENT '业主姓名' ,
    `phone_number` VARCHAR(16) NOT NULL COMMENT '联系电话' ,
    `id_card` VARCHAR(18) COMMENT '身份证号' ,
    `gender` INT COMMENT '性别：1.男 2.女' ,
    `house_id` BIGINT COMMENT '关联房屋ID' ,
    `relationship` VARCHAR(32) DEFAULT '业主' COMMENT '与房屋关系(业主/家属/租户)' ,
    `status` INT NOT NULL DEFAULT 1 COMMENT '状态：1.正常 0.停用' ,
    PRIMARY KEY (id)
) COMMENT = '业主信息表';
CREATE INDEX idx_owner_house_id ON owner(house_id);

-- =============================================
-- 三、物业业务
-- =============================================

-- 3.1 报修表
DROP TABLE IF EXISTS repair;
CREATE TABLE repair(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `repair_no` VARCHAR(64) NOT NULL COMMENT '报修单号' ,
    `owner_id` BIGINT NOT NULL COMMENT '报修业主ID' ,
    `house_id` BIGINT NOT NULL COMMENT '房屋ID' ,
    `repair_type` VARCHAR(64) NOT NULL COMMENT '报修类型(水电/门窗/管道/公共设施等)' ,
    `repair_content` TEXT NOT NULL COMMENT '报修内容' ,
    `repair_images` VARCHAR(1024) COMMENT '报修图片(多张逗号分隔)' ,
    `contact_phone` VARCHAR(16) COMMENT '联系电话' ,
    `expected_time` DATETIME COMMENT '期望维修时间' ,
    `status` INT NOT NULL DEFAULT 0 COMMENT '状态：0.待处理 1.已派单 2.维修中 3.已完成 4.已评价 5.已关闭' ,
    PRIMARY KEY (id)
) COMMENT = '报修信息表';
CREATE UNIQUE INDEX uk_repair_no ON repair(repair_no);
CREATE INDEX idx_repair_owner_id ON repair(owner_id);
CREATE INDEX idx_repair_house_id ON repair(house_id);

-- 3.2 派单表
DROP TABLE IF EXISTS repair_order;
CREATE TABLE repair_order(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `repair_id` BIGINT NOT NULL COMMENT '报修单ID' ,
    `worker_id` BIGINT NOT NULL COMMENT '维修人员ID(关联sys_user_info)' ,
    `assign_time` DATETIME NOT NULL COMMENT '派单时间' ,
    `accept_time` DATETIME COMMENT '接单时间' ,
    `complete_time` DATETIME COMMENT '完成时间' ,
    `result_desc` TEXT COMMENT '维修结果描述' ,
    `status` INT NOT NULL DEFAULT 0 COMMENT '状态：0.待接单 1.已接单 2.维修中 3.已完成 4.已取消' ,
    PRIMARY KEY (id)
) COMMENT = '派单信息表';
CREATE INDEX idx_repair_order_repair_id ON repair_order(repair_id);
CREATE INDEX idx_repair_order_worker_id ON repair_order(worker_id);

-- 3.3 报修评价表
DROP TABLE IF EXISTS repair_evaluate;
CREATE TABLE repair_evaluate(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `repair_id` BIGINT NOT NULL COMMENT '报修单ID' ,
    `owner_id` BIGINT NOT NULL COMMENT '评价业主ID' ,
    `score` INT NOT NULL COMMENT '评分(1-5分)' ,
    `evaluate_content` VARCHAR(512) COMMENT '评价内容' ,
    `evaluate_images` VARCHAR(1024) COMMENT '评价图片(多张逗号分隔)' ,
    `service_attitude` INT COMMENT '服务态度评分(1-5)' ,
    `repair_quality` INT COMMENT '维修质量评分(1-5)' ,
    `response_speed` INT COMMENT '响应速度评分(1-5)' ,
    PRIMARY KEY (id)
) COMMENT = '报修评价信息表';
CREATE INDEX idx_evaluate_repair_id ON repair_evaluate(repair_id);
CREATE INDEX idx_evaluate_owner_id ON repair_evaluate(owner_id);

-- 3.4 投诉建议表
DROP TABLE IF EXISTS complaint;
CREATE TABLE complaint(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `complaint_no` VARCHAR(64) NOT NULL COMMENT '投诉编号' ,
    `owner_id` BIGINT NOT NULL COMMENT '投诉业主ID' ,
    `complaint_type` INT NOT NULL COMMENT '类型：1.投诉 2.建议' ,
    `complaint_category` VARCHAR(64) NOT NULL COMMENT '分类(物业服务/环境卫生/安全隐患/设施损坏/其他)' ,
    `complaint_title` VARCHAR(128) NOT NULL COMMENT '标题' ,
    `complaint_content` TEXT NOT NULL COMMENT '详细内容' ,
    `complaint_images` VARCHAR(1024) COMMENT '图片(多张逗号分隔)' ,
    `contact_phone` VARCHAR(16) COMMENT '联系电话' ,
    `handle_user_id` BIGINT COMMENT '处理人ID(关联sys_user_info)' ,
    `handle_time` DATETIME COMMENT '处理时间' ,
    `handle_result` TEXT COMMENT '处理结果' ,
    `status` INT NOT NULL DEFAULT 0 COMMENT '状态：0.待处理 1.处理中 2.已处理 3.已关闭' ,
    `satisfaction` INT COMMENT '满意度评分(1-5)' ,
    PRIMARY KEY (id)
) COMMENT = '投诉建议信息表';
CREATE UNIQUE INDEX uk_complaint_no ON complaint(complaint_no);
CREATE INDEX idx_complaint_owner_id ON complaint(owner_id);
CREATE INDEX idx_complaint_handle_user ON complaint(handle_user_id);

-- 3.5 巡检表
DROP TABLE IF EXISTS inspection;
CREATE TABLE inspection(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `inspection_no` VARCHAR(64) NOT NULL COMMENT '巡检任务编号' ,
    `inspection_name` VARCHAR(128) NOT NULL COMMENT '巡检任务名称' ,
    `community_id` BIGINT NOT NULL COMMENT '所属小区ID' ,
    `inspection_area` VARCHAR(128) COMMENT '巡检区域' ,
    `inspector_id` BIGINT NOT NULL COMMENT '巡检人员ID(关联sys_user_info)' ,
    `plan_start_time` DATETIME NOT NULL COMMENT '计划开始时间' ,
    `plan_end_time` DATETIME NOT NULL COMMENT '计划结束时间' ,
    `actual_start_time` DATETIME COMMENT '实际开始时间' ,
    `actual_end_time` DATETIME COMMENT '实际结束时间' ,
    `inspection_items` VARCHAR(512) COMMENT '巡检项目(如：消防设施/电梯/楼道/停车场等)' ,
    `status` INT NOT NULL DEFAULT 0 COMMENT '状态：0.待执行 1.巡检中 2.已完成 3.已取消' ,
    PRIMARY KEY (id)
) COMMENT = '巡检信息表';
CREATE UNIQUE INDEX uk_inspection_no ON inspection(inspection_no);
CREATE INDEX idx_inspection_community ON inspection(community_id);
CREATE INDEX idx_inspection_inspector ON inspection(inspector_id);

-- 3.6 巡检整改表
DROP TABLE IF EXISTS inspection_record;
CREATE TABLE inspection_record(
    `id` BIGINT NOT NULL COMMENT '主键' ,
    `version` INT NOT NULL COMMENT '版本号' ,
    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
    `description` VARCHAR(255) COMMENT '描述' ,
    `remark` VARCHAR(255) COMMENT '备注' ,
    `inspection_id` BIGINT NOT NULL COMMENT '巡检任务ID' ,
    `record_no` VARCHAR(64) NOT NULL COMMENT '整改单号' ,
    `issue_type` VARCHAR(64) NOT NULL COMMENT '问题类型(设施损坏/安全隐患/环境问题/其他)' ,
    `issue_desc` TEXT NOT NULL COMMENT '问题描述' ,
    `issue_images` VARCHAR(1024) COMMENT '问题图片(多张逗号分隔)' ,
    `issue_location` VARCHAR(128) COMMENT '问题位置' ,
    `severity` INT NOT NULL DEFAULT 1 COMMENT '严重程度：1.轻微 2.一般 3.严重 4.紧急' ,
    `handle_user_id` BIGINT COMMENT '整改负责人ID(关联sys_user_info)' ,
    `handle_desc` TEXT COMMENT '整改说明' ,
    `handle_images` VARCHAR(1024) COMMENT '整改后图片(多张逗号分隔)' ,
    `plan_deadline` DATETIME COMMENT '计划整改期限' ,
    `actual_complete_time` DATETIME COMMENT '实际完成时间' ,
    `verify_user_id` BIGINT COMMENT '验收人ID' ,
    `verify_time` DATETIME COMMENT '验收时间' ,
    `verify_result` INT COMMENT '验收结果：1.合格 2.不合格' ,
    `status` INT NOT NULL DEFAULT 0 COMMENT '状态：0.待整改 1.整改中 2.待验收 3.已完成 4.已驳回' ,
    PRIMARY KEY (id)
) COMMENT = '巡检整改信息表';
CREATE UNIQUE INDEX uk_record_no ON inspection_record(record_no);
CREATE INDEX idx_record_inspection_id ON inspection_record(inspection_id);
CREATE INDEX idx_record_handle_user ON inspection_record(handle_user_id);

-- =============================================
-- 四、初始数据
-- =============================================

-- 4.1 角色数据
INSERT INTO sys_role_info (id, version, create_time, update_time, create_user, update_user, description, remark, role_code, role_name) VALUES
(1, 1, NOW(), NOW(), 0, 0, '系统管理员', NULL, 'ROLE_ADMIN', '管理员'),
(2, 1, NOW(), NOW(), 0, 0, '物业工作人员', NULL, 'ROLE_STAFF', '物业员工'),
(3, 1, NOW(), NOW(), 0, 0, '维修人员', NULL, 'ROLE_WORKER', '维修人员'),
(4, 1, NOW(), NOW(), 0, 0, '巡检人员', NULL, 'ROLE_INSPECTOR', '巡检人员'),
(5, 1, NOW(), NOW(), 0, 0, '业主用户', NULL, 'ROLE_OWNER', '业主');

-- 4.2 用户数据 (密码为 123456 的 MD5 值)
INSERT INTO sys_user_info (id, version, create_time, update_time, create_user, update_user, description, remark, user_name, password, phone_number, avatar_address, full_name) VALUES
(1, 1, NOW(), NOW(), 0, 0, '系统管理员', NULL, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13800000001', NULL, '系统管理员'),
(2, 1, NOW(), NOW(), 0, 0, '物业经理', NULL, 'manager', 'e10adc3949ba59abbe56e057f20f883e', '13800000002', NULL, '张经理'),
(3, 1, NOW(), NOW(), 0, 0, '物业前台', NULL, 'receptionist', 'e10adc3949ba59abbe56e057f20f883e', '13800000003', NULL, '李前台'),
(4, 1, NOW(), NOW(), 0, 0, '维修师傅-王', NULL, 'worker_wang', 'e10adc3949ba59abbe56e057f20f883e', '13800000004', NULL, '王师傅'),
(5, 1, NOW(), NOW(), 0, 0, '维修师傅-赵', NULL, 'worker_zhao', 'e10adc3949ba59abbe56e057f20f883e', '13800000005', NULL, '赵师傅'),
(6, 1, NOW(), NOW(), 0, 0, '巡检员-刘', NULL, 'inspector_liu', 'e10adc3949ba59abbe56e057f20f883e', '13800000006', NULL, '刘巡检'),
(7, 1, NOW(), NOW(), 0, 0, '业主-陈', NULL, 'owner_chen', 'e10adc3949ba59abbe56e057f20f883e', '13900000001', NULL, '陈先生'),
(8, 1, NOW(), NOW(), 0, 0, '业主-周', NULL, 'owner_zhou', 'e10adc3949ba59abbe56e057f20f883e', '13900000002', NULL, '周女士'),
(9, 1, NOW(), NOW(), 0, 0, '业主-吴', NULL, 'owner_wu', 'e10adc3949ba59abbe56e057f20f883e', '13900000003', NULL, '吴先生'),
(10, 1, NOW(), NOW(), 0, 0, '业主-孙', NULL, 'owner_sun', 'e10adc3949ba59abbe56e057f20f883e', '13900000004', NULL, '孙女士');

-- 4.3 用户角色关联
INSERT INTO sys_user_role (id, version, create_time, update_time, create_user, update_user, description, remark, user_info_id, role_info_id) VALUES
(1, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 1),
(2, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 2),
(3, 1, NOW(), NOW(), 0, 0, NULL, NULL, 3, 2),
(4, 1, NOW(), NOW(), 0, 0, NULL, NULL, 4, 3),
(5, 1, NOW(), NOW(), 0, 0, NULL, NULL, 5, 3),
(6, 1, NOW(), NOW(), 0, 0, NULL, NULL, 6, 4),
(7, 1, NOW(), NOW(), 0, 0, NULL, NULL, 7, 5),
(8, 1, NOW(), NOW(), 0, 0, NULL, NULL, 8, 5),
(9, 1, NOW(), NOW(), 0, 0, NULL, NULL, 9, 5),
(10, 1, NOW(), NOW(), 0, 0, NULL, NULL, 10, 5);

-- 4.4 权限数据 - 目录
INSERT INTO sys_permission_info (id, version, create_time, update_time, create_user, update_user, description, remark, permission_code, permission_name, permission_type, parent_id, permission_icon, permission_str, permission_component, sort) VALUES
-- 一级目录
(1, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'dashboard', '工作台', 1, 0, 'HomeFilled', NULL, NULL, 1),
(2, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'community_mgmt', '基础信息', 1, 0, 'OfficeBuilding', NULL, NULL, 2),
(3, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'business_mgmt', '物业业务', 1, 0, 'SetUp', NULL, NULL, 3),
(4, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'system_mgmt', '系统管理', 1, 0, 'Setting', NULL, NULL, 4),
-- 基础信息-菜单
(10, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'community_list', '小区管理', 2, 2, NULL, 'community:list', 'community', 1),
(11, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'building_list', '楼栋管理', 2, 2, NULL, 'building:list', 'community', 2),
(12, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'house_list', '房屋管理', 2, 2, NULL, 'house:list', 'community', 3),
(13, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'owner_list', '业主管理', 2, 2, NULL, 'owner:list', 'community', 4),
-- 物业业务-菜单
(20, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'repair_list', '报修管理', 2, 3, NULL, 'repair:list', 'repair', 1),
(21, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'repair_order_list', '派单管理', 2, 3, NULL, 'repair_order:list', 'repair', 2),
(22, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'complaint_list', '投诉建议', 2, 3, NULL, 'complaint:list', 'complaint', 3),
(23, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'inspection_list', '巡检管理', 2, 3, NULL, 'inspection:list', 'inspection', 4),
(24, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'inspection_record_list', '巡检整改', 2, 3, NULL, 'inspection_record:list', 'inspection', 5),
-- 系统管理-菜单
(30, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'user_list', '用户管理', 2, 4, NULL, 'user:list', 'system', 1),
(31, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'role_list', '角色管理', 2, 4, NULL, 'role:list', 'system', 2),
(32, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'permission_list', '权限管理', 2, 4, NULL, 'permission:list', 'system', 3),
-- 按钮权限-小区管理
(100, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'community:add', '新增小区', 3, 10, NULL, 'community:add', NULL, 1),
(101, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'community:edit', '编辑小区', 3, 10, NULL, 'community:edit', NULL, 2),
(102, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'community:delete', '删除小区', 3, 10, NULL, 'community:delete', NULL, 3),
-- 按钮权限-楼栋管理
(103, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'building:add', '新增楼栋', 3, 11, NULL, 'building:add', NULL, 1),
(104, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'building:edit', '编辑楼栋', 3, 11, NULL, 'building:edit', NULL, 2),
(105, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'building:delete', '删除楼栋', 3, 11, NULL, 'building:delete', NULL, 3),
-- 按钮权限-房屋管理
(106, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'house:add', '新增房屋', 3, 12, NULL, 'house:add', NULL, 1),
(107, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'house:edit', '编辑房屋', 3, 12, NULL, 'house:edit', NULL, 2),
(108, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'house:delete', '删除房屋', 3, 12, NULL, 'house:delete', NULL, 3),
-- 按钮权限-业主管理
(109, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'owner:add', '新增业主', 3, 13, NULL, 'owner:add', NULL, 1),
(110, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'owner:edit', '编辑业主', 3, 13, NULL, 'owner:edit', NULL, 2),
(111, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'owner:delete', '删除业主', 3, 13, NULL, 'owner:delete', NULL, 3),
-- 按钮权限-报修管理
(120, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'repair:add', '新增报修', 3, 20, NULL, 'repair:add', NULL, 1),
(121, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'repair:edit', '编辑报修', 3, 20, NULL, 'repair:edit', NULL, 2),
(122, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'repair:delete', '删除报修', 3, 20, NULL, 'repair:delete', NULL, 3),
(123, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'repair:assign', '派单', 3, 20, NULL, 'repair:assign', NULL, 4),
-- 按钮权限-投诉建议
(130, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'complaint:add', '新增投诉', 3, 22, NULL, 'complaint:add', NULL, 1),
(131, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'complaint:handle', '处理投诉', 3, 22, NULL, 'complaint:handle', NULL, 2),
-- 按钮权限-巡检管理
(140, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'inspection:add', '新增巡检', 3, 23, NULL, 'inspection:add', NULL, 1),
(141, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'inspection:edit', '编辑巡检', 3, 23, NULL, 'inspection:edit', NULL, 2),
-- 按钮权限-系统管理
(150, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'user:add', '新增用户', 3, 30, NULL, 'user:add', NULL, 1),
(151, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'user:edit', '编辑用户', 3, 30, NULL, 'user:edit', NULL, 2),
(152, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'user:delete', '删除用户', 3, 30, NULL, 'user:delete', NULL, 3),
(153, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'role:add', '新增角色', 3, 31, NULL, 'role:add', NULL, 1),
(154, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'role:edit', '编辑角色', 3, 31, NULL, 'role:edit', NULL, 2),
(155, 1, NOW(), NOW(), 0, 0, NULL, NULL, 'role:delete', '删除角色', 3, 31, NULL, 'role:delete', NULL, 3);

-- 4.5 角色权限关联 - 管理员拥有全部权限
INSERT INTO sys_role_permission (id, version, create_time, update_time, create_user, update_user, description, remark, role_info_id, permission_info_id) VALUES
(1, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 1),
(2, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 2),
(3, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 3),
(4, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 4),
(5, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 10),
(6, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 11),
(7, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 12),
(8, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 13),
(9, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 20),
(10, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 21),
(11, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 22),
(12, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 23),
(13, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 24),
(14, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 30),
(15, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 31),
(16, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 32),
(17, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 100),
(18, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 101),
(19, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 102),
(20, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 103),
(21, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 104),
(22, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 105),
(23, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 106),
(24, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 107),
(25, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 108),
(26, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 109),
(27, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 110),
(28, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 111),
(29, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 120),
(30, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 121),
(31, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 122),
(32, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 123),
(33, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 130),
(34, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 131),
(35, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 140),
(36, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 141),
(37, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 150),
(38, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 151),
(39, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 152),
(40, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 153),
(41, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 154),
(42, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, 155);

-- 物业员工权限 - 基础信息+物业业务（不含系统管理）
INSERT INTO sys_role_permission (id, version, create_time, update_time, create_user, update_user, description, remark, role_info_id, permission_info_id) VALUES
(50, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 1),
(51, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 2),
(52, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 3),
(53, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 10),
(54, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 11),
(55, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 12),
(56, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 13),
(57, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 20),
(58, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 21),
(59, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 22),
(60, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 23),
(61, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 24),
(62, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 100),
(63, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 101),
(64, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 120),
(65, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 123),
(66, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 130),
(67, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 131),
(68, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, 140);

-- 维修人员权限 - 工作台+报修相关
INSERT INTO sys_role_permission (id, version, create_time, update_time, create_user, update_user, description, remark, role_info_id, permission_info_id) VALUES
(70, 1, NOW(), NOW(), 0, 0, NULL, NULL, 3, 1),
(71, 1, NOW(), NOW(), 0, 0, NULL, NULL, 3, 3),
(72, 1, NOW(), NOW(), 0, 0, NULL, NULL, 3, 20),
(73, 1, NOW(), NOW(), 0, 0, NULL, NULL, 3, 21),
(74, 1, NOW(), NOW(), 0, 0, NULL, NULL, 3, 121);

-- 业主权限 - 工作台+报修+投诉
INSERT INTO sys_role_permission (id, version, create_time, update_time, create_user, update_user, description, remark, role_info_id, permission_info_id) VALUES
(80, 1, NOW(), NOW(), 0, 0, NULL, NULL, 5, 1),
(81, 1, NOW(), NOW(), 0, 0, NULL, NULL, 5, 3),
(82, 1, NOW(), NOW(), 0, 0, NULL, NULL, 5, 20),
(83, 1, NOW(), NOW(), 0, 0, NULL, NULL, 5, 22),
(84, 1, NOW(), NOW(), 0, 0, NULL, NULL, 5, 120),
(85, 1, NOW(), NOW(), 0, 0, NULL, NULL, 5, 130);

-- 4.6 小区数据
INSERT INTO community (id, version, create_time, update_time, create_user, update_user, description, remark, community_name, community_address, area, total_buildings, developer, property_company, contact_phone, status) VALUES
(1, 1, NOW(), NOW(), 0, 0, '示范小区', NULL, '阳光花园小区', '北京市朝阳区建国路100号', 50000.00, 5, '阳光地产', '阳光物业管理有限公司', '010-88886666', 1),
(2, 1, NOW(), NOW(), 0, 0, '示范小区', NULL, '翠湖天地小区', '上海市黄浦区翠湖路88号', 80000.00, 8, '翠湖地产', '翠湖物业管理有限公司', '021-66668888', 1),
(3, 1, NOW(), NOW(), 0, 0, '示范小区', NULL, '碧水云天小区', '广州市天河区天河路200号', 65000.00, 6, '碧云地产', '碧云物业管理有限公司', '020-33334444', 1);

-- 4.7 楼栋数据 (阳光花园小区)
INSERT INTO building (id, version, create_time, update_time, create_user, update_user, description, remark, community_id, building_name, total_floors, units_per_floor, status) VALUES
(1, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, '1号楼', 18, 4, 1),
(2, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, '2号楼', 18, 4, 1),
(3, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, '3号楼', 24, 6, 1),
(4, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, '4号楼', 24, 6, 1),
(5, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, '5号楼', 12, 4, 1);

-- 4.8 房屋数据 (1号楼部分房屋)
INSERT INTO house (id, version, create_time, update_time, create_user, update_user, description, remark, building_id, house_number, floor, unit_number, area, house_type, status) VALUES
(1, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, '1-1-101', 1, 1, 89.50, '两室一厅', 1),
(2, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, '1-1-102', 1, 1, 105.00, '三室一厅', 1),
(3, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, '1-1-201', 2, 1, 89.50, '两室一厅', 1),
(4, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, '1-1-202', 2, 1, 105.00, '三室一厅', 0),
(5, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, '1-2-101', 1, 2, 75.00, '两室一厅', 1),
(6, 1, NOW(), NOW(), 0, 0, NULL, NULL, 1, '1-2-102', 1, 2, 120.00, '三室两厅', 2),
(7, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, '2-1-101', 1, 1, 95.00, '三室一厅', 1),
(8, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, '2-1-102', 1, 1, 88.00, '两室一厅', 0),
(9, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, '2-1-201', 2, 1, 95.00, '三室一厅', 1),
(10, 1, NOW(), NOW(), 0, 0, NULL, NULL, 2, '2-1-202', 2, 1, 88.00, '两室一厅', 0);

-- 4.9 业主数据
INSERT INTO owner (id, version, create_time, update_time, create_user, update_user, description, remark, owner_name, phone_number, id_card, gender, house_id, relationship, status) VALUES
(1, 1, NOW(), NOW(), 0, 0, NULL, NULL, '陈先生', '13900000001', '110101199001011234', 1, 1, '业主', 1),
(2, 1, NOW(), NOW(), 0, 0, NULL, NULL, '陈太太', '13900000011', '110101199205052345', 2, 1, '家属', 1),
(3, 1, NOW(), NOW(), 0, 0, NULL, NULL, '周女士', '13900000002', '310101198803031234', 2, 3, '业主', 1),
(4, 1, NOW(), NOW(), 0, 0, NULL, NULL, '吴先生', '13900000003', '440106199105051234', 1, 5, '业主', 1),
(5, 1, NOW(), NOW(), 0, 0, NULL, NULL, '孙女士', '13900000004', '440106199308082345', 2, 7, '业主', 1),
(6, 1, NOW(), NOW(), 0, 0, NULL, NULL, '赵先生', '13900000005', '110101198506061234', 1, 6, '租户', 1);

-- 4.10 报修数据
INSERT INTO repair (id, version, create_time, update_time, create_user, update_user, description, remark, repair_no, owner_id, house_id, repair_type, repair_content, repair_images, contact_phone, expected_time, status) VALUES
(1, 1, NOW(), NOW(), 7, 0, NULL, NULL, 'WX20260701001', 1, 1, '水电', '厨房水龙头漏水严重，需要更换', NULL, '13900000001', '2026-07-02 10:00:00', 3),
(2, 1, NOW(), NOW(), 8, 0, NULL, NULL, 'WX20260702001', 3, 3, '门窗', '卧室窗户无法正常关闭，密封条脱落', NULL, '13900000002', '2026-07-03 14:00:00', 1),
(3, 1, NOW(), NOW(), 9, 0, NULL, NULL, 'WX20260703001', 4, 5, '管道', '卫生间下水道堵塞，污水反流', NULL, '13900000003', '2026-07-04 09:00:00', 0),
(4, 1, NOW(), NOW(), 10, 0, NULL, NULL, 'WX20260703002', 5, 7, '公共设施', '楼道灯不亮，已持续一周', NULL, '13900000004', '2026-07-05 00:00:00', 2);

-- 4.11 派单数据
INSERT INTO repair_order (id, version, create_time, update_time, create_user, update_user, description, remark, repair_id, worker_id, assign_time, accept_time, complete_time, result_desc, status) VALUES
(1, 1, NOW(), NOW(), 2, 0, NULL, NULL, 1, 4, '2026-07-01 10:00:00', '2026-07-01 10:30:00', '2026-07-01 11:30:00', '已更换新的水龙头，测试正常', 3),
(2, 1, NOW(), NOW(), 2, 0, NULL, NULL, 2, 5, '2026-07-02 09:00:00', '2026-07-02 09:20:00', NULL, NULL, 1),
(3, 1, NOW(), NOW(), 2, 0, NULL, NULL, 4, 4, '2026-07-03 15:00:00', '2026-07-03 15:30:00', NULL, NULL, 2);

-- 4.12 报修评价数据
INSERT INTO repair_evaluate (id, version, create_time, update_time, create_user, update_user, description, remark, repair_id, owner_id, score, evaluate_content, evaluate_images, service_attitude, repair_quality, response_speed) VALUES
(1, 1, NOW(), NOW(), 7, 0, NULL, NULL, 1, 1, 5, '维修师傅很专业，很快就修好了，服务态度也很好！', NULL, 5, 5, 5);

-- 4.13 投诉建议数据
INSERT INTO complaint (id, version, create_time, update_time, create_user, update_user, description, remark, complaint_no, owner_id, complaint_type, complaint_category, complaint_title, complaint_content, complaint_images, contact_phone, handle_user_id, handle_time, handle_result, status, satisfaction) VALUES
(1, 1, NOW(), NOW(), 7, 0, NULL, NULL, 'TS20260701001', 1, 1, '环境卫生', '小区垃圾清理不及时', '1号楼楼下垃圾桶已满，夏天味道很大，希望能增加清理频次', NULL, '13900000001', 2, '2026-07-01 16:00:00', '已联系保洁部门，增加每日清理次数为2次，感谢您的反馈', 2, 4),
(2, 1, NOW(), NOW(), 8, 0, NULL, NULL, 'TS20260702001', 3, 2, '设施损坏', '建议增加健身器材', '小区健身区域器材较少，建议增加一些适合老年人的健身器材', NULL, '13900000002', NULL, NULL, NULL, 0, NULL),
(3, 1, NOW(), NOW(), 9, 0, NULL, NULL, 'JY20260703001', 4, 2, '物业服务', '建议优化停车位管理', '小区停车位紧张，建议引入智能停车管理系统，提高车位利用率', NULL, '13900000003', NULL, NULL, NULL, 0, NULL);

-- 4.14 巡检数据
INSERT INTO inspection (id, version, create_time, update_time, create_user, update_user, description, remark, inspection_no, inspection_name, community_id, inspection_area, inspector_id, plan_start_time, plan_end_time, actual_start_time, actual_end_time, inspection_items, status) VALUES
(1, 1, NOW(), NOW(), 2, 0, NULL, NULL, 'XJ20260701001', '阳光花园7月日常巡检', 1, '全小区', 6, '2026-07-01 08:00:00', '2026-07-01 18:00:00', '2026-07-01 08:30:00', '2026-07-01 17:00:00', '消防设施,电梯,楼道,停车场,绿化带', 2),
(2, 1, NOW(), NOW(), 2, 0, NULL, NULL, 'XJ20260704001', '阳光花园7月消防安全专项检查', 1, '1-3号楼', 6, '2026-07-04 09:00:00', '2026-07-04 17:00:00', NULL, NULL, '消防栓,灭火器,消防通道,应急照明', 0);

-- 4.15 巡检整改数据
INSERT INTO inspection_record (id, version, create_time, update_time, create_user, update_user, description, remark, inspection_id, record_no, issue_type, issue_desc, issue_images, issue_location, severity, handle_user_id, handle_desc, handle_images, plan_deadline, actual_complete_time, verify_user_id, verify_time, verify_result, status) VALUES
(1, 1, NOW(), NOW(), 6, 0, NULL, NULL, 1, 'ZG20260701001', '安全隐患', '1号楼2单元消防通道堆放杂物，影响通行', NULL, '1号楼2单元消防通道', 3, 4, '已清理消防通道杂物，恢复通道畅通', NULL, '2026-07-03 00:00:00', '2026-07-02 10:00:00', 2, '2026-07-02 14:00:00', 1, 3),
(2, 1, NOW(), NOW(), 6, 0, NULL, NULL, 1, 'ZG20260701002', '设施损坏', '2号楼电梯按钮面板破损，3楼按钮失灵', NULL, '2号楼电梯内', 2, 5, NULL, NULL, '2026-07-05 00:00:00', NULL, NULL, NULL, NULL, 0),
(3, 1, NOW(), NOW(), 6, 0, NULL, NULL, 1, 'ZG20260701003', '环境问题', '3号楼前方绿化带草坪大面积枯死', NULL, '3号楼前方绿化带', 1, NULL, NULL, NULL, '2026-07-10 00:00:00', NULL, NULL, NULL, NULL, 0);
