package org.le.base.variable.service;

import org.le.base.variable.service.po.VariableEntity;

/**
 * Created at 2018-08-09 19:50:19
 *
 * @author xiaole
 */
public interface VariableService {

    VariableQuery createVariableQuery();

    VariableEntity createVariable();

    void saveVariable(VariableEntity entity);

    void deleteVariable(Long id);

    void deleteVariableForever(Long id);

}
