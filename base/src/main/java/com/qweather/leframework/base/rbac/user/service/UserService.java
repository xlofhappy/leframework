package com.qweather.leframework.base.rbac.user.service;

import com.qweather.leframework.base.rbac.unique.service.UniqueType;
import com.qweather.leframework.base.rbac.user.service.po.UserEntity;
import com.qweather.leframework.base.rbac.role.service.po.RoleEntity;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
public interface UserService {

    UserQuery createUserQuery();

    /**
     * User login
     *
     * @param loginId    login id
     * @param loginType  login type {@link UniqueType}
     * @param password   pwd
     * @param userStatus user status, some times, account be activated via different status value
     * @return {@link UserEntity}
     */
    UserEntity login(String loginId, UniqueType loginType, String password, Short userStatus);

    UserEntity login(String loginId, UniqueType loginType, String password);

    UserEntity createUser();

    /**
     * save user, update info
     * if newUser is true, password as origin password, or just update other info
     * {@link #resetPassword(Long, String)}
     *
     * @param entity  user entity
     * @param newUser if new user
     */
    void saveUser(@Nonnull UserEntity entity, boolean newUser);

    void deleteUser(@Nonnull Long id);

    void grantUser(@Nonnull UserEntity user, @Nonnull List<RoleEntity> roleEntities);

    /**
     * update user's status
     *
     * @param id     user id
     * @param status status
     */
    void updateUserStatus(Long id, byte status);

    /**
     * reset user password
     * password = base64( md5( origin's password ) )
     *
     * @param id
     * @param password
     */
    void resetPassword(Long id, String password, String salt);

    void resetPassword(Long id, String password);

    void changeUsername(Long id, String username);

    /**
     * update db origin
     */
    void resetPasswordOrigin(Long id, String password, String salt);

}
