package org.le.base.rbac.user.service.cmd;


import org.le.base.rbac.user.service.po.UserEntity;
import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
public class GetUserByIdCmd implements Command<UserEntity> {

    protected Long id;

    public GetUserByIdCmd(Long id) {
        this.id = id;
    }

    @Override
    public UserEntity execute(SqlSession sqlSession) {
        if ( id != null ) {
            return sqlSession.selectOne("org.le.base.rbac.user.getById", id);
        }
        return null;
    }

}
