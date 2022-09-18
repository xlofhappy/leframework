package org.le.base.rbac.user.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
public class ResetUserPasswordCmd implements Command<Void> {

    protected Long id;
    protected String password;
    protected String salt;

    public ResetUserPasswordCmd(Long id, String password, String salt) {
        this.id = id;
        this.password = password;
        this.salt = salt;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( id != null && password != null ) {
            Map map = new HashMap(3);
            map.put("id", id);
            map.put("password", password);
            map.put("salt", salt);
            sqlSession.insert("org.le.base.rbac.user.resetPassword", map);
        }
        return null;
    }

}
