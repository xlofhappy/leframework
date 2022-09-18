package org.le.cms.common.enums;

/**
 * @author xiaole
 * @date 2020-08-15 11:11:57
 */
public enum ReturnStatusEnum {
    /**
     * ok
     */
    OK("200"),
    PERMISSION_DENIED("201"),
    PARAMETER_INVALID("202"),
    EMAIL_EXISTS("203"),
    VERIFY_CODE_ERROR("204"),
    LOGIN_FAILED("205"),
    LOGGED("206"),
    INTERNAL_ERROR("999");

    String code;

    ReturnStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
