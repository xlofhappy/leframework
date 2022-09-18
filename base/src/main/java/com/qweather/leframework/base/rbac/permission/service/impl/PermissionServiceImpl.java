package com.qweather.leframework.base.rbac.permission.service.impl;

import com.qweather.leframework.base.common.util.BaseConstant;
import com.qweather.leframework.base.common.util.VoUtil;
import com.qweather.leframework.base.rbac.role_permission.service.cmd.DeleteRolePermissionCmd;
import com.qweather.leframework.base.rbac.role_permission.service.cmd.InsertRolePermissionCmd;
import com.qweather.leframework.base.rbac.role_permission.service.impl.RolePermissionQueryImpl;
import com.qweather.leframework.base.rbac.role_permission.service.po.RolePermissionEntity;
import com.qweather.leframework.base.rbac.permission.service.PermissionQuery;
import com.qweather.leframework.base.rbac.permission.service.PermissionService;
import com.qweather.leframework.base.rbac.permission.service.cmd.DeletePermissionCmd;
import com.qweather.leframework.base.rbac.permission.service.cmd.InsertPermissionCmd;
import com.qweather.leframework.base.rbac.permission.service.cmd.UpdatePermissionCmd;
import com.qweather.leframework.base.rbac.permission.service.po.PermissionEntity;
import com.qweather.leframework.base.rbac.user.service.po.UserEntity;
import com.qweather.leframework.core.config.Config;
import com.qweather.leframework.model.Query;
import com.qweather.leframework.model.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private final CommandExecutor executor;

    @Autowired
    public PermissionServiceImpl(CommandExecutor commandExecutor) {
        this.executor = commandExecutor;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PermissionQuery createPermissionQuery() {
        return new PermissionQueryImpl(executor);
    }

    @Override
    public PermissionEntity createPermission() {
        return new PermissionEntity(Config.getDaoConfig().getIdPool().getNextId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPermission(PermissionEntity entity) {
        this.executor.execute(new InsertPermissionCmd(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePermission(PermissionEntity entity) {
        if (entity != null) {
            boolean newEntity = false;
            if (entity.getId() != null) {
                PermissionEntity one = this.createPermissionQuery().id(entity.getId()).one();
                if (one != null) {
                    one.setCode(entity.getCode());
                    one.setOperation(entity.getOperation());
                    one.setUrl(entity.getUrl());
                    one.setRoute(entity.getRoute());
                    one.setParam(entity.getParam());
                    one.setExpression(entity.getExpression());
                    one.setMenu(entity.isMenu());
                    one.setSort(entity.getSort());
                    one.setRemark(entity.getRemark());
                    one.setIcon(entity.getIcon());
                    VoUtil.setLePoCommonProperty(one);
                    this.executor.execute(new UpdatePermissionCmd(one));
                } else {
                    newEntity = true;
                }
            } else {
                newEntity = true;
            }
            if (newEntity) {
                VoUtil.setLePoCommonProperty(entity);
                this.executor.execute(new InsertPermissionCmd(entity));
                this.executor.execute(new InsertRolePermissionCmd(new RolePermissionEntity(BaseConstant.SYSTEM_ROLE_ROOT_ID, entity.getId())));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePermission(Long id) {
        RolePermissionQueryImpl rolePermissionQuery = new RolePermissionQueryImpl(this.executor);

        List<RolePermissionEntity> list = rolePermissionQuery.permissionId(id).list();
        if (list != null && !list.isEmpty()) {
            for (RolePermissionEntity entity : list) {
                this.executor.execute(new DeleteRolePermissionCmd(entity.getRoleId(), entity.getPermissionId()));
            }
        }
        this.executor.execute(new DeletePermissionCmd(id));

    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public @Nonnull List<PermissionEntity> listMenuByUser(UserEntity userEntity, Long root) {
        if (userEntity == null) {
            return null;
        }
        List<PermissionEntity> list = this.createPermissionQuery().pid(root).isMenu(true).orderBySort(Query.Direction.ASCENDING).list();
        return checkMenu(list, userEntity);
    }

    /**
     * 1. check if the user has access to the permission
     * 2. check if the permission is a menu
     *
     * @param menuEntities menu list
     * @param userEntity   all permissions
     * @return the menu node list
     */
    private List<PermissionEntity> checkMenu(@NonNull final List<PermissionEntity> menuEntities,
                                             @NonNull final UserEntity userEntity) {
        return menuEntities.stream().map(e -> {
            e.removeNoAccessMenu(userEntity);
            if (userEntity.hasPermission(e)) {
                return e;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
