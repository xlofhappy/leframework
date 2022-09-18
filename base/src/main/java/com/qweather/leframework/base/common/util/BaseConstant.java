package com.qweather.leframework.base.common.util;

import com.qweather.leframework.core.util.Constant;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * Global Constant class
 *
 * @author xiaole
 * @date 2018-09-09 12:23:56
 */
public class BaseConstant extends Constant {

    /**
     * key of User Po in session
     */
    public static String SESSION_USER = "USER";
    public static String SESSION_USER_MENU = "USER_MENU";
    public static String SESSION_USER_ADMIN_MENU = "USER_ADMIN_MENU";

    /**
     * Ant path matcher
     */
    public static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    public static final Long SYSTEM_ADMIN_ID = 0L;
    public static final Long SYSTEM_PERMISSION_ROOT_ID = 0L;
    public static final Long ADMIN_PERMISSION_ROOT_ID = 1L;
    public static final Long USER_PERMISSION_ROOT_ID = 2L;
    public static final Long SYSTEM_ROLE_ROOT_ID = 0L;
    public static final Long ADMIN_ROLE_ROOT_ID = 1L;
    public static final Long DICTIONARY_ROOT_ID = 0L;

}
