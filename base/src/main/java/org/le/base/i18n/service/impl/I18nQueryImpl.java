package org.le.base.i18n.service.impl;

import org.le.base.i18n.service.I18nQuery;
import org.le.base.i18n.service.cmd.CountI18nCmd;
import org.le.base.i18n.service.cmd.ListI18nCmd;
import org.le.base.i18n.service.po.I18nEntity;
import org.le.base.i18n.service.po.I18nLangEnum;
import org.le.model.AbstractQuery;
import org.le.model.command.CommandExecutor;
import org.le.core.result.Page;
import org.le.model.OrderBy;

import java.util.List;

/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
public class I18nQueryImpl extends AbstractQuery<I18nQuery, I18nEntity> implements I18nQuery {

    private String code;
    private String codeLike;
    private String lang;
    private String content;
    private CommandExecutor commandExecutor;

    public I18nQueryImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    enum Query implements OrderBy {
        CODE("A.CODE"), LANG("A.LANG");
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
    public I18nQuery code(String code) {
        this.code = code;
        return this;
    }

    @Override
    public I18nQuery codeLike(String codeLike) {
        this.codeLike = codeLike.replaceAll("%", "/%").replaceAll("_", "/_");
        return this;
    }

    @Override
    public I18nQuery lang(String lang) {
        this.lang = lang;
        return this;
    }

    @Override
    public I18nQuery lang(I18nLangEnum lang) {
        this.lang = lang.getValue();
        return this;
    }

    @Override
    public I18nQuery orderByCode(Direction direction) {
        return orderBy(Query.CODE, direction);
    }

    @Override
    public I18nQuery orderByLang(Direction direction) {
        return orderBy(Query.LANG, direction);
    }

    @Override
    protected long executeCount() {
        return commandExecutor.execute(new CountI18nCmd(this));
    }

    @Override
    protected List<I18nEntity> executeList(Page page) {
        if (page != null) {
            this.setSkip(page.getSkip());
            this.setLimit(page.getLimit());
        }
        return commandExecutor.execute(new ListI18nCmd(this));
    }

    @Override
    public String getOrderByColumns() {
        return " A.CODE ";
    }
}
