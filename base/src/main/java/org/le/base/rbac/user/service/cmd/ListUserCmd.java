package org.le.base.rbac.user.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.rbac.user.service.impl.UserQueryImpl;
import org.le.base.rbac.user.service.po.UserEntity;
import java.util.List;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
public class ListUserCmd implements Command<List<UserEntity>> {

    protected UserQueryImpl userQueryImpl;

    public ListUserCmd(UserQueryImpl userQueryImpl) {
        this.userQueryImpl = userQueryImpl;
    }

    @Override
    public List<UserEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.rbac.user.list", userQueryImpl);
    }

}
