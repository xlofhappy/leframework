package org.le.base.i18n.service.cmd;


import org.le.base.i18n.service.po.I18nEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
public class InsertI18nCmd implements Command<Void> {

    protected I18nEntity entity;

    public InsertI18nCmd(I18nEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entity != null ) {
            sqlSession.insert("org.le.base.i18n.insert", entity);
        }
        return null;
    }

}
