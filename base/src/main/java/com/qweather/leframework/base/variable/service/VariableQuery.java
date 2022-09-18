package com.qweather.leframework.base.variable.service;

import com.qweather.leframework.base.variable.service.po.VariableEntity;
import com.qweather.leframework.core.interfaces.VariableCode;
import com.qweather.leframework.model.IdQuery;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public interface VariableQuery extends IdQuery<VariableQuery, VariableEntity> {

    VariableQuery code(VariableCode code);

    VariableQuery value(String value);

    VariableQuery orderByCode(Direction direction);

}
