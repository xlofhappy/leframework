package com.qweather.leframework.base.dictionary.service.impl;

import com.qweather.leframework.base.dictionary.service.cmd.ListDictionaryCmd;
import com.qweather.leframework.base.dictionary.service.DictionaryQuery;
import com.qweather.leframework.base.dictionary.service.cmd.CountDictionaryCmd;
import com.qweather.leframework.base.dictionary.service.po.DictionaryEntity;
import com.qweather.leframework.core.interfaces.DictionaryIndex;
import com.qweather.leframework.core.result.Page;
import com.qweather.leframework.model.AbstractQuery;
import com.qweather.leframework.model.OrderBy;
import com.qweather.leframework.model.command.CommandExecutor;

import java.util.List;

/**
 * Created at 2018-08-03 21:43:22
 *
 * @author xiaole
 */
public class DictionaryQueryImpl extends AbstractQuery<DictionaryQuery, DictionaryEntity> implements DictionaryQuery {


    private Long id;
    private Long pid;
    private String code;
    private String value;
    private CommandExecutor commandExecutor;

    public DictionaryQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        ID("A.ID"), TID("A.PID"), CODE("A.CODE"), VALUE("A.VALUE");
        private String name;

        Query(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    @Override
    public DictionaryQuery id(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public DictionaryQuery pid(DictionaryIndex index) {
        this.pid = index.id();
        return this;
    }

    @Override
    public DictionaryQuery code(String code) {
        this.code = code;
        return this;
    }

    @Override
    public DictionaryQuery value(String value) {
        this.value = value;
        return this;
    }

    @Override
    public DictionaryQuery orderById(Direction direction) {
        return orderBy(Query.ID, direction);
    }

    @Override
    public DictionaryQuery orderByCode(Direction direction) {
        return orderBy(Query.CODE, direction);
    }

    @Override
    public DictionaryQuery orderByValue(Direction direction) {
        return orderBy(Query.VALUE, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountDictionaryCmd(this));
    }

    @Override
    protected List<DictionaryEntity> executeList(Page page) {
        if (page != null) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListDictionaryCmd(this));
    }
}
