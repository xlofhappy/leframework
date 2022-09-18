package org.le.core.util.primitive;

import com.google.common.base.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author xiaole
 * @date 2021-06-26 12:08:43
 */
public class LeIntUtil {

    public static int intValue(Integer integer, int defaultValue) {
        return integer == null ? defaultValue : integer;
    }

    public static @Nullable Integer parseInt(String s) {
        if (Strings.isNullOrEmpty(s)) {
            return null;
        }
        try {
            return Integer.parseInt(s);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static @Nonnull Integer parseInt(String s, int defaultValue) {
        Integer integer = parseInt(s);
        return integer == null ? defaultValue : integer;
    }
}
