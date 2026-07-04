DROP TABLE IF EXISTS sys_permission_info;
CREATE TABLE sys_permission_info(
                                    `id` BIGINT NOT NULL COMMENT '主键;主键' ,
                                    `version` INT NOT NULL COMMENT '版本号;版本号' ,
                                    `create_time` DATETIME NOT NULL COMMENT '创建时间;创建时间' ,
                                    `update_time` DATETIME NOT NULL COMMENT '更新时间;更新时间' ,
                                    `create_user` BIGINT NOT NULL COMMENT '创建⼈;创建⼈' ,
                                    `update_user` BIGINT NOT NULL COMMENT '更新⼈;更新⼈' ,
                                    `description` VARCHAR(255) COMMENT '描述;描述' ,
                                    `remark` VARCHAR(255) COMMENT '备注;备注' ,
                                    `permission_code` VARCHAR(128) NOT NULL COMMENT '权限编码;权限编码' ,
                                    `permission_name` VARCHAR(255) NOT NULL COMMENT '权限名称;权限名称' ,
                                    `permission_type` INT NOT NULL COMMENT '权限类型;权限类型：1.⽬录 2.菜单 3.按钮' ,
                                    `parent_id` BIGINT NOT NULL COMMENT '⽗级节点ID;⽗级节点ID' ,
                                    `permission_icon` VARCHAR(255) COMMENT '图标;图标' ,
                                    `permission_str` VARCHAR(255) COMMENT '权限字串;权限字串' ,
                                    `permission_component` VARCHAR(255) COMMENT '组件地址;组件地址' ,
                                    `sort` INT NOT NULL COMMENT '排序字段;排序字段' ,
                                    PRIMARY KEY (id)
) COMMENT = '权限信息表';
DROP TABLE IF EXISTS sys_role_info;
CREATE TABLE sys_role_info(
                              `id` BIGINT NOT NULL COMMENT '主键;主键' ,
                              `version` INT NOT NULL COMMENT '版本号;版本号' ,
                              `create_time` DATETIME NOT NULL COMMENT '创建时间;创建时间' ,
                              `update_time` DATETIME NOT NULL COMMENT '更新时间;更新时间' ,
                              `create_user` BIGINT NOT NULL COMMENT '创建⼈;创建⼈' ,
                              `update_user` BIGINT NOT NULL COMMENT '更新⼈;更新⼈' ,
                              `description` VARCHAR(255) COMMENT '描述;描述' ,
                              `remark` VARCHAR(255) COMMENT '备注;备注' ,
                              `role_code` VARCHAR(128) NOT NULL COMMENT '⻆⾊代码;⻆⾊代码' ,
                              `role_name` VARCHAR(255) NOT NULL COMMENT '⻆⾊名称;⻆⾊名称' ,
                              PRIMARY KEY (id)
) COMMENT = '⻆⾊信息表';
DROP TABLE IF EXISTS sys_role_permission;
CREATE TABLE sys_role_permission(
                            `id` BIGINT NOT NULL COMMENT '主键;主键' ,
                            `version` INT NOT NULL COMMENT '版本号;版本号' ,
                            `create_time` DATETIME NOT NULL COMMENT '创建时间;创建时间' ,
                            `update_time` DATETIME NOT NULL COMMENT '更新时间;更新时间' ,
                            `create_user` BIGINT NOT NULL COMMENT '创建⼈;创建⼈' ,
                            `update_user` BIGINT NOT NULL COMMENT '更新⼈;更新⼈' ,
                            `description` VARCHAR(255) COMMENT '描述;描述' ,
                            `remark` VARCHAR(255) COMMENT '备注;备注' ,
                            `role_info_id` BIGINT NOT NULL COMMENT '⻆⾊ID;⻆⾊ID' ,
                            `permission_info_id` BIGINT NOT NULL COMMENT '权限ID;权限ID' ,
                            PRIMARY KEY (id)
    ) COMMENT = '⻆⾊权限关联表';
CREATE INDEX permission_info_id ON sys_role_permission(permission_info_id);
CREATE INDEX role_info_id ON sys_role_permission(role_info_id);
DROP TABLE IF EXISTS sys_user_info;
CREATE TABLE sys_user_info(
                              `id` BIGINT NOT NULL COMMENT '主键;主键' ,
                              `version` INT NOT NULL COMMENT '版本号;版本号' ,
                              `create_time` DATETIME NOT NULL COMMENT '创建时间;创建时间' ,
                              `update_time` DATETIME NOT NULL COMMENT '更新时间;更新时间' ,
                              `create_user` BIGINT NOT NULL COMMENT '创建⼈;创建⼈' ,
                              `update_user` BIGINT NOT NULL COMMENT '更新⼈;更新⼈' ,
                              `description` VARCHAR(255) COMMENT '描述;描述' ,
                              `remark` VARCHAR(255) COMMENT '备注;备注' ,
                              `user_name` VARCHAR(64) NOT NULL COMMENT '⽤户名，登录⽤;⽤户名，登录⽤',
                              `password` VARCHAR(128) NOT NULL COMMENT '密码;密码' ,
                              `phone_number` VARCHAR(16) COMMENT '电话号码;电话号码' ,
                              `avatar_address` VARCHAR(128) COMMENT '头像地址;头像地址' ,
                              `full_name` VARCHAR(128) COMMENT '姓名;姓名' ,
                              PRIMARY KEY (id)
) COMMENT = '⽤户信息表';
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role(
                              `id` BIGINT NOT NULL COMMENT '主键;主键' ,
                              `version` INT NOT NULL COMMENT '版本号;版本号' ,
                              `create_time` DATETIME NOT NULL COMMENT '创建时间;创建时间' ,
                              `update_time` DATETIME NOT NULL COMMENT '更新时间;更新时间' ,
                              `create_user` BIGINT NOT NULL COMMENT '创建⼈;创建⼈' ,
                              `update_user` BIGINT NOT NULL COMMENT '更新⼈;更新⼈' ,
                              `description` VARCHAR(255) COMMENT '描述;描述' ,
                              `remark` VARCHAR(255) COMMENT '备注;备注' ,
                              `user_info_id` BIGINT NOT NULL COMMENT '⽤户ID;⽤户ID' ,
                              `role_info_id` BIGINT NOT NULL COMMENT '⻆⾊ID;⻆⾊ID' ,
                              PRIMARY KEY (id)
) COMMENT = '⽤户⻆⾊关联表';
CREATE INDEX role_info_id ON sys_user_role(role_info_id);
CREATE INDEX user_info_id ON sys_user_role(user_info_id);