# 初始化数据


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
(1, '2026-02-05 11:00:00', '2026-02-06 09:00:00', 1, 1, NULL, NULL, 'CP20260205001', 3, 1, '环境卫生', '楼道垃圾清理不及时', '2单元楼道垃圾桶经常堆满，希望增加清运频次', NULL, '13900000003', 4, '2026-02-06 09:00:00', '已与保洁公司沟通，增加每日清运次数', 2, 4),
(2, '2026-02-07 16:30:00', NULL, 1, 1, NULL, NULL, 'CP20260207001', 1, 2, '物业服务', '建议增设快递柜', '小区门口人流量大，建议增设智能快递柜方便取件', NULL, '13900000001', NULL, NULL, 0, NULL);

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

-- =============================================
-- 四、财务管理
-- =============================================

-- 4.1 费用项目数据
INSERT INTO fee_item
(id, create_time, update_time, create_user, update_user, description, remark, fee_code, fee_name, fee_type, charge_mode, unit_price, unit, billing_cycle, status)
VALUES
(1, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '按建筑面积每月收取', NULL, 'FEE_PROPERTY', '物业费', 1, 1, 2.50, '元/㎡/月', 1, 1),
(2, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '固定车位月租', NULL, 'FEE_PARKING', '停车费', 4, 2, 300.00, '元/车位/月', 1, 1),
(3, '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1, '按实际用水量计费', NULL, 'FEE_WATER', '水费', 2, 3, 4.50, '元/吨', 1, 1);

-- 4.2 账单数据
INSERT INTO bill
(id, create_time, update_time, create_user, update_user, description, remark, bill_no, house_id, owner_id, fee_item_id, period_start, period_end, amount, paid_amount, due_date, generate_time, status)
VALUES
(1, '2026-02-01 00:10:00', '2026-02-10 14:20:00', 1, 4, NULL, NULL, 'BL202602010001', 1, 1, 1, '2026-02-01', '2026-02-28', 223.75, 223.75, '2026-02-15', '2026-02-01 00:10:00', 2),
(2, '2026-02-01 00:10:00', '2026-02-01 00:10:00', 1, 1, NULL, NULL, 'BL202602010002', 2, 2, 1, '2026-02-01', '2026-02-28', 300.00, 0.00, '2026-02-15', '2026-02-01 00:10:00', 0),
(3, '2026-02-01 00:10:00', '2026-02-12 09:00:00', 1, 4, NULL, NULL, 'BL202602010003', 2, 2, 2, '2026-02-01', '2026-02-28', 300.00, 150.00, '2026-02-15', '2026-02-01 00:10:00', 1);

-- 4.3 缴费登记数据
INSERT INTO payment_record
(id, create_time, update_time, create_user, update_user, description, remark, payment_no, bill_id, owner_id, pay_amount, pay_method, pay_time, operator_id, voucher_no, status)
VALUES
(1, '2026-02-10 14:20:00', '2026-02-10 14:20:00', 4, 4, NULL, NULL, 'PAY202602100001', 1, 1, 223.75, 2, '2026-02-10 14:20:00', 4, 'WX20260210142012345', 1),
(2, '2026-02-12 09:00:00', '2026-02-12 09:00:00', 4, 4, NULL, '业主先支付部分车位费，剩余月底补齐', 'PAY202602120001', 3, 2, 150.00, 1, '2026-02-12 09:00:00', 4, NULL, 1);