package com.qweather.leframework.base.rbac.unique.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.base.rbac.unique.service.UniqueType;
import com.qweather.leframework.base.rbac.unique.service.po.UniqueEntity;
import com.qweather.leframework.model.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-03 15:31:45
 *
 * @author xiaole
 */
public class GetUniqueCmd implements Command<UniqueEntity> {

    protected String unionId;
    protected String type;

    public GetUniqueCmd(String unionId, UniqueType type) {
        this.unionId = unionId;
        this.type = type.type();
    }

    @Override
    public UniqueEntity execute(SqlSession sqlSession) {
        Map<String, String> map = new HashMap<>(2);
        map.put("unionId", unionId);
        map.put("type", type);
        return sqlSession.selectOne("org.le.base.rbac.unique.get", map);
    }

}
