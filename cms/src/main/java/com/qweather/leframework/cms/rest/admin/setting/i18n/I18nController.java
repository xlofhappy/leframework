package com.qweather.leframework.cms.rest.admin.setting.i18n;

import com.google.common.base.Strings;
import com.qweather.leframework.base.i18n.service.I18nQuery;
import com.qweather.leframework.base.i18n.service.I18nService;
import com.qweather.leframework.base.i18n.service.po.I18nEntity;
import com.qweather.leframework.cms.common.enums.ReturnStatusEnum;
import com.qweather.leframework.cms.rest.admin.BaseAdminController;
import com.qweather.leframework.core.properties.LeProperties;
import com.qweather.leframework.core.result.LeResult;
import com.qweather.leframework.core.result.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * People manage controller
 *
 * @author xiaole
 * @date 2020-08-15 16:06:00
 */
@RestController
@RequestMapping(value = I18nController.PATH)
public class I18nController extends BaseAdminController {

    private final Logger logger = LoggerFactory.getLogger(I18nController.class);

    protected static final String PATH = BaseAdminController.PATH + "/i18n";

    private final I18nService i18nService;

    @Autowired
    public I18nController(LeProperties leProperties, I18nService i18nService) {
        super(leProperties);
        this.i18nService = i18nService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<List<I18nEntity>> get(Page page, String code, String lang) {
        try {
            I18nQuery i18nQuery = this.i18nService.createI18nQuery();
            if (!Strings.isNullOrEmpty(code)) {
                i18nQuery = i18nQuery.codeLike(code);
            }
            if (!Strings.isNullOrEmpty(lang)) {
                i18nQuery = i18nQuery.lang(lang);
            }
            long count = i18nQuery.count();
            page.setCount(count);
            List<I18nEntity> i18nEntities = i18nQuery.listPage(page.getSkip(), page.getLimit());
            return LeResult.success(ReturnStatusEnum.OK.getCode(), i18nEntities);
        } catch (Exception e) {
            logger.error("get i18n list error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> update(I18nEntity i18n) {
        try {
            if (Strings.isNullOrEmpty(i18n.getCode()) || Strings.isNullOrEmpty(i18n.getLang()) || Strings.isNullOrEmpty(i18n.getContent())) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            this.i18nService.saveI18n(i18n);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("update i18n error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @DeleteMapping(value = "/code/{code}/lang/{lang}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> delete(@PathVariable String code, @PathVariable String lang) {
        try {
            this.i18nService.deleteI18n(code, lang);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("delete i18n error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }


}
