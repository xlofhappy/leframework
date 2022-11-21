package com.qweather.leframework.base.variable.service;

import com.qweather.leframework.base.variable.service.po.VariableEntity;
import com.qweather.leframework.core.interfaces.VariableCode;

import javax.annotation.Nonnull;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public interface VariableService {

    VariableQuery createVariableQuery();

    VariableEntity createVariable();

    void saveVariable(VariableEntity entity);

    Long incrVariable(@Nonnull VariableCode variableCode, Long step);

    void deleteVariable(Long id);

    void deleteVariableForever(Long id);

}
