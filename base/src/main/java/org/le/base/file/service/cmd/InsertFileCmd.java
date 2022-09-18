package org.le.base.file.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.core.config.Config;
import org.le.base.file.service.po.FileEntity;

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
