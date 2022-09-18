package org.le.base.rbac.user.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.rbac.user.service.impl.UserQueryImpl;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
public class CountUserCmd implements Command<Long> {

    protected UserQueryImpl userQueryImpl;

    public CountUserCmd(UserQueryImpl userQueryImpl) {
        this.userQueryImpl = userQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("org.le.base.rbac.user.count", userQueryImpl);
    }

}
