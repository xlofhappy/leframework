package com.qweather.leframework.base.variable.service.cmd;


import com.qweather.leframework.model.command.Command;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public class IncrVariableCmd implements Command<Void> {

    protected final Long id;
    protected final Long step;

    public IncrVariableCmd(Long id, Long step) {
        this.id = id;
        this.step = step;
    }


    @Override
    public Void execute(SqlSession sqlSession) {
        Map<String, Long> map = new HashMap<>(2);
        map.put("id", id);
        map.put("step", step);
        sqlSession.update("org.le.base.variable.incr", map);
        return null;
    }

}
