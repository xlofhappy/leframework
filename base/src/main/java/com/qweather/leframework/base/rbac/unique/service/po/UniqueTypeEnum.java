package com.qweather.leframework.base.rbac.unique.service.po;

import com.qweather.leframework.base.rbac.unique.service.UniqueType;

import java.util.Arrays;

/**
 * Type of user's unique-id
 * one user could have more than one way to login, such as: qq、wechat、email ...
 *
 * @author xiaole
 * @date 2018-11-23 12:31:45
 */
public enum UniqueTypeEnum implements UniqueType {
    HE, EMAIL, USERNAME, WECHAT, QQ, SINA_WEIBO, TENCENT_WEIBO, FACEBOOK, GOOGLE, TWITTER, APPLE, PHONE;

    @Override
    public String type() {
        return this.name();
    }

    public static UniqueType parse(String type) {
        return Arrays.stream(UniqueTypeEnum.values()).filter(e -> e.type().equalsIgnoreCase(type)).findFirst().orElse(null);
    }
}