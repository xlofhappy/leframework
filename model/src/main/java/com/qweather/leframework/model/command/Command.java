package com.qweather.leframework.model.command;

import org.apache.ibatis.session.SqlSession;

/**
 * @author xiaole
 */
@FunctionalInterface
public interface Command<T> {

    /**
     * send to executor
     *
     * @param sqlSession dao executor
     *
     * @return <T>
     */
    T execute(SqlSession sqlSession);
}
