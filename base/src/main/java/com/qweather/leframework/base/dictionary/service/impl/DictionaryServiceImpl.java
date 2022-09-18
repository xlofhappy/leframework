package com.qweather.leframework.base.dictionary.service.impl;

import com.qweather.leframework.base.dictionary.service.cmd.DeleteDictionaryCmd;
import com.qweather.leframework.base.dictionary.service.cmd.InsertDictionaryCmd;
import com.qweather.leframework.base.common.util.BaseConstant;
import com.qweather.leframework.base.common.util.VoUtil;
import com.qweather.leframework.base.dictionary.service.DictionaryQuery;
import com.qweather.leframework.base.dictionary.service.DictionaryService;
import com.qweather.leframework.base.dictionary.service.cmd.DeleteDictionaryForeverCmd;
import com.qweather.leframework.base.dictionary.service.cmd.UpdateDictionaryCmd;
import com.qweather.leframework.base.dictionary.service.po.DictionaryEntity;
import com.qweather.leframework.core.config.Config;
import com.qweather.leframework.model.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created at 2018-08-03 21:43:22
 *
 * @author xiaole
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final CommandExecutor executor;

    @Autowired
    public DictionaryServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public DictionaryQuery createDictionaryQuery() {
        return new DictionaryQueryImpl(executor);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public DictionaryEntity createDictionary() {
        return new DictionaryEntity(Config.getDaoConfig().getIdPool().getNextId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDictionary(DictionaryEntity entity) {
        if (entity != null && entity.getId() != null && entity.getId() != 0) {
            if (entity.getPid() == null) {
                entity.setPid(BaseConstant.DICTIONARY_ROOT_ID);
            }
            DictionaryEntity one = this.createDictionaryQuery().id(entity.getId()).one();
            if (one == null) {
                VoUtil.setLePoCommonProperty(entity);
                executor.execute(new InsertDictionaryCmd(entity));
            } else {
                one.setValue(entity.getValue());
                one.setCode(entity.getCode());
                one.setRemark(entity.getRemark());
                VoUtil.setLePoCommonProperty(one);
                executor.execute(new UpdateDictionaryCmd(one));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictionary(Long id) {
        DictionaryEntity one = this.createDictionaryQuery().id(id).one();
        if (one != null) {
            deleteDictionary(one);
        }
    }

    private void deleteDictionary(DictionaryEntity entity) {
        if (entity != null) {
            if (entity.getClosestChildren() != null && !entity.getClosestChildren().isEmpty()) {
                for (DictionaryEntity child : entity.getClosestChildren()) {
                    deleteDictionary(child);
                }
            }
            this.executor.execute(new DeleteDictionaryCmd(entity.getId()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictionaryForever(Long id) {
        executor.execute(new DeleteDictionaryForeverCmd(id));
    }

}
