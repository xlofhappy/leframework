package com.qweather.leframework.base.rbac.unique.service.cmd;


import com.qweather.leframework.base.rbac.unique.service.impl.UniqueQueryImpl;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2018-08-03 15:31:45
 *
 * @author xiaole
 */
public class CountUniqueCmd implements Command<Long> {

    protected UniqueQueryImpl uniqueQueryImpl;

    public CountUniqueCmd(UniqueQueryImpl uniqueQueryImpl) {
        this.uniqueQueryImpl = uniqueQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("com.qweather.leframework.base.rbac.unique.count", uniqueQueryImpl);
    }

}
