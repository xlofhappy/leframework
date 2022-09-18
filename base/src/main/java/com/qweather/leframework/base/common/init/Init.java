package com.qweather.leframework.base.common.init;

import com.qweather.leframework.base.common.config.Global;
import com.qweather.leframework.base.i18n.service.I18nService;
import com.qweather.leframework.base.i18n.service.po.I18nEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;


/**
 * Created at 2018-08-04 09:36:45
 *
 * @author xiaole
 */
@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {

    private final I18nService i18nService;

    @Autowired
    public Init(I18nService i18nService) {
        this.i18nService = i18nService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            List<I18nEntity> list = this.i18nService.createI18nQuery().list();
            if (list != null) {
                if (Global.LOCALE_MAP == null) {
                    Global.LOCALE_MAP = new HashMap<>(16);
                } else {
                    Global.LOCALE_MAP.clear();
                }
                for (I18nEntity entity : list) {
                    Global.LOCALE_MAP.put(entity.getCode() + "_" + entity.getLang(), entity.getContent());
                }
            }
        }
    }
}
