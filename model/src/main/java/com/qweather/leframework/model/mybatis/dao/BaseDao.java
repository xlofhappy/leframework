package com.qweather.leframework.model.mybatis.dao;

import com.qweather.leframework.model.command.Command;
import com.qweather.leframework.model.command.CommandExecutor;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author xiaole
 */
@Repository
public class BaseDao extends SqlSessionDaoSupport implements CommandExecutor {

    private static CommandExecutor instance;

    public static CommandExecutor getInstance() {
        return instance;
    }

    /**
     * This setting and spring xml setting means mybatis's session managed by spring,
     * if this under a transactional, mybatis session will be closed by spring and also the connection of database.
     *
     * @param sqlSessionTemplate mybatis session template
     */
    @Autowired
    @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
        instance = this;
    }

    @Override
    public <T> T execute(Command<T> cmd) {
        return cmd.execute(super.getSqlSession());
    }

}
