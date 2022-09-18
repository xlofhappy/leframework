package org.le.uid.generator.worker.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.uid.generator.worker.service.impl.WorkerNodeQueryImpl;

/**
 * Created at 2019-08-07 14:34:13
 *
 * @author xiaole
 */
public class CountWorkerNodeCmd implements Command<Long> {

    protected WorkerNodeQueryImpl workerNodeQueryImpl;

    public CountWorkerNodeCmd(WorkerNodeQueryImpl workerNodeQueryImpl) {
        this.workerNodeQueryImpl = workerNodeQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("org.le.uid.generator.worker.count", workerNodeQueryImpl);
    }

}
