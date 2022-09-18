package com.qweather.leframework.generator.worker.service.cmd;


import com.qweather.leframework.generator.worker.service.impl.WorkerNodeQueryImpl;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

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
        return sqlSession.selectOne("com.qweather.leframework.uid.generator.worker.count", workerNodeQueryImpl);
    }

}
