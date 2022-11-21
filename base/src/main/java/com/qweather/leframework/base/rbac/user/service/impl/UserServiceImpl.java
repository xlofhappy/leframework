package com.qweather.leframework.base.rbac.user.service.impl;

import com.google.common.base.Strings;
import com.qweather.leframework.base.common.util.BaseConstant;
import com.qweather.leframework.base.common.util.VoUtil;
import com.qweather.leframework.base.rbac.unique.service.UniqueType;
import com.qweather.leframework.base.rbac.unique.service.cmd.DeleteUniqueByUidCmd;
import com.qweather.leframework.base.rbac.unique.service.cmd.DeleteUniqueCmd;
import com.qweather.leframework.base.rbac.unique.service.cmd.GetUniqueCmd;
import com.qweather.leframework.base.rbac.unique.service.cmd.InsertUniqueCmd;
import com.qweather.leframework.base.rbac.unique.service.po.UniqueEntity;
import com.qweather.leframework.base.rbac.unique.service.po.UniqueTypeEnum;
import com.qweather.leframework.base.rbac.user.service.cmd.*;
import com.qweather.leframework.base.rbac.user_extend.service.cmd.DeleteUserExtendCmd;
import com.qweather.leframework.base.rbac.user_role.service.cmd.DeleteUserRoleCmd;
import com.qweather.leframework.base.rbac.user_role.service.cmd.InsertUserRoleCmd;
import com.qweather.leframework.base.rbac.role.service.po.RoleEntity;
import com.qweather.leframework.base.rbac.user.service.UserQuery;
import com.qweather.leframework.base.rbac.user.service.UserService;
import com.qweather.leframework.base.rbac.user.service.po.UserEntity;
import com.qweather.leframework.core.config.Config;
import com.qweather.leframework.core.util.LeUtil;
import com.qweather.leframework.model.command.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final CommandExecutor executor;

    @Autowired
    public UserServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    public UserQuery createUserQuery() {
        return new UserQueryImpl(executor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity createUser() {
        return new UserEntity(Config.getDaoConfig().getIdPool().getNextId());
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public UserEntity login(String loginId, UniqueType loginType, String password, Short userStatus) {
        GetUniqueCmd getUniqueCmd = new GetUniqueCmd(loginId, loginType);
        UniqueEntity uniqueEntity = this.executor.execute(getUniqueCmd);
        if (uniqueEntity != null) {
            UserEntity userEntity = this.createUserQuery().id(uniqueEntity.getUid()).status(userStatus).one();
            if (userEntity != null && userEntity.verify(password)) {
                return userEntity;
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public UserEntity login(String loginId, UniqueType loginType, String password) {
        return login(loginId, loginType, password, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(@Nonnull UserEntity entity, final boolean newUser) {
        if (entity != null) {
            boolean newEntity = false;
            if (entity.getId() != null && !BaseConstant.SYSTEM_ADMIN_ID.equals(entity.getId())) {
                UserEntity old = createUserQuery().id(entity.getId()).one();
                if (old != null) {
                    if (newUser) {
                        throw new RuntimeException("user " + old.getId() + " exists");
                    }
                    logger.info("update email, new {}, old {}", entity.getEmail(), old.getEmail());

                    if (!Strings.nullToEmpty(old.getEmail()).equalsIgnoreCase(Strings.nullToEmpty(entity.getEmail()))) {
                        if (!Strings.isNullOrEmpty(entity.getEmail())) {
                            // check if new email exists
                            UniqueEntity uniqueEntity = this.executor.execute(new GetUniqueCmd(entity.getEmail(), UniqueTypeEnum.EMAIL));
                            if (uniqueEntity != null) {
                                throw new RuntimeException("update user email error, " + entity.getEmail() + " exists");
                            }
                        }

                        if (!Strings.isNullOrEmpty(old.getEmail())) {
                            // delete old email unique
                            this.executor.execute(new DeleteUniqueCmd(old.getEmail(), UniqueTypeEnum.EMAIL));
                        }

                        // add new email unique
                        UniqueEntity uniqueEntity = new UniqueEntity();
                        uniqueEntity.setUnionId(entity.getEmail());
                        uniqueEntity.setType(UniqueTypeEnum.EMAIL);
                        uniqueEntity.setUid(old.getId());
                        this.executor.execute(new InsertUniqueCmd(uniqueEntity));

                        // change to new email
                        old.setEmail(entity.getEmail());
                    }

                    logger.info("update email, new {}, old {}", entity.getPhone(), old.getPhone());

                    if (!Strings.nullToEmpty(entity.getPhone()).equalsIgnoreCase(old.getPhone())) {
                        if (!Strings.isNullOrEmpty(entity.getPhone())) {
                            // check if new phone exists
                            UniqueEntity uniqueEntity = this.executor.execute(new GetUniqueCmd(entity.getPhone(), UniqueTypeEnum.PHONE));
                            if (uniqueEntity != null) {
                                throw new RuntimeException("update user phone error, " + entity.getPhone() + " exists");
                            }
                        }
                        // delete old phone unique
                        if (!Strings.isNullOrEmpty(old.getPhone())) {
                            this.executor.execute(new DeleteUniqueCmd(old.getPhone(), UniqueTypeEnum.PHONE));
                        }

                        if (!Strings.isNullOrEmpty(entity.getPhone())) {
                            // add new phone unique
                            UniqueEntity uniqueEntity = new UniqueEntity();
                            uniqueEntity.setUnionId(entity.getPhone());
                            uniqueEntity.setType(UniqueTypeEnum.PHONE);
                            uniqueEntity.setUid(old.getId());
                            this.executor.execute(new InsertUniqueCmd(uniqueEntity));

                            logger.info("change phone, old {}, new {}", old.getPhone(), entity.getPhone());

                            // change to new email
                            old.setPhone(entity.getPhone());
                        }
                    }

                    // update other info
                    old.setNickname(entity.getNickname());
                    old.setRealname(entity.getRealname());
                    old.setBirthday(entity.getBirthday());
                    old.setLang(entity.getLang());
                    old.setAvatar(entity.getAvatar());
                    old.setCountry(entity.getCountry());
                    old.setSex(entity.getSex());
                    old.setPhone(entity.getPhone());
                    old.setEmail(entity.getEmail());
                    old.setStatus(entity.getStatus());
                    old.setPasswordMark(entity.isPasswordMark());
                    VoUtil.setLePoCommonProperty(old);
                    this.executor.execute(new UpdateUserCmd(old));
                } else {
                    if (newUser) {
                        newEntity = true;
                    } else {
                        throw new RuntimeException("user not exists, new-user must be true");
                    }
                }
            } else {
                newEntity = true;
            }

            if (newEntity && !BaseConstant.SYSTEM_ADMIN_ID.equals(entity.getId())) {
                String salt = Strings.isNullOrEmpty(entity.getSalt()) ? LeUtil.getUUID().toUpperCase() : entity.getSalt().toUpperCase();
                entity.setSalt(salt);
                if (!Strings.isNullOrEmpty(entity.getPassword())) {
                    String originPassword = entity.getPassword();

                    String dbPassword = entity.dbPassword(originPassword);
                    entity.setPassword(dbPassword);
                } else {
                    entity.setPassword(LeUtil.getUUID().toUpperCase());
                }

                VoUtil.setLePoCommonProperty(entity);
                this.executor.execute(new InsertUserCmd(entity));

                if (!Strings.isNullOrEmpty(entity.getEmail())) {
                    UniqueEntity uniqueIdEntity = new UniqueEntity();
                    uniqueIdEntity.setUid(entity.getId());
                    uniqueIdEntity.setUnionId(entity.getEmail());
                    uniqueIdEntity.setType(UniqueTypeEnum.EMAIL);
                    this.executor.execute(new InsertUniqueCmd(uniqueIdEntity));
                }

                if (!Strings.isNullOrEmpty(entity.getUsername())) {
                    UniqueEntity usernameUniqueEntity = new UniqueEntity();
                    usernameUniqueEntity.setUid(entity.getId());
                    usernameUniqueEntity.setUnionId(entity.getUsername());
                    usernameUniqueEntity.setType(UniqueTypeEnum.USERNAME);
                    this.executor.execute(new InsertUniqueCmd(usernameUniqueEntity));
                }

                if (!Strings.isNullOrEmpty(entity.getPhone())) {
                    UniqueEntity phoneUniqueEntity = new UniqueEntity();
                    phoneUniqueEntity.setUid(entity.getId());
                    phoneUniqueEntity.setUnionId(entity.getPhone());
                    phoneUniqueEntity.setType(UniqueTypeEnum.PHONE);
                    this.executor.execute(new InsertUniqueCmd(phoneUniqueEntity));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(@Nonnull Long id) {
        if (!BaseConstant.SYSTEM_ADMIN_ID.equals(id)) {
            this.executor.execute(new DeleteUserExtendCmd(id));
            this.executor.execute(new DeleteUserRoleCmd(id));
            this.executor.execute(new DeleteUniqueByUidCmd(id));
            this.executor.execute(new DeleteUserCmd(id));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantUser(@Nonnull UserEntity user, @Nonnull List<RoleEntity> roleEntities) {
        if (!BaseConstant.SYSTEM_ADMIN_ID.equals(user.getId())) {
            if (roleEntities.size() > 0) {
                this.executor.execute(new DeleteUserRoleCmd(user.getId()));
                for (RoleEntity roleEntity : roleEntities) {
                    this.executor.execute(new InsertUserRoleCmd(user.getId(), roleEntity.getId()));
                }
            } else {
                this.executor.execute(new DeleteUserRoleCmd(user.getId()));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserStatus(Long userId, byte status) {
        if (userId != null) {
            UserEntity one = this.createUserQuery().id(userId).one();
            if (one != null) {
                this.executor.execute(new UpdateUserStatusCmd(userId, status));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long userId, String password, String salt) {
        if (userId != null && !Strings.isNullOrEmpty(password)) {
            UserEntity one = this.createUserQuery().id(userId).one();
            if (one != null) {
                String dbPassword = one.dbPassword(password);
                this.executor.execute(new ResetUserPasswordCmd(one.getId(), dbPassword, salt));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long id, String password) {
        this.resetPassword(id, password, null);
    }

    @Override
    public void resetPasswordOrigin(Long userId, String password, String salt) {
        if (userId != null && !Strings.isNullOrEmpty(password)) {
            UserEntity one = this.createUserQuery().id(userId).one();
            if (one != null) {
                this.executor.execute(new ResetUserPasswordOriginCmd(one.getId(), password, salt));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeUsername(Long userId, String username) {
        if (userId != null && !Strings.isNullOrEmpty(username)) {
            UserEntity one = this.createUserQuery().id(userId).one();
            if (one != null) {
                this.executor.execute(new ChangeUsernameCmd(one.getId(), username));
            }
        }
    }
}
