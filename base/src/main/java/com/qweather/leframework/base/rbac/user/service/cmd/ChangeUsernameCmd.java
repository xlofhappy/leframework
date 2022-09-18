package com.qweather.leframework.base.rbac.user.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
public class ChangeUsernameCmd implements Command<Void> {

    protected Long id;
    protected String username;

    public ChangeUsernameCmd(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( id != null && username != null ) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", id);
            map.put("username", username);
            sqlSession.insert("com.qweather.leframework.base.rbac.user.changeUsername", map);
        }
        return null;
    }

}
