package org.le.core.util.primitive;

import com.google.common.base.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author xiaole
 * @date 2021-06-26 12:08:43
 */
public class LeDoubleUtil {

    public static double doubleValue(Double doubleObject, double defaultValue) {
        return doubleObject == null ? defaultValue : doubleObject;
    }

    public static @Nullable Double parseDouble(String s) {
        if (Strings.isNullOrEmpty(s)) {
            return null;
        }
        try {
            return Double.parseDouble(s);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static @Nonnull Double parseDouble(String s, double defaultValue) {
        Double parseDouble = parseDouble(s);
        return parseDouble == null ? defaultValue : parseDouble;
    }
}
