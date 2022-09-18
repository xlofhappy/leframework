package org.le.base.rbac.unique.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.rbac.unique.service.impl.UniqueQueryImpl;

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
        return sqlSession.selectOne("org.le.base.rbac.unique.count", uniqueQueryImpl);
    }

}
