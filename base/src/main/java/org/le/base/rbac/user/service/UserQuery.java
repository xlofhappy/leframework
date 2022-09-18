package org.le.base.rbac.user.service;

import org.le.base.rbac.unique.service.UniqueType;
import org.le.base.rbac.user.service.po.UserEntity;
import org.le.model.BaseQuery;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
public interface UserQuery extends BaseQuery<UserQuery, UserEntity> {

    UserQuery unionId(String unionId, UniqueType type);

    UserQuery unionIdLike(String unionIdLike, UniqueType type);

    UserQuery unionId(String unionId);

    UserQuery unionIdLike(String unionIdLike);

    UserQuery type(UniqueType type);

    UserQuery nickname(String nickname);

    UserQuery realname(String realname);

    UserQuery phone(String phone);

    UserQuery country(String country);

    UserQuery roleId(Long roleId);

    UserQuery inRoles(Long... roleId);

    UserQuery username(String username);

    UserQuery email(String email);

    UserQuery emailLike(String emailLike);

    UserQuery authorTimeLike(String authorTimeLike);

    UserQuery authorTimeAfter(String authorTime);

    UserQuery idNotEquals(Long id);

    UserQuery orderByEmail(Direction direction);

    UserQuery orderByCountry(Direction direction);


}
