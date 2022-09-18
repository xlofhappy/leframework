package com.qweather.leframework.core.util.primitive;

import com.google.common.base.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author xiaole
 * @date 2021-06-26 12:08:43
 */
public class LeFloatUtil {

    public static float floatValue(Float floatValue, float defaultValue) {
        return floatValue == null ? defaultValue : floatValue;
    }

    public static @Nullable Float parseFloat(String s) {
        if (Strings.isNullOrEmpty(s)) {
            return null;
        }
        try {
            return Float.parseFloat(s);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static @Nonnull Float parseFloat(String s, float defaultValue) {
        Float parseFloat = parseFloat(s);
        return parseFloat == null ? defaultValue : parseFloat;
    }
}
