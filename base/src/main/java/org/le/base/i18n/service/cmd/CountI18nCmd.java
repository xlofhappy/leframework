package org.le.base.i18n.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.i18n.service.impl.I18nQueryImpl;

/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
public class CountI18nCmd implements Command<Long> {

    protected I18nQueryImpl i18nQueryImpl;

    public CountI18nCmd(I18nQueryImpl i18nQueryImpl) {
        this.i18nQueryImpl = i18nQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("org.le.base.i18n.count", i18nQueryImpl);
    }

}
