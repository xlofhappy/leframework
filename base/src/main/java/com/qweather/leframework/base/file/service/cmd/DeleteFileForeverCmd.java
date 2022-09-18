package com.qweather.leframework.base.file.service.cmd;


import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.model.command.Command;

/**
 * Created at 2019-08-15 10:42:38
 *
 * @author xiaole
 */
public class DeleteFileForeverCmd implements Command<Void> {

    protected Long id;

    public DeleteFileForeverCmd(Long id) {
        this.id = id;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if ( id != null ) {
            sqlSession.delete("org.le.base.file.deleteForever", id);
        }
        return null;
    }

}
