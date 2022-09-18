package com.qweather.leframework.base.rbac.user_extend.service;

import com.qweather.leframework.base.rbac.user_extend.service.po.UserExtendEntity;

/**
 * Created at 2018-08-03 15:19:53
 *
 * @author xiaole
 */
public interface UserExtendService {

    UserExtendQuery createUserExtendQuery();

    void addUserExtend(UserExtendEntity entity);

    void deleteUserExtend(Long uid, Integer k);

    void updateUserExtend(Long uid, Integer k, String v);

    void updateUserExtend(UserExtendEntity userExtendEntity);


}
