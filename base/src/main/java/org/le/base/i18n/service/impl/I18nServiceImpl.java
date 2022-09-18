package org.le.base.i18n.service.impl;

import org.le.base.i18n.service.I18nQuery;
import org.le.base.i18n.service.I18nService;
import org.le.base.i18n.service.cmd.DeleteI18nCmd;
import org.le.base.i18n.service.cmd.InsertI18nCmd;
import org.le.base.i18n.service.cmd.UpdateI18nCmd;
import org.le.base.i18n.service.po.I18nEntity;
import org.le.model.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
@Service
public class I18nServiceImpl implements I18nService {

    private final CommandExecutor executor;

    @Autowired
    public I18nServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public I18nQuery createI18nQuery() {
        return new I18nQueryImpl(executor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveI18n(I18nEntity entity) {
        I18nEntity one = this.createI18nQuery().code(entity.getCode()).lang(entity.getLang()).one();
        if ( one == null ) {
            executor.execute(new InsertI18nCmd(entity));
        } else {
            executor.execute(new UpdateI18nCmd(entity));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteI18n(String code, String lang) {
        executor.execute(new DeleteI18nCmd(code, lang));
    }

}
