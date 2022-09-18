package com.qweather.leframework.base.i18n.service;

import com.qweather.leframework.base.i18n.service.po.I18nEntity;

/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
public interface I18nService {

    I18nQuery createI18nQuery();

    void saveI18n(I18nEntity entity);

    void deleteI18n(String code, String lang);

}
