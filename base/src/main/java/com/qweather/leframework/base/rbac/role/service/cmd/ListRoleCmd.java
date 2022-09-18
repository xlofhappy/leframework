package com.qweather.leframework.base.rbac.role.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;
import com.qweather.leframework.base.rbac.role.service.impl.RoleQueryImpl;
import com.qweather.leframework.base.rbac.role.service.po.RoleEntity;
import java.util.List;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class ListRoleCmd implements Command<List<RoleEntity>> {

    protected RoleQueryImpl roleQueryImpl;

    public ListRoleCmd(RoleQueryImpl roleQueryImpl) {
        this.roleQueryImpl = roleQueryImpl;
    }

    @Override
    public List<RoleEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("com.qweather.leframework.base.rbac.role.list", roleQueryImpl);
    }

}
