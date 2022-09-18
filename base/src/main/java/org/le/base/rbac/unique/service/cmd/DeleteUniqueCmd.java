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
public class DeleteUniqueCmd implements Command<Void> {

    protected String unionId;
    protected String type;

    public DeleteUniqueCmd(String unionId, UniqueType type) {
        this.unionId = unionId;
        this.type = type.type();
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if (unionId != null && type != null) {
            Map<String, String> map = new HashMap<>(2);
            map.put("unionId", unionId);
            map.put("type", type);
            sqlSession.insert("org.le.base.rbac.unique.delete", map);
        }
        return null;
    }

}
