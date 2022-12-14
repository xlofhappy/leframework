package com.qweather.leframework.generator.worker.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;
import com.qweather.leframework.generator.worker.service.pojo.WorkerNodeEntity;

/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
public class InsertWorkerNodeCmd implements Command<Void> {

    protected WorkerNodeEntity entity;

    public InsertWorkerNodeCmd(WorkerNodeEntity entity) {
        this.entity = entity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if (entity != null) {
            sqlSession.insert("com.qweather.leframework.uid.generator.worker.insert", entity);
        }
        return null;
    }

}
