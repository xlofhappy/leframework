package com.qweather.leframework.base.i18n.service.po;


/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
public enum I18nLangEnum {

    ZH_CN("zh_CN"), EN_US("en_US");

    private final String value;

    I18nLangEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}