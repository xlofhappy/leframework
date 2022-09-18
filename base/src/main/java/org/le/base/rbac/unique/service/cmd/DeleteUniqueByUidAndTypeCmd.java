package org.le.base.rbac.unique.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.base.rbac.unique.service.UniqueType;
import org.le.model.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-03 15:31:45
 *
 * @author xiaole
 */
public class DeleteUniqueByUidAndTypeCmd implements Command<Void> {

    protected Long uid;
    protected String type;

    public DeleteUniqueByUidAndTypeCmd(Long uid, UniqueType type) {
        this.uid = uid;
        this.type = type.type();
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if (uid != null && type != null) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("uid", uid);
            map.put("type", type);
            sqlSession.insert("org.le.base.rbac.unique.deleteByUidAndType", map);
        }
        return null;
    }

}
