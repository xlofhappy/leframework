package com.qweather.leframework.base.rbac.user.service.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qweather.leframework.base.rbac.permission.service.po.PermissionEntity;
import com.qweather.leframework.base.rbac.user_extend.service.impl.UserExtendQueryImpl;
import com.qweather.leframework.base.rbac.user_extend.service.po.UserExtendEntity;
import org.apache.commons.codec.digest.DigestUtils;
import com.qweather.leframework.base.rbac.role.service.Role;
import com.qweather.leframework.base.rbac.role.service.cmd.ListRoleByUserIdCmd;
import com.qweather.leframework.base.rbac.role.service.po.RoleEntity;
import com.qweather.leframework.model.BasePo;
import com.qweather.leframework.model.mybatis.dao.BaseDao;
import com.qweather.leframework.model.po.LePo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created at 2018-08-03 15:33:41
 *
 * @author xiaole
 */
public class UserEntity extends LePo {

    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 年龄
     */
    private Integer sex;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 国家
     */
    private String country;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 偏好语言
     */
    private String lang;
    /**
     * 密码标记
     * true: 没有设置过密码，即使有也是系统随机的
     * false: 设置过密码，只能修改或找回密码
     */
    private boolean passwordMark = true;
    /**
     * 角色列表
     */
    @JsonIgnore
    private List<RoleEntity> roleEntities;
    @JsonIgnore
    private List<PermissionEntity> permissionEntities;
    @JsonIgnore
    private List<UserExtendEntity> userExtendEntities;

    @JsonIgnore
    public List<PermissionEntity> getPermissionEntities() {
        if (permissionEntities == null) {
            synchronized (this) {
                if (permissionEntities == null) {
                    if (getRoleEntities().size() > 0) {
                        permissionEntities = new ArrayList<>();
                        for (RoleEntity roleEntity : getRoleEntities()) {
                            for (PermissionEntity permissionEntity : roleEntity.getPermissionEntities()) {
                                if (!permissionEntities.contains(permissionEntity)) {
                                    permissionEntities.add(permissionEntity);
                                }
                            }
                        }
                    } else {
                        permissionEntities = Collections.emptyList();
                    }
                }
            }
        }
        return permissionEntities;
    }

    public void setPermissionEntities(List<PermissionEntity> permissionEntities) {
        this.permissionEntities = permissionEntities;
    }

    @JsonIgnore
    public List<RoleEntity> getRoleEntities() {
        if (roleEntities == null) {
            synchronized (this) {
                if (roleEntities == null) {
                    List<RoleEntity> roleEntities = BaseDao.getInstance().execute(new ListRoleByUserIdCmd(this.getId()));
                    this.roleEntities = new ArrayList<>(roleEntities.size());
                    for (RoleEntity roleEntity : roleEntities) {
                        if (!this.roleEntities.contains(roleEntity)) {
                            this.roleEntities.add(roleEntity);
                        }
                    }
                }
            }
        }
        return roleEntities;
    }

    public void setRoleEntities(List<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }

    @JsonIgnore
    public List<RoleEntity> getRoleEntitiesCascade() {
        if (roleEntities != null) {
            return roleEntities;
        }
        synchronized (this) {
            if (roleEntities == null) {
                List<RoleEntity> roleEntities = BaseDao.getInstance().execute(new ListRoleByUserIdCmd(this.getId()));
                this.roleEntities = new ArrayList<>(roleEntities.size());
                for (RoleEntity roleEntity : roleEntities) {
                    if (!this.roleEntities.contains(roleEntity)) {
                        this.roleEntities.add(roleEntity);
                    }
                    List<RoleEntity> children = roleEntity.getChildren();
                    for (RoleEntity child : children) {
                        if (!this.roleEntities.contains(child)) {
                            this.roleEntities.add(child);
                        }
                    }
                }
            }
        }
        return roleEntities;
    }

    /**
     * 拥有此角色或间接拥有此角色
     */
    @JsonIgnore
    public boolean hasRole(Role role) {
        return this.getRoleEntitiesCascade().stream().map(BasePo::getId).anyMatch(e -> e == role.id());
    }
    /**
     * 拥有此角色或间接拥有此角色
     */
    @JsonIgnore
    public boolean hasRole(long roleId) {
        return this.getRoleEntitiesCascade().stream().map(BasePo::getId).anyMatch(e -> e == roleId);
    }

    @JsonIgnore
    public boolean hasPermission(PermissionEntity permission) {
        return permission != null && this.getRoleEntities().stream().anyMatch(permission::belongToRole);
    }

    public UserEntity() {
        super();
    }

    public UserEntity(Long id) {
        super(id);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return this.realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean verify(String originPassword) {
        return dbPassword(originPassword).equalsIgnoreCase(getPassword());
    }

    @JsonIgnore
    public String dbPassword(String originPassword) {
        String salt = getSalt();
        return DigestUtils.sha256Hex(salt + originPassword).toUpperCase();
    }

    @JsonIgnore
    public boolean isAdmin() {
        List<RoleEntity> roleEntities = getRoleEntities();
        return roleEntities != null && roleEntities.stream().anyMatch(RoleEntity::belongToAdmin);
    }

    public UserExtendEntity getUserExtendEntity(int k) {
        return getUserExtendEntities().stream().filter(item -> item.getK() == k).findFirst().orElse(null);
    }

    public String getUserExtendValue(int k) {
        return getUserExtendEntities().stream().filter(item -> item.getK() == k).findFirst().map(UserExtendEntity::getV).orElse(null);
    }

    @JsonIgnore
    public List<UserExtendEntity> getUserExtendEntities() {
        if (userExtendEntities == null) {
            synchronized (this) {
                if (userExtendEntities == null) {
                    userExtendEntities = new UserExtendQueryImpl(BaseDao.getInstance()).uid(getId()).list();
                }
            }
        }
        return userExtendEntities;
    }

    public boolean equalsExtendValue(int k, String value) {
        UserExtendEntity userExtend = getUserExtendEntity(k);
        if (userExtend == null || userExtend.getV() == null) {
            return false;
        } else {
            return userExtend.getV().equals(value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass() && super.equals(o);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean isPasswordMark() {
        return passwordMark;
    }

    public void setPasswordMark(boolean passwordMark) {
        this.passwordMark = passwordMark;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}