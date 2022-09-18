package org.le.base.rbac.unique.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;
import org.le.base.rbac.unique.service.impl.UniqueQueryImpl;
import org.le.base.rbac.unique.service.po.UniqueEntity;
import java.util.List;

/**
 * Created at 2018-08-03 15:31:45
 *
 * @author xiaole
 */
public class ListUniqueCmd implements Command<List<UniqueEntity>> {

    protected UniqueQueryImpl uniqueQueryImpl;

    public ListUniqueCmd(UniqueQueryImpl uniqueQueryImpl) {
        this.uniqueQueryImpl = uniqueQueryImpl;
    }

    @Override
    public List<UniqueEntity> execute(SqlSession sqlSession) {
        return sqlSession.selectList("org.le.base.rbac.unique.list", uniqueQueryImpl);
    }

}
