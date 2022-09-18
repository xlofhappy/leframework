package com.qweather.leframework.generator.worker.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
public class UpdateWorkerNodeTimeCmd implements Command<Void> {

    protected Long id;
    protected String time;

    public UpdateWorkerNodeTimeCmd(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if (id != null && time != null) {
            Map map = new HashMap<>(2);
            map.put("id", id);
            map.put("time", time);
            sqlSession.insert("org.le.uid.generator.worker.updateTime", map);
        }
        return null;
    }

}
