package org.le.cms.rest.admin.setting.dictionary;

import org.le.base.dictionary.service.DictionaryService;
import org.le.base.dictionary.service.po.DictionaryEntity;
import org.le.cms.common.enums.ReturnStatusEnum;
import org.le.cms.common.util.CmsConstant;
import org.le.cms.rest.admin.BaseAdminController;
import org.le.core.properties.LeProperties;
import org.le.core.result.LeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Role manage controller
 *
 * @author xiaole
 * @date 2020-08-15 12:00:00
 */
@RestController
@RequestMapping(value = DictionaryController.PATH)
public class DictionaryController extends BaseAdminController {

    private final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    protected static final String PATH = BaseAdminController.PATH + "/dictionary";

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(LeProperties leProperties, DictionaryService dictionaryService) {
        super(leProperties);
        this.dictionaryService = dictionaryService;
    }

    @GetMapping(value = "/pid/{pid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<List<DictionaryEntity>> get(@PathVariable Long pid) {
        try {
            // add pid condition
            List<DictionaryEntity> list = this.dictionaryService.createDictionaryQuery().list();
            return LeResult.success(ReturnStatusEnum.OK.getCode(), list);
        } catch (Exception e) {
            logger.error("get dictionary list error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<DictionaryEntity> getDetail(@PathVariable Long id) {
        try {
            if (CmsConstant.DICTIONARY_ROOT_ID.equals(id)) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            DictionaryEntity one = this.dictionaryService.createDictionaryQuery().id(id).one();
            if (one == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            return LeResult.success(ReturnStatusEnum.OK.getCode(), one);
        } catch (Exception e) {
            logger.error("get dictionary detail error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @PostMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> editSubmit(@PathVariable Long id, DictionaryEntity dictionaryEntity) {
        try {
            DictionaryEntity one = this.dictionaryService.createDictionaryQuery().id(id).one();
            if (one == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            one.setCode(dictionaryEntity.getCode());
            one.setValue(dictionaryEntity.getValue());
            one.setRemark(dictionaryEntity.getRemark());
            this.dictionaryService.saveDictionary(one);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("update dictionary error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @DeleteMapping(value = "/{id}")
    public LeResult<Void> delete(@PathVariable Long id) {
        try {
            DictionaryEntity one = this.dictionaryService.createDictionaryQuery().id(id).one();
            if (one == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            this.dictionaryService.deleteDictionary(id);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("delete dictionary error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }


}
