package com.qweather.leframework.base.rbac.role.service.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qweather.leframework.base.common.util.BaseConstant;
import com.qweather.leframework.base.rbac.permission.service.cmd.GetPermissionByRoleIdCmd;
import com.qweather.leframework.base.rbac.permission.service.po.PermissionEntity;
import com.qweather.leframework.base.rbac.role.service.cmd.ListClosestRoleByPidCmd;
import com.qweather.leframework.base.rbac.role.service.impl.RoleQueryImpl;
import com.qweather.leframework.base.rbac.role.service.cmd.GetRoleByIdCmd;
import com.qweather.leframework.base.rbac.role.service.cmd.ListRoleMutexByIdCmd;
import com.qweather.leframework.model.mybatis.dao.BaseDao;
import com.qweather.leframework.model.po.LePo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class RoleEntity extends LePo {

    /**
     * 父角色ID
     */
    private Long pid;
    /**
     * 多语言字符串
     */
    private String code;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 权限个数限制
     */
    private Integer permissionLimit;
    /**
     * 先决条件角色
     */
    private Long needRoleId;
    /**
     * 角色描述
     */
    private String description;

    @JsonIgnore
    private RoleEntity parent;
    @JsonIgnore
    private List<RoleEntity> children;
    @JsonIgnore
    private List<RoleEntity> closestChildren;
    @JsonIgnore
    private List<PermissionEntity> permissionEntities;
    @JsonIgnore
    private List<RoleEntity> roleMutexEntities;

    @JsonIgnore
    public List<RoleEntity> getClosestChildren() {
        if (closestChildren == null) {
            synchronized (this) {
                if (closestChildren == null) {
                    closestChildren = new ArrayList<>();
                    List<RoleEntity> roleEntities = BaseDao.getInstance().execute(new ListClosestRoleByPidCmd(this.getId()));
                    if (roleEntities != null) {
                        roleEntities.forEach(e -> e.parent = this);
                        closestChildren = roleEntities;
                    }
                }
            }
        }
        return closestChildren;
    }

    @JsonIgnore
    public List<RoleEntity> getRoleMutexEntities() {
        if (roleMutexEntities == null) {
            synchronized (this) {
                if (roleMutexEntities == null) {
                    roleMutexEntities = new ArrayList<>();
                    List<RoleEntity> list = BaseDao.getInstance().execute(new ListRoleMutexByIdCmd(getId()));
                    if (list != null) {
                        for (RoleEntity roleEntity : list) {
                            if (!roleMutexEntities.contains(roleEntity)) {
                                roleMutexEntities.add(roleEntity);
                            }
                        }
                    }
                }
            }
        }
        return roleMutexEntities;
    }

    @JsonIgnore
    public List<RoleEntity> getChildren() {
        if (children == null) {
            synchronized (this) {
                if (children == null) {
                    children = new ArrayList<>();
                    List<RoleEntity> list = new RoleQueryImpl(BaseDao.getInstance()).pid(getId()).list();
                    for (RoleEntity roleEntity : list) {
                        roleEntity.parent = this;
                        if (!children.contains(roleEntity)) {
                            children.add(roleEntity);
                        }
                        if (roleEntity.getChildren() != null) {
                            for (RoleEntity child : roleEntity.getChildren()) {
                                if (!children.contains(child)) {
                                    child.parent = roleEntity;
                                    children.add(child);
                                }
                            }
                        }
                    }
                }
            }
        }
        return children;
    }

    @JsonIgnore
    public RoleEntity getParent() {
        if (parent == null) {
            synchronized (this) {
                if (parent == null) {
                    if (pid != null && pid.equals(getId())) {
                        parent = BaseDao.getInstance().execute(new GetRoleByIdCmd(pid));
                    }
                }
            }
        }
        return parent;
    }

    public void setParent(RoleEntity parent) {
        this.parent = parent;
    }

    @JsonIgnore
    public List<PermissionEntity> getPermissionEntities() {
        if (permissionEntities == null) {
            synchronized (this) {
                if (permissionEntities == null) {
                    permissionEntities = BaseDao.getInstance().execute(new GetPermissionByRoleIdCmd(getId()));
                    permissionEntities = permissionEntities == null ? new ArrayList<>() : permissionEntities;
                    for (RoleEntity child : getChildren()) {
                        List<PermissionEntity> childPermissionEntities = child.getPermissionEntities();
                        for (PermissionEntity childPermissionEntity : childPermissionEntities) {
                            if (!permissionEntities.contains(childPermissionEntity)) {
                                permissionEntities.add(childPermissionEntity);
                            }
                        }

                    }
                }
            }
        }
        return permissionEntities;
    }

    public RoleEntity() {
        super();
    }

    public RoleEntity(Long id) {
        super(id);
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPermissionLimit() {
        return this.permissionLimit;
    }

    public void setPermissionLimit(Integer permissionLimit) {
        this.permissionLimit = permissionLimit;
    }

    public Long getNeedRoleId() {
        return this.needRoleId;
    }

    public void setNeedRoleId(Long needRoleId) {
        this.needRoleId = needRoleId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean belongToAdmin() {
        if (BaseConstant.ADMIN_ROLE_ROOT_ID.equals(getId())) {
            return true;
        } else if (getParent() != null) {
            return getParent().belongToAdmin();

        }
        return false;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof RoleEntity) {
                if (obj == this) {
                    return true;
                }
                return this.getId() != null && this.getId().equals(((RoleEntity) obj).getId());
            }
        }
        return false;
    }
}