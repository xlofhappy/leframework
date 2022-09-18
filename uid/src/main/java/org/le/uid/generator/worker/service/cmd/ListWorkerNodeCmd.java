package org.le.uid.generator.worker.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.uid.generator.worker.service.impl.WorkerNodeQueryImpl;
import org.le.uid.generator.worker.service.pojo.WorkerNodeEntity;

import java.util.List;

/**
 * Created at 2019-08-07 15:34:13
 *
 * @author xiaole
 */
public class ListWorkerNodeCmd implements Command<List<WorkerNodeEntity>> {

    protected WorkerNodeQueryImpl workerNodeQueryImpl;

    public ListWorkerNodeCmd(WorkerNodeQueryImpl workerNodeQueryImpl) {
        this.workerNodeQueryImpl = workerNodeQueryImpl;
    }

    @Override
    public List<WorkerNodeEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.uid.generator.worker.list", workerNodeQueryImpl);
    }

}
