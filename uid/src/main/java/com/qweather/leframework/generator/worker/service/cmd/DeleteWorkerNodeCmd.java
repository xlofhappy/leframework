package com.qweather.leframework.generator.worker.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
public class DeleteWorkerNodeCmd implements Command<Void> {

    protected Long id;

    public DeleteWorkerNodeCmd(Long id) {
        this.id = id;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if (id != null) {
            sqlSession.insert("org.le.uid.generator.worker.delete", id);
        }
        return null;
    }

}
