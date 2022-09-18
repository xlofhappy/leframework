package org.le.base.i18n.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.i18n.service.impl.I18nQueryImpl;
import org.le.base.i18n.service.po.I18nEntity;
import java.util.List;

/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
public class ListI18nCmd implements Command<List<I18nEntity>> {

    protected I18nQueryImpl i18nQueryImpl;

    public ListI18nCmd(I18nQueryImpl i18nQueryImpl) {
        this.i18nQueryImpl = i18nQueryImpl;
    }

    @Override
    public List<I18nEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.i18n.list", i18nQueryImpl);
    }

}
