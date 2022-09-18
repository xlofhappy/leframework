package com.qweather.leframework.base.rbac.user_extend.service.cmd;

import org.apache.ibatis.session.SqlSession;
import com.qweather.leframework.base.rbac.user_extend.service.po.UserExtendEntity;
import com.qweather.leframework.model.command.Command;

/**
 * @author dongjunchen
 * @date 2020/11/21 2:28 下午
 */
public class UpdateValueByUserIdAndKCmd implements Command<Void> {

    protected UserExtendEntity userExtendEntity;

    public UpdateValueByUserIdAndKCmd(Long uid, Integer k, String v) {
        this.userExtendEntity = new UserExtendEntity(uid, k, v);
    }

    public UpdateValueByUserIdAndKCmd(UserExtendEntity userExtendEntity) {
        this.userExtendEntity = userExtendEntity;
    }

    @Override
    public Void execute(SqlSession sqlSession) {
        if (userExtendEntity != null) {
            sqlSession.update("org.le.base.rbac.user_extend.updateUserExtend", userExtendEntity);
        }
        return null;
    }
}
