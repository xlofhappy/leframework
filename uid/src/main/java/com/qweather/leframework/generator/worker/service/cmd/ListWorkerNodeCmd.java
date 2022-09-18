package com.qweather.leframework.generator.worker.service.cmd;


import com.qweather.leframework.model.command.Command;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.generator.worker.service.impl.WorkerNodeQueryImpl;
import com.qweather.leframework.generator.worker.service.pojo.WorkerNodeEntity;

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
