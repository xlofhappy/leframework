package org.le.base.i18n.service.po;


import java.io.Serializable;

/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
public class I18nEntity implements Serializable {

    /**
     * i18n code, eg: site.menu
     */
    private String code;
    /**
     * i18n language
     */
    private String lang;
    /**
     * i18n content
     */
    private String content;

    public I18nEntity() {
        super();
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}