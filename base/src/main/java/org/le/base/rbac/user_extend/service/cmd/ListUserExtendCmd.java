package org.le.base.rbac.user_extend.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.rbac.user_extend.service.impl.UserExtendQueryImpl;
import org.le.base.rbac.user_extend.service.po.UserExtendEntity;
import java.util.List;

/**
 * Created at 2018-08-03 15:19:53
 *
 * @author xiaole
 */
public class ListUserExtendCmd implements Command<List<UserExtendEntity>> {

    protected UserExtendQueryImpl userExtendQueryImpl;

    public ListUserExtendCmd(UserExtendQueryImpl userExtendQueryImpl) {
        this.userExtendQueryImpl = userExtendQueryImpl;
    }

    @Override
    public List<UserExtendEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.rbac.user_extend.list", userExtendQueryImpl);
    }

}
