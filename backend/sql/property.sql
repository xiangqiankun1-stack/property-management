-- =============================================
-- 一、系统权限
-- =============================================

DROP TABLE IF EXISTS sys_permission_info;
CREATE TABLE sys_permission_info(
                                    `id` BIGINT NOT NULL COMMENT '主键' ,
                                    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
                                    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
                                    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
                                    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
                                    `description` VARCHAR(255) COMMENT '描述' ,
                                    `remark` VARCHAR(255) COMMENT '备注' ,
                                    `permission_code` VARCHAR(128) NOT NULL COMMENT '权限编码' ,
                                    `permission_name` VARCHAR(255) NOT NULL COMMENT '权限名称' ,
                                    `permission_type` INT NOT NULL COMMENT '权限类型：1.目录 2.菜单 3.按钮' ,
                                    `parent_id` BIGINT COMMENT '父级节点ID(顶级节点为NULL)' ,
                                    `permission_icon` VARCHAR(255) COMMENT '图标' ,
                                    `permission_str` VARCHAR(255) COMMENT '权限字串' ,
                                    `permission_component` VARCHAR(255) COMMENT '组件地址' ,
                                    `sort` INT NOT NULL COMMENT '排序字段' ,
                                    PRIMARY KEY (id),
                                    CONSTRAINT fk_permission_parent FOREIGN KEY (parent_id) REFERENCES sys_permission_info(id)
) COMMENT = '权限信息表';

DROP TABLE IF EXISTS sys_role_info;
CREATE TABLE sys_role_info(
                              `id` BIGINT NOT NULL COMMENT '主键' ,
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
                                    `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
                                    `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
                                    `create_user` BIGINT NOT NULL COMMENT '创建人' ,
                                    `update_user` BIGINT NOT NULL COMMENT '更新人' ,
                                    `description` VARCHAR(255) COMMENT '描述' ,
                                    `remark` VARCHAR(255) COMMENT '备注' ,
                                    `role_info_id` BIGINT NOT NULL COMMENT '角色ID' ,
                                    `permission_info_id` BIGINT NOT NULL COMMENT '权限ID' ,
                                    PRIMARY KEY (id),
                                    CONSTRAINT fk_role_permission_role FOREIGN KEY (role_info_id) REFERENCES sys_role_info(id),
                                    CONSTRAINT fk_role_permission_permission FOREIGN KEY (permission_info_id) REFERENCES sys_permission_info(id)
) COMMENT = '角色权限关联表';
CREATE INDEX permission_info_id ON sys_role_permission(permission_info_id);
CREATE INDEX role_info_id ON sys_role_permission(role_info_id);

DROP TABLE IF EXISTS sys_user_info;
CREATE TABLE sys_user_info(
                              `id` BIGINT NOT NULL COMMENT '主键' ,
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
                              `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
                              `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
                              `create_user` BIGINT NOT NULL COMMENT '创建人' ,
                              `update_user` BIGINT NOT NULL COMMENT '更新人' ,
                              `description` VARCHAR(255) COMMENT '描述' ,
                              `remark` VARCHAR(255) COMMENT '备注' ,
                              `user_info_id` BIGINT NOT NULL COMMENT '用户ID' ,
                              `role_info_id` BIGINT NOT NULL COMMENT '角色ID' ,
                              PRIMARY KEY (id),
                              CONSTRAINT fk_user_role_user FOREIGN KEY (user_info_id) REFERENCES sys_user_info(id),
                              CONSTRAINT fk_user_role_role FOREIGN KEY (role_info_id) REFERENCES sys_role_info(id)
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
                         PRIMARY KEY (id),
                         CONSTRAINT fk_building_community FOREIGN KEY (community_id) REFERENCES community(id)
) COMMENT = '楼栋信息表';
CREATE INDEX idx_building_community_id ON building(community_id);

-- 2.3 房屋表
DROP TABLE IF EXISTS house;
CREATE TABLE house(
                      `id` BIGINT NOT NULL COMMENT '主键' ,
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
                      PRIMARY KEY (id),
                      CONSTRAINT fk_house_building FOREIGN KEY (building_id) REFERENCES building(id)
) COMMENT = '房屋信息表';
CREATE INDEX idx_house_building_id ON house(building_id);

-- 2.4 业主表
DROP TABLE IF EXISTS owner;
CREATE TABLE owner(
                      `id` BIGINT NOT NULL COMMENT '主键' ,
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
                      PRIMARY KEY (id),
                      CONSTRAINT fk_owner_house FOREIGN KEY (house_id) REFERENCES house(id)
) COMMENT = '业主信息表';
CREATE INDEX idx_owner_house_id ON owner(house_id);

-- =============================================
-- 三、物业业务
-- =============================================

-- 3.1 报修表
DROP TABLE IF EXISTS repair;
CREATE TABLE repair(
                       `id` BIGINT NOT NULL COMMENT '主键' ,
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
                       PRIMARY KEY (id),
                       CONSTRAINT fk_repair_owner FOREIGN KEY (owner_id) REFERENCES owner(id),
                       CONSTRAINT fk_repair_house FOREIGN KEY (house_id) REFERENCES house(id)
) COMMENT = '报修信息表';
CREATE UNIQUE INDEX uk_repair_no ON repair(repair_no);
CREATE INDEX idx_repair_owner_id ON repair(owner_id);
CREATE INDEX idx_repair_house_id ON repair(house_id);

-- 3.2 派单表
DROP TABLE IF EXISTS repair_order;
CREATE TABLE repair_order(
                             `id` BIGINT NOT NULL COMMENT '主键' ,
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
                             PRIMARY KEY (id),
                             CONSTRAINT fk_repair_order_repair FOREIGN KEY (repair_id) REFERENCES repair(id),
                             CONSTRAINT fk_repair_order_worker FOREIGN KEY (worker_id) REFERENCES sys_user_info(id)
) COMMENT = '派单信息表';
CREATE INDEX idx_repair_order_repair_id ON repair_order(repair_id);
CREATE INDEX idx_repair_order_worker_id ON repair_order(worker_id);

-- 3.3 报修评价表
DROP TABLE IF EXISTS repair_evaluate;
CREATE TABLE repair_evaluate(
                                `id` BIGINT NOT NULL COMMENT '主键' ,
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
                                PRIMARY KEY (id),
                                CONSTRAINT fk_evaluate_repair FOREIGN KEY (repair_id) REFERENCES repair(id),
                                CONSTRAINT fk_evaluate_owner FOREIGN KEY (owner_id) REFERENCES owner(id)
) COMMENT = '报修评价信息表';
CREATE INDEX idx_evaluate_repair_id ON repair_evaluate(repair_id);
CREATE INDEX idx_evaluate_owner_id ON repair_evaluate(owner_id);

-- 3.4 投诉建议表
DROP TABLE IF EXISTS complaint;
CREATE TABLE complaint(
                          `id` BIGINT NOT NULL COMMENT '主键' ,
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
                          PRIMARY KEY (id),
                          CONSTRAINT fk_complaint_owner FOREIGN KEY (owner_id) REFERENCES owner(id),
                          CONSTRAINT fk_complaint_handle_user FOREIGN KEY (handle_user_id) REFERENCES sys_user_info(id)
) COMMENT = '投诉建议信息表';
CREATE UNIQUE INDEX uk_complaint_no ON complaint(complaint_no);
CREATE INDEX idx_complaint_owner_id ON complaint(owner_id);
CREATE INDEX idx_complaint_handle_user ON complaint(handle_user_id);

-- 3.5 巡检表
DROP TABLE IF EXISTS inspection;
CREATE TABLE inspection(
                           `id` BIGINT NOT NULL COMMENT '主键' ,
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
                           PRIMARY KEY (id),
                           CONSTRAINT fk_inspection_community FOREIGN KEY (community_id) REFERENCES community(id),
                           CONSTRAINT fk_inspection_inspector FOREIGN KEY (inspector_id) REFERENCES sys_user_info(id)
) COMMENT = '巡检信息表';
CREATE UNIQUE INDEX uk_inspection_no ON inspection(inspection_no);
CREATE INDEX idx_inspection_community ON inspection(community_id);
CREATE INDEX idx_inspection_inspector ON inspection(inspector_id);

-- 3.6 巡检整改表
DROP TABLE IF EXISTS inspection_record;
CREATE TABLE inspection_record(
                                  `id` BIGINT NOT NULL COMMENT '主键' ,
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
                                  PRIMARY KEY (id),
                                  CONSTRAINT fk_record_inspection FOREIGN KEY (inspection_id) REFERENCES inspection(id),
                                  CONSTRAINT fk_record_handle_user FOREIGN KEY (handle_user_id) REFERENCES sys_user_info(id),
                                  CONSTRAINT fk_record_verify_user FOREIGN KEY (verify_user_id) REFERENCES sys_user_info(id)
) COMMENT = '巡检整改信息表';
CREATE UNIQUE INDEX uk_record_no ON inspection_record(record_no);
CREATE INDEX idx_record_inspection_id ON inspection_record(inspection_id);
CREATE INDEX idx_record_handle_user ON inspection_record(handle_user_id);

-- =============================================
-- 初始化数据（与 schema_with_fk.sql 配套使用）
-- 说明：为便于演示，所有 create_user / update_user 均设为 1（管理员）
-- =============================================

-- =============================================
-- 一、系统权限
-- =============================================

-- 1.1 权限数据（parent_id = 0 表示根节点）
INSERT INTO sys_permission_info
(id, create_time, update_time, create_user, update_user, description, remark, permission_code, permission_name, permission_type, parent_id, permission_icon, permission_str, permission_component, sort)
VALUES
    (1, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '系统管理目录', NULL, 'sys', '系统管理', 1, NULL, 'setting', NULL, NULL, 1),
    (2, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '用户管理菜单', NULL, 'sys:user', '用户管理', 2, 1, 'user', 'sys:user:list', '/sys/user/index', 1),
    (3, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '角色管理菜单', NULL, 'sys:role', '角色管理', 2, 1, 'team', 'sys:role:list', '/sys/role/index', 2),
    (4, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '新增用户按钮', NULL, 'sys:user:add', '新增用户', 3, 2, NULL, 'sys:user:add', NULL, 1),
    (5, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '物业业务目录', NULL, 'property', '物业业务', 1, NULL, 'home', NULL, NULL, 2),
    (6, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '报修管理菜单', NULL, 'property:repair', '报修管理', 2, 5, 'tool', 'property:repair:list', '/property/repair/index', 1);

-- 1.2 角色数据
INSERT INTO sys_role_info
(id, create_time, update_time, create_user, update_user, description, remark, role_code, role_name)
VALUES
    (1, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '系统管理员，拥有全部权限', NULL, 'ADMIN', '管理员'),
    (2, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '负责报修派单处理', NULL, 'WORKER', '维修人员'),
    (3, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '负责投诉建议处理', NULL, 'SERVICE', '客服人员');

-- 1.3 角色权限关联
INSERT INTO sys_role_permission
(id, create_time, update_time, create_user, update_user, description, remark, role_info_id, permission_info_id)
VALUES
    (1, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 1, 1),
    (2, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 1, 2),
    (3, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 1, 3),
    (4, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 1, 4),
    (5, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 1, 5),
    (6, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 1, 6),
    (7, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 2, 6),
    (8, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 3, 6);

-- 1.4 用户数据
INSERT INTO sys_user_info
(id, create_time, update_time, create_user, update_user, description, remark, user_name, password, phone_number, avatar_address, full_name)
VALUES
    (1, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '系统内置管理员', NULL, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13800000001', NULL, '系统管理员'),
    (2, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '水电维修师傅', NULL, 'worker01', 'e10adc3949ba59abbe56e057f20f883e', '13800000002', NULL, '张三'),
    (3, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '综合维修师傅', NULL, 'worker02', 'e10adc3949ba59abbe56e057f20f883e', '13800000003', NULL, '李四'),
    (4, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '物业客服', NULL, 'service01', 'e10adc3949ba59abbe56e057f20f883e', '13800000004', NULL, '王五');

-- 1.5 用户角色关联
INSERT INTO sys_user_role
(id, create_time, update_time, create_user, update_user, description, remark, user_info_id, role_info_id)
VALUES
    (1, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 1, 1),
    (2, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 2, 2),
    (3, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 3, 2),
    (4, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 4, 3);

-- =============================================
-- 二、基础信息
-- =============================================

-- 2.1 小区数据
INSERT INTO community
(id, create_time, update_time, create_user, update_user, description, remark, community_name, community_address, area, total_buildings, developer, property_company, contact_phone, status)
VALUES
    (1, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, '阳光花园', '市中心区人民路100号', 85000.00, 2, '阳光地产', '和谐物业管理有限公司', '021-88886666', 1);

-- 2.2 楼栋数据
INSERT INTO building
(id, create_time, update_time, create_user, update_user, description, remark, community_id, building_name, total_floors, units_per_floor, status)
VALUES
    (1, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 1, '1号楼', 18, 4, 1),
    (2, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 1, '2号楼', 18, 4, 1);

-- 2.3 房屋数据
INSERT INTO house
(id, create_time, update_time, create_user, update_user, description, remark, building_id, house_number, floor, unit_number, area, house_type, status)
VALUES
    (1, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 1, '1-1-101', 1, 1, 89.50, '两室一厅', 1),
    (2, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 1, '1-1-102', 1, 1, 120.00, '三室两厅', 1),
    (3, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 2, '2-1-101', 1, 1, 95.00, '两室两厅', 2),
    (4, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, NULL, NULL, 2, '2-1-102', 1, 1, 65.00, '一室一厅', 0);

-- 2.4 业主数据
INSERT INTO owner
(id, create_time, update_time, create_user, update_user, description, remark, owner_name, phone_number, id_card, gender, house_id, relationship, status)
VALUES
    (1, '2026-01-02 10:00:00', '2026-01-02 10:00:00', 1, 1, NULL, NULL, '刘建国', '13900000001', '310101198001011234', 1, 1, '业主', 1),
    (2, '2026-01-02 10:10:00', '2026-01-02 10:10:00', 1, 1, NULL, NULL, '陈美丽', '13900000002', '310101198505052345', 2, 2, '业主', 1),
    (3, '2026-01-02 10:20:00', '2026-01-02 10:20:00', 1, 1, NULL, NULL, '赵小龙', '13900000003', '310101199212123456', 1, 3, '租户', 1);

-- =============================================
-- 三、物业业务
-- =============================================

-- 3.1 报修数据
INSERT INTO repair
(id, create_time, update_time, create_user, update_user, description, remark, repair_no, owner_id, house_id, repair_type, repair_content, repair_images, contact_phone, expected_time, status)
VALUES
    (1, '2026-02-01 08:30:00', '2026-02-01 08:30:00', 1, 1, NULL, NULL, 'RP20260201001', 1, 1, '水电', '厨房水龙头漏水，请尽快维修', NULL, '13900000001', '2026-02-02 09:00:00', 3),
    (2, '2026-02-03 14:00:00', '2026-02-03 14:00:00', 1, 1, NULL, NULL, 'RP20260203001', 2, 2, '门窗', '卧室窗户密封条老化，通风时有异响', NULL, '13900000002', '2026-02-05 10:00:00', 1);

-- 3.2 派单数据
INSERT INTO repair_order
(id, create_time, update_time, create_user, update_user, description, remark, repair_id, worker_id, assign_time, accept_time, complete_time, result_desc, status)
VALUES
    (1, '2026-02-01 09:00:00', '2026-02-01 15:00:00', 1, 1, NULL, NULL, 1, 2, '2026-02-01 09:00:00', '2026-02-01 09:20:00', '2026-02-01 15:00:00', '已更换水龙头密封圈，恢复正常使用', 3),
    (2, '2026-02-03 15:00:00', '2026-02-03 15:00:00', 1, 1, NULL, NULL, 2, 3, '2026-02-03 15:00:00', NULL, NULL, NULL, 0);

-- 3.3 报修评价数据
INSERT INTO repair_evaluate
(id, create_time, update_time, create_user, update_user, description, remark, repair_id, owner_id, score, evaluate_content, evaluate_images, service_attitude, repair_quality, response_speed)
VALUES
    (1, '2026-02-01 16:00:00', '2026-02-01 16:00:00', 1, 1, NULL, NULL, 1, 1, 5, '师傅很专业，维修速度也很快，非常满意', NULL, 5, 5, 4);

-- 3.4 投诉建议数据
INSERT INTO complaint
(id, create_time, update_time, create_user, update_user, description, remark, complaint_no, owner_id, complaint_type, complaint_category, complaint_title, complaint_content, complaint_images, contact_phone, handle_user_id, handle_time, handle_result, status, satisfaction)
VALUES
    (1, '2026-02-05 11:00:00', '2026-02-06 09:00:00', 1, 1, NULL, NULL, 'CP20260205001', 3, 1, '环境卫生', '楼道垃圾清理不及时', '2单元楼道垃圾桶经常堆满，希望增加清运频次', NULL, '13900000003', 4, '2026-02-06 09:00:00', '已与保洁公司沟通，增加每日清运次数', 2, 4);
-- 3.5 巡检数据
INSERT INTO inspection
(id, create_time, update_time, create_user, update_user, description, remark, inspection_no, inspection_name, community_id, inspection_area, inspector_id, plan_start_time, plan_end_time, actual_start_time, actual_end_time, inspection_items, status)
VALUES
    (1, '2026-02-01 07:30:00', '2026-02-01 10:00:00', 1, 1, NULL, NULL, 'INS20260201001', '2月消防设施巡检', 1, '1号楼、2号楼', 2, '2026-02-01 08:00:00', '2026-02-01 10:00:00', '2026-02-01 08:05:00', '2026-02-01 09:50:00', '消防栓/灭火器/疏散通道', 2);

-- 3.6 巡检整改数据
INSERT INTO inspection_record
(id, create_time, update_time, create_user, update_user, description, remark, inspection_id, record_no, issue_type, issue_desc, issue_images, issue_location, severity, handle_user_id, handle_desc, handle_images, plan_deadline, actual_complete_time, verify_user_id, verify_time, verify_result, status)
VALUES
    (1, '2026-02-01 09:00:00', '2026-02-03 11:00:00', 1, 1, NULL, NULL, 1, 'REC20260201001', '设施损坏', '1号楼3楼灭火器压力表指针不在绿色区域', NULL, '1号楼3楼楼道', 3, 3, '已更换新灭火器并检查压力', NULL, '2026-02-03 18:00:00', '2026-02-03 11:00:00', 1, '2026-02-03 12:00:00', 1, 3);

