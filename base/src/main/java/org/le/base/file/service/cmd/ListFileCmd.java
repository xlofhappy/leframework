package org.le.base.file.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.file.service.impl.FileQueryImpl;
import org.le.base.file.service.po.FileEntity;
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
        return sqlSession.selectList("org.le.base.file.list", fileQueryImpl);
    }

}
