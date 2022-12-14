package com.qweather.leframework.base.rbac.user_extend.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;
import com.qweather.leframework.base.rbac.user_extend.service.impl.UserExtendQueryImpl;

/**
 * Created at 2018-08-03 15:19:53
 *
 * @author xiaole
 */
public class CountUserExtendCmd implements Command<Long> {

    protected UserExtendQueryImpl userExtendQueryImpl;

    public CountUserExtendCmd(UserExtendQueryImpl userExtendQueryImpl) {
        this.userExtendQueryImpl = userExtendQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("com.qweather.leframework.base.rbac.user_extend.count", userExtendQueryImpl);
    }

}
