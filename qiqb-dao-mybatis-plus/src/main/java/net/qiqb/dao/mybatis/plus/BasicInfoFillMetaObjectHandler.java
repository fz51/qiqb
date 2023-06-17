package net.qiqb.dao.mybatis.plus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import net.qiqb.auth.AuthUserHolder;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

@Slf4j
public class BasicInfoFillMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "createBy", () -> AuthUserHolder.get().getCurrent().toString(), String.class);
        this.strictInsertFill(metaObject, "lastModifyTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "lastModifyBy", () -> AuthUserHolder.get().getCurrent().toString(), String.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.strictUpdateFill(metaObject, "lastModifyTime", LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "lastModifyBy", () -> AuthUserHolder.get().getCurrent().toString(), String.class);

    }
}