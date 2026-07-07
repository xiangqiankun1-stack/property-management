# 建表脚本


-- =============================================
-- 一、系统权限
-- =============================================

DROP TABLE IF EXISTS sys_permission_info;
CREATE TABLE sys_permission_info(
                                    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                              `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                                    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                              `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                              `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                          `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                         `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                      `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                      `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                       `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                             `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                                `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                          `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                           `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
                                  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
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
-- 四、财务管理
-- =============================================

-- 4.1 费用项目表
DROP TABLE IF EXISTS fee_item;
CREATE TABLE fee_item(
                         `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
                         `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
                         `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
                         `create_user` BIGINT NOT NULL COMMENT '创建人' ,
                         `update_user` BIGINT NOT NULL COMMENT '更新人' ,
                         `description` VARCHAR(255) COMMENT '描述' ,
                         `remark` VARCHAR(255) COMMENT '备注' ,
                         `fee_code` VARCHAR(64) NOT NULL COMMENT '费用编码' ,
                         `fee_name` VARCHAR(128) NOT NULL COMMENT '费用名称(如：物业费/停车费/水费)' ,
                         `fee_type` INT NOT NULL COMMENT '费用类型：1.物业费 2.水费 3.电费 4.停车费 5.其他' ,
                         `charge_mode` INT NOT NULL COMMENT '计费方式：1.按面积计费 2.固定金额 3.按用量计费' ,
                         `unit_price` DECIMAL(10,2) NOT NULL COMMENT '单价(元)' ,
                         `unit` VARCHAR(32) COMMENT '计价单位(如：元/㎡/月、元/车位/月)' ,
                         `billing_cycle` INT NOT NULL DEFAULT 1 COMMENT '计费周期：1.月度 2.季度 3.年度 4.一次性' ,
                         `status` INT NOT NULL DEFAULT 1 COMMENT '状态：1.启用 0.停用' ,
                         PRIMARY KEY (id)
) COMMENT = '费用项目信息表';
CREATE UNIQUE INDEX uk_fee_code ON fee_item(fee_code);

-- 4.2 账单表
DROP TABLE IF EXISTS bill;
CREATE TABLE bill(
                     `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
                     `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
                     `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
                     `create_user` BIGINT NOT NULL COMMENT '创建人' ,
                     `update_user` BIGINT NOT NULL COMMENT '更新人' ,
                     `description` VARCHAR(255) COMMENT '描述' ,
                     `remark` VARCHAR(255) COMMENT '备注' ,
                     `bill_no` VARCHAR(64) NOT NULL COMMENT '账单编号' ,
                     `house_id` BIGINT NOT NULL COMMENT '房屋ID' ,
                     `owner_id` BIGINT NOT NULL COMMENT '业主ID' ,
                     `fee_item_id` BIGINT NOT NULL COMMENT '费用项目ID' ,
                     `period_start` DATE NOT NULL COMMENT '账期开始日期' ,
                     `period_end` DATE NOT NULL COMMENT '账期结束日期' ,
                     `amount` DECIMAL(10,2) NOT NULL COMMENT '应收金额(元)' ,
                     `paid_amount` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '已收金额(元)' ,
                     `due_date` DATE COMMENT '缴费截止日期' ,
                     `generate_time` DATETIME NOT NULL COMMENT '账单生成时间' ,
                     `status` INT NOT NULL DEFAULT 0 COMMENT '状态：0.未缴纳 1.部分缴纳 2.已缴清 3.已作废' ,
                     PRIMARY KEY (id),
                     CONSTRAINT fk_bill_house FOREIGN KEY (house_id) REFERENCES house(id),
                     CONSTRAINT fk_bill_owner FOREIGN KEY (owner_id) REFERENCES owner(id),
                     CONSTRAINT fk_bill_fee_item FOREIGN KEY (fee_item_id) REFERENCES fee_item(id)
) COMMENT = '账单信息表';
CREATE UNIQUE INDEX uk_bill_no ON bill(bill_no);
CREATE INDEX idx_bill_house_id ON bill(house_id);
CREATE INDEX idx_bill_owner_id ON bill(owner_id);
CREATE INDEX idx_bill_fee_item_id ON bill(fee_item_id);

-- 4.3 缴费登记表
DROP TABLE IF EXISTS payment_record;
CREATE TABLE payment_record(
                               `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' ,
                               `create_time` DATETIME NOT NULL COMMENT '创建时间' ,
                               `update_time` DATETIME NOT NULL COMMENT '更新时间' ,
                               `create_user` BIGINT NOT NULL COMMENT '创建人' ,
                               `update_user` BIGINT NOT NULL COMMENT '更新人' ,
                               `description` VARCHAR(255) COMMENT '描述' ,
                               `remark` VARCHAR(255) COMMENT '备注' ,
                               `payment_no` VARCHAR(64) NOT NULL COMMENT '缴费流水号' ,
                               `bill_id` BIGINT NOT NULL COMMENT '账单ID' ,
                               `owner_id` BIGINT NOT NULL COMMENT '缴费业主ID' ,
                               `pay_amount` DECIMAL(10,2) NOT NULL COMMENT '本次缴费金额(元)' ,
                               `pay_method` INT NOT NULL COMMENT '缴费方式：1.现金 2.微信 3.支付宝 4.银行卡 5.其他' ,
                               `pay_time` DATETIME NOT NULL COMMENT '缴费时间' ,
                               `operator_id` BIGINT NOT NULL COMMENT '登记人员ID(关联sys_user_info)' ,
                               `voucher_no` VARCHAR(128) COMMENT '第三方支付流水号/凭证号' ,
                               `status` INT NOT NULL DEFAULT 1 COMMENT '状态：1.有效 0.已作废(如登记有误被撤销)' ,
                               PRIMARY KEY (id),
                               CONSTRAINT fk_payment_bill FOREIGN KEY (bill_id) REFERENCES bill(id),
                               CONSTRAINT fk_payment_owner FOREIGN KEY (owner_id) REFERENCES owner(id),
                               CONSTRAINT fk_payment_operator FOREIGN KEY (operator_id) REFERENCES sys_user_info(id)
) COMMENT = '缴费登记信息表';
CREATE UNIQUE INDEX uk_payment_no ON payment_record(payment_no);
CREATE INDEX idx_payment_bill_id ON payment_record(bill_id);
CREATE INDEX idx_payment_owner_id ON payment_record(owner_id);
CREATE INDEX idx_payment_operator_id ON payment_record(operator_id);


-- 为所有表添加 deleted 字段
ALTER TABLE sys_user_info ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE sys_role_info ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE sys_permission_info ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE sys_user_role ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE sys_role_permission ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE community ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE building ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE house ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE owner ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE bill ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE payment_record ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE repair ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE repair_order ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE repair_evaluate ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE complaint ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE fee_item ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE inspection ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';
ALTER TABLE inspection_record ADD COLUMN deleted INT DEFAULT 0 COMMENT '逻辑删除标记 0-未删除 1-已删除';

-- 为 deleted 字段创建索引（提升查询性能）
CREATE INDEX idx_deleted ON sys_user_info(deleted);
CREATE INDEX idx_deleted ON sys_role_info(deleted);
CREATE INDEX idx_deleted ON sys_permission_info(deleted);
CREATE INDEX idx_deleted ON sys_user_role(deleted);
CREATE INDEX idx_deleted ON sys_role_permission(deleted);
CREATE INDEX idx_deleted ON community(deleted);
CREATE INDEX idx_deleted ON building(deleted);
CREATE INDEX idx_deleted ON house(deleted);
CREATE INDEX idx_deleted ON owner(deleted);
CREATE INDEX idx_deleted ON bill(deleted);
CREATE INDEX idx_deleted ON payment_record(deleted);
CREATE INDEX idx_deleted ON repair(deleted);
CREATE INDEX idx_deleted ON repair_order(deleted);
CREATE INDEX idx_deleted ON repair_evaluate(deleted);
CREATE INDEX idx_deleted ON complaint(deleted);
CREATE INDEX idx_deleted ON fee_item(deleted);
CREATE INDEX idx_deleted ON inspection(deleted);
CREATE INDEX idx_deleted ON inspection_record(deleted);