package com.qweather.leframework.base.rbac.user_extend.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-03 15:19:53
 *
 * @author xiaole
 */
public class DeleteUserExtendCmd implements Command<Void> {

    protected Long uid;
    protected Integer k;

    public DeleteUserExtendCmd(Long uid) {
        this.uid = uid;
        this.k = null;
    }

    public DeleteUserExtendCmd(Long uid, Integer k) {
        this.uid = uid;
        this.k = k;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( uid != null && k != null ) {
            Map map = new HashMap<>(2);
            map.put("uid", uid);
            map.put("k", k);
            sqlSession.delete("org.le.base.rbac.user_extend.delete", map);
        }
        return null;
    }

}
