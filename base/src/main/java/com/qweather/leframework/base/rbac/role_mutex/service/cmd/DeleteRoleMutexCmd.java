package com.qweather.leframework.base.rbac.role_mutex.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-03 14:34:14
 *
 * @author xiaole
 */
public class DeleteRoleMutexCmd implements Command<Void> {

    protected Long roleA;
    protected Long roleB;

    public DeleteRoleMutexCmd(Long roleA, Long roleB) {
        this.roleA = roleA;
        this.roleB = roleB;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( roleA != null && roleB != null ) {
            Map map = new HashMap<>(2);
            map.put("roleA", roleA);
            map.put("roleB", roleB);
            sqlSession.delete("org.le.base.rbac.role_mutex.delete", map);
        }
        return null;
    }

}
