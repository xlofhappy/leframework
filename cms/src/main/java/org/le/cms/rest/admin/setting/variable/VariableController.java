package org.le.cms.rest.admin.setting.variable;

import com.google.common.base.Strings;
import org.le.base.variable.service.VariableQuery;
import org.le.base.variable.service.VariableService;
import org.le.base.variable.service.po.VariableEntity;
import org.le.cms.common.enums.ReturnStatusEnum;
import org.le.cms.rest.admin.BaseAdminController;
import org.le.core.properties.LeProperties;
import org.le.core.result.LeResult;
import org.le.core.result.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * System's variables manage controller
 *
 * @author xiaole
 * @date 2020-08-15 16:06:00
 */
@RestController
@RequestMapping(value = VariableController.PATH)
public class VariableController extends BaseAdminController {

    private final Logger logger = LoggerFactory.getLogger(VariableController.class);

    protected static final String PATH = BaseAdminController.PATH + "/variable";

    private final VariableService variableService;

    @Autowired
    public VariableController(LeProperties leProperties, VariableService variableService) {
        super(leProperties);
        this.variableService = variableService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<List<VariableEntity>> get(@ModelAttribute Page page, String code) {
        try {
            VariableQuery variableQuery = this.variableService.createVariableQuery();
            // add the filter
            long count = variableQuery.count();
            page.setCount(count);
            List<VariableEntity> variableEntities = variableQuery.listPage(page.getSkip(), page.getLimit());
            return LeResult.success(ReturnStatusEnum.OK.getCode(), variableEntities);
        } catch (Exception e) {
            logger.error("get list error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<VariableEntity> view(@PathVariable Long id) {
        try {
            VariableEntity one = this.variableService.createVariableQuery().id(id).one();
            if (one == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            return LeResult.success(ReturnStatusEnum.OK.getCode(), one);
        } catch (Exception e) {
            logger.error("get variable detail error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> update(VariableEntity variable, @PathVariable Long id) {
        try {
            if (Strings.isNullOrEmpty(variable.getCode())) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            VariableEntity one = this.variableService.createVariableQuery().id(id).one();
            if (one == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }

            this.variableService.saveVariable(variable);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("update variable error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> create(VariableEntity variable) {
        try {
            if (Strings.isNullOrEmpty(variable.getCode()) || Strings.isNullOrEmpty(variable.getValue())) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            this.variableService.saveVariable(variable);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("create variable error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @DeleteMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> delete(@PathVariable Long id) {
        try {
            VariableEntity one = this.variableService.createVariableQuery().id(id).one();
            if (one == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            this.variableService.deleteVariable(id);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("delete variable error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }


}
