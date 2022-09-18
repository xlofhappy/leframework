package com.qweather.leframework.base.file.service.cmd;


import com.qweather.leframework.base.file.service.po.FileEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;
import com.qweather.leframework.core.config.Config;

/**
 * Created at 2019-08-15 10:42:38
 *
 * @author xiaole
 */
public class InsertFileCmd implements Command<Void> {

    protected FileEntity entity;

    public InsertFileCmd(FileEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null ) {
            if ( entity.getId() == null ) {
                entity.setId(Config.getDaoConfig().getIdPool().getNextId());
            }
            sqlSession.insert("org.le.base.file.insert", entity);
        }
        return null;
    }

}
