package com.qweather.leframework.base.common.i18n;

import com.google.common.base.Strings;
import com.qweather.leframework.base.i18n.service.I18nService;
import com.qweather.leframework.base.i18n.service.po.I18nEntity;
import com.qweather.leframework.model.Query;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CMS message source
 * message from the database of CMS
 * component must be {@link AbstractApplicationContext#MESSAGE_SOURCE_BEAN_NAME}, because
 * when Spring init message source , it likes :
 * <p>
 * if (beanFactory.containsLocalBean(MESSAGE_SOURCE_BEAN_NAME)) {
 * ....
 * } else {
 * // Use empty MessageSource to be able to accept getMessage calls.
 * DelegatingMessageSource dms = new DelegatingMessageSource();
 * ...
 * }
 * <p>
 * or, it will create a message source instance of {@link org.springframework.context.support.DelegatingMessageSource}
 * <p>
 * Created at 2018-10-30 11:43:19
 *
 * @author xiaole
 */
public class LeMessageSource extends AbstractMessageSource {

    private final I18nService i18nService;
    private final ResourceBundleMessageSource springMessageSource;
    /**
     * cache data store
     */
    private final Map<String, Map<String, MessageFormat>> cached = new ConcurrentHashMap<>();
    /**
     * If locale is null or {@link LeMessageSource#getLang} is empty, use the defaults Locale.CHINA
     */
    private final Locale defaultLocale = Locale.CHINA;
    /**
     * Loaded data from database cache duration.
     * When not set, data are cached forever.
     */
    private long cacheSecond = -1;

    public LeMessageSource(I18nService i18nService, MessageSourceProperties properties) {
        this.i18nService = i18nService;
        springMessageSource = new ResourceBundleMessageSource();

        if (StringUtils.hasText(properties.getBasename())) {
            springMessageSource.setBasenames(StringUtils.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(properties.getBasename())));
        }
        if (properties.getEncoding() != null) {
            springMessageSource.setDefaultEncoding(properties.getEncoding().name());
        }
        springMessageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
        Duration cacheDuration = properties.getCacheDuration();
        if (cacheDuration != null) {
            springMessageSource.setCacheMillis(cacheDuration.toMillis());
        }
        springMessageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
        springMessageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
    }

    @Override
    @Nullable
    protected MessageFormat resolveCode(String code, Locale locale) {
        if ( code == null || code.trim().isEmpty() ) {
            return null;
        }

        String message = springMessageSource.getMessage(code, null, locale);
        if (!Strings.isNullOrEmpty(message)) {
            return new MessageFormat(message);
        }

        MessageFormat inCache = find(code, locale);
        if ( inCache != null ) {
            inCache.format(new Object[0]);
            return inCache;
        } else {
            return null;
        }
    }


    private MessageFormat find(String code, Locale locale) {
        String                     lang            = getLang(locale);
        Map<String, MessageFormat> stringStringMap = cached.get(code);
        if ( stringStringMap != null ) {
            MessageFormat s = stringStringMap.get(lang);
            if ( s != null ) {
                s.setLocale(locale);
                return s;
            } else {
                for ( String s1 : stringStringMap.keySet() ) {
                    MessageFormat messageFormat = stringStringMap.get(s1);
                    messageFormat.setLocale(locale);
                    return messageFormat;
                }
            }
        } else {
            List<I18nEntity> i18nEntities = this.i18nService.createI18nQuery().code(code).orderByLang(Query.Direction.ASCENDING).list();
            if (!i18nEntities.isEmpty()) {
                stringStringMap = new ConcurrentHashMap<>(3);
                MessageFormat defaultValue = null;
                for ( I18nEntity i18nEntity : i18nEntities ) {
                    MessageFormat messageFormat = new MessageFormat(i18nEntity.getContent());
                    stringStringMap.putIfAbsent(i18nEntity.getLang(), messageFormat);
                    defaultValue = defaultValue != null ? defaultValue : messageFormat;
                }
                cached.put(code, stringStringMap);
                return stringStringMap.getOrDefault(lang, defaultValue);
            }
        }
        return null;
    }

    private String getLang(Locale locale) {
        String lang = locale == null ? defaultLocale.toString() : locale.toString();
        lang = lang.trim().isEmpty() ? defaultLocale.toString() : lang.trim();
        return lang;
    }

}
