package org.le.base.common.util;

import com.google.common.base.Strings;
import org.le.base.rbac.permission.service.po.PermissionEntity;
import org.le.base.rbac.role.service.po.RoleEntity;
import org.le.base.rbac.user.service.po.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;
import java.util.Map;

/**
 * Auth Tool
 * Created by xiaole on 16/10/11
 *
 * @author xiaole
 */
public class AuthUtil {

    private static final Logger logger = LoggerFactory.getLogger(AuthUtil.class);


    public static boolean check(String waitForAuth, UserEntity user) {
        return check(waitForAuth, user, null);
    }

    /**
     * 检查权限
     *
     * @param waitForAuth string  需要验证的规则列表,支持逗号分隔的权限规则或索引数组 如:  /xl/admin/index,/xl/admin/manage
     * @param user        User         认证用户的 基本信息
     *
     * @return boolean    通过验证返回true | 失败返回false
     */
    public static boolean check(String waitForAuth, UserEntity user, Map<String, Object> expressionVariables) {
        if ( Strings.isNullOrEmpty(waitForAuth) || user == null ) {
            return false;
        }

        List<PermissionEntity> permissionEntities = user.getPermissionEntities();
        if ( permissionEntities != null && permissionEntities.size() > 0 ) {
            for ( PermissionEntity permissionEntity : permissionEntities ) {
                if ( waitForAuth.matches(permissionEntity.getUrl()) ) {
                    try {
                        if ( expressionVariables != null ) {
                            StandardEvaluationContext context = new StandardEvaluationContext();
                            for ( Map.Entry<String, Object> stringObjectEntry : expressionVariables.entrySet() ) {
                                context.setVariable(stringObjectEntry.getKey(), stringObjectEntry.getValue());
                            }
                            ExpressionParser parser = new SpelExpressionParser();
                            Expression       exp    = parser.parseExpression(permissionEntity.getExpression());
                            return exp.getValue(context, Boolean.class);
                        } else {
                            return true;
                        }
                    } catch ( Exception ex ) {
                        logger.error(" Verify permission's expression appear exception.", ex);
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查用户是否有对应角色
     *
     * @param user User         认证用户的 基本信息
     * @param role Role          角色
     *
     * @return boolean           通过验证返回true;失败返回false
     */
    public static boolean hasRole(UserEntity user, RoleEntity role) {
        if ( role == null || user == null || role.getId() == null ) {
            return false;
        }
        List<RoleEntity> roleList = user.getRoleEntities();
        if ( roleList != null ) {
            return roleList.contains(role);
        }
        return false;
    }

    public static boolean hasRole(UserEntity user, Long roleId) {
        return hasRole(user, new RoleEntity(roleId));
    }


}
