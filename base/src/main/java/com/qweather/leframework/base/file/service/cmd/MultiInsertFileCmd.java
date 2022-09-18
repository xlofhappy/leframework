package com.qweather.leframework.base.file.service.cmd;


import com.qweather.leframework.base.file.service.po.FileEntity;
import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

import java.util.List;

/**
 * Created at 2019-08-15 10:42:38
 *
 * @author xiaole
 */
public class MultiInsertFileCmd implements Command<Void> {

    protected List<FileEntity> entityList;

    public MultiInsertFileCmd(List<FileEntity> entityList) {
        this.entityList = entityList;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( entityList != null && entityList.size() > 0 ) {
            sqlSession.insert("com.qweather.leframework.base.file.multiInsert", entityList);
        }
        return null;
    }

}
