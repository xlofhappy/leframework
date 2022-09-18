package org.le.base.file.service.impl;

import org.le.base.file.service.FileQuery;
import org.le.base.file.service.cmd.CountFileCmd;
import org.le.base.file.service.cmd.ListFileCmd;
import org.le.base.file.service.po.FileEntity;
import org.le.core.result.Page;
import org.le.model.AbstractQuery;
import org.le.model.OrderBy;
import org.le.model.command.CommandExecutor;

import java.util.List;

/**
 * Created at 2019-08-15 10:42:38
 *
 * @author xiaole
 */
public class FileQueryImpl extends AbstractQuery<FileQuery, FileEntity> implements FileQuery {


    private Long id;
    private String sourceName;
    private String name;
    private String path;
    private String ext;
    private String data;
    private CommandExecutor commandExecutor;

    public FileQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        ID(" A.ID "), SOURCE_NAME(" A.SOURCE_NAME "), NAME(" A.NAME "), PATH(" A.PATH "), SIZE(" A.SIZE "), EXT(" A.EXT "), DATA(" A.DATA ");
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
    public FileQuery id(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public FileQuery sourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    @Override
    public FileQuery name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public FileQuery path(String path) {
        this.path = path;
        return this;
    }

    @Override
    public FileQuery ext(String ext) {
        this.ext = ext;
        return this;
    }
    @Override
    public FileQuery data(String data) {
        this.data = data;
        return this;
    }


    @Override
    public FileQuery orderById(Direction direction) {
        return orderBy(Query.ID, direction);
    }

    @Override
    public FileQuery orderBySourceName(Direction direction) {
        return orderBy(Query.SOURCE_NAME, direction);
    }

    @Override
    public FileQuery orderByName(Direction direction) {
        return orderBy(Query.NAME, direction);
    }

    @Override
    public FileQuery orderByPath(Direction direction) {
        return orderBy(Query.PATH, direction);
    }

    @Override
    public FileQuery orderBySize(Direction direction) {
        return orderBy(Query.SIZE, direction);
    }

    @Override
    public FileQuery orderByExt(Direction direction) {
        return orderBy(Query.EXT, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountFileCmd(this));
    }

    @Override
    protected List<FileEntity> executeList(Page page) {
        if (page != null) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListFileCmd(this));
    }
}
