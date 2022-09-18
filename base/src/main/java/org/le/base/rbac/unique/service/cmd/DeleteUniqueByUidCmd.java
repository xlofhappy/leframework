package org.le.base.rbac.unique.service.cmd;


import org.apache.ibatis.session.SqlSession;
import org.le.model.command.Command;

/**
 * Created at 2018-08-03 15:31:45
 *
 * @author xiaole
 */
public class DeleteUniqueByUidCmd implements Command<Void> {

    protected Long uid;

    public DeleteUniqueByUidCmd(Long uid) {
        this.uid = uid;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if (uid != null) {
            sqlSession.delete("org.le.base.rbac.unique.deleteByUid", uid);
        }
        return null;
    }

}
