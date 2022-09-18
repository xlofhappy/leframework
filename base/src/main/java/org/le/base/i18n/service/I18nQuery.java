package org.le.base.i18n.service;

import org.le.base.i18n.service.po.I18nEntity;
import org.le.base.i18n.service.po.I18nLangEnum;
import org.le.model.Query;

/**
 * Created at 2018-08-03 22:15:41
 *
 * @author xiaole
 */
public interface I18nQuery extends Query<I18nQuery, I18nEntity> {

    I18nQuery code(String code);

    I18nQuery codeLike(String code);

    I18nQuery lang(String lang);

    I18nQuery lang(I18nLangEnum lang);

    I18nQuery orderByCode(Direction direction);

    I18nQuery orderByLang(Direction direction);

}
