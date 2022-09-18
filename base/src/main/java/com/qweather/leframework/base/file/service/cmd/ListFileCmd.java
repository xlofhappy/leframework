package com.qweather.leframework.base.file.service.cmd;


import com.qweather.leframework.base.file.service.impl.FileQueryImpl;
import com.qweather.leframework.base.file.service.po.FileEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

import java.util.List;

/**
 * Created at 2019-08-15 10:42:38
 *
 * @author xiaole
 */
public class ListFileCmd implements Command<List<FileEntity>> {

    protected FileQueryImpl fileQueryImpl;

    public ListFileCmd(FileQueryImpl fileQueryImpl) {
        this.fileQueryImpl = fileQueryImpl;
    }

    @Override
    public List<FileEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("com.qweather.leframework.base.file.list", fileQueryImpl);
    }

}
