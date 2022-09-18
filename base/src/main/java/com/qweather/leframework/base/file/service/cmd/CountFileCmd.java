package com.qweather.leframework.base.file.service.cmd;


import com.qweather.leframework.base.file.service.impl.FileQueryImpl;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2019-08-15 10:42:38
 *
 * @author xiaole
 */
public class CountFileCmd implements Command<Long> {

    protected FileQueryImpl fileQueryImpl;

    public CountFileCmd(FileQueryImpl fileQueryImpl) {
        this.fileQueryImpl = fileQueryImpl;
    }

    @Override
    public Long execute(SqlSession sqlSession) {
        return sqlSession.selectOne("org.le.base.file.count", fileQueryImpl);
    }

}
