package com.qweather.leframework.base.rbac.user_extend.service;

import com.qweather.leframework.model.Query;
import com.qweather.leframework.base.rbac.user_extend.service.po.UserExtendEntity;

/**
 * Created at 2018-08-03 15:19:53
 *
 * @author xiaole
 */
public interface UserExtendQuery extends Query<UserExtendQuery, UserExtendEntity> {

    UserExtendQuery uid(Long uid);

    UserExtendQuery k(UserExtend k);

    UserExtendQuery v(String v);

    UserExtendQuery orderByUid(Direction direction);

    UserExtendQuery orderByK(Direction direction);

}
