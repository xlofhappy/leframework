package org.le.cms.common.util;

import org.le.base.common.util.BaseConstant;

/**
 * CMS Constant Variable
 *
 * @author xiaole
 * @date 2018-10-30 16:41:39
 */
public class CmsConstant extends BaseConstant {

    /**
     * verify code in session
     */
    public static final String VERIFY_CODE = "VERIFY_CODE";
    /**
     * cms next url or redirect url or return url 's key
     */
    public static final String RETURN_URL_KEY = "next";
    /**
     * request for page, the message key for view
     * when error, set something to user on the view which is the message's key in session or request
     * eg : request.setAttribute(PAGE_REQUEST_MESSAGE_KEY, "Login info is error!");
     */
    public static final String PAGE_REQUEST_MESSAGE_KEY = "message";

}
