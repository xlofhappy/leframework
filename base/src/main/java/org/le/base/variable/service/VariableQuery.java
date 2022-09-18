package org.le.base.variable.service;

import org.le.base.variable.service.po.VariableEntity;
import org.le.core.interfaces.VariableCode;
import org.le.model.IdQuery;

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
