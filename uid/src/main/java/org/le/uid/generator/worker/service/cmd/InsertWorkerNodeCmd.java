package org.le.uid.generator.worker.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.uid.generator.worker.service.pojo.WorkerNodeEntity;

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
            sqlSession.insert("org.le.uid.generator.worker.insert", entity);
        }
        return null;
    }

}
