package org.le.core.util.primitive;

import com.google.common.base.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author xiaole
 * @date 2021-06-26 12:08:43
 */
public class LeLongUtil {

    public static long longValue(Long longObject, long defaultValue) {
        return longObject == null ? defaultValue : longObject;
    }

    public static @Nullable Long parseLong(String s) {
        if (Strings.isNullOrEmpty(s)) {
            return null;
        }
        try {
            return Long.parseLong(s);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static @Nonnull Long parseLong(String s, long defaultValue) {
        Long parseLong = parseLong(s);
        return parseLong == null ? defaultValue : parseLong;
    }
}
