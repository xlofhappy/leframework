package com.qweather.leframework.base.i18n.service.cmd;


import com.qweather.leframework.base.i18n.service.po.I18nEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;
import com.qweather.leframework.base.i18n.service.impl.I18nQueryImpl;

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
        return sqlSession.selectList("com.qweather.leframework.base.i18n.list", i18nQueryImpl);
    }

}
