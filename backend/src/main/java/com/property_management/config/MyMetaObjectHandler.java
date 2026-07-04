package com.property_management.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.property_management.utils.ThreadLocalUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        Long currentUserId = ThreadLocalUtil.getCurrentUserId();

        if (currentUserId == null) {
            currentUserId = 0L;
        }

        this.strictInsertFill(metaObject,
                "createTime",
                LocalDateTime.class,
                LocalDateTime.now());

        this.strictInsertFill(metaObject,
                "updateTime",
                LocalDateTime.class,
                LocalDateTime.now());

        this.strictInsertFill(metaObject,
                "createUser",
                Long.class,
                currentUserId);

        this.strictInsertFill(metaObject,
                "updateUser",
                Long.class,
                currentUserId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        Long currentUserId = ThreadLocalUtil.getCurrentUserId();

        this.strictUpdateFill(metaObject,
                "updateTime",
                LocalDateTime.class,
                LocalDateTime.now());

        this.strictUpdateFill(metaObject,
                "updateUser",
                Long.class,
                currentUserId);
    }
}