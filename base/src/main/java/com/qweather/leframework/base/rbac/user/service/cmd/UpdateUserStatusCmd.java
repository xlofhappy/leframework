package com.qweather.leframework.base.rbac.user.service.cmd;

import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

import java.util.HashMap;
import java.util.Map;


/**
 * Created at 2019-06-01 13:33:41
 *
 * @author xiaole
 */
public class UpdateUserStatusCmd implements Command<Void> {

    protected Long userId;
    protected byte status;

    public UpdateUserStatusCmd(Long userId, byte status) {
        this.userId = userId;
        this.status = status;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if (userId != null) {
            Map map = new HashMap(2);
            map.put("id", userId);
            map.put("status", status);
            sqlSession.update("org.le.base.rbac.user.updateUserStatus", map);
        }
        return null;
    }
}
