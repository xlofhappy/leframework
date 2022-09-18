package com.qweather.leframework.base.rbac.user.service.cmd;


import com.qweather.leframework.base.rbac.user.service.po.UserEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

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
            return sqlSession.selectOne("com.qweather.leframework.base.rbac.user.getById", id);
        }
        return null;
    }

}
