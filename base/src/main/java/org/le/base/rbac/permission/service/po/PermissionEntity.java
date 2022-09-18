package org.le.base.rbac.permission.service.po;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import org.le.base.common.util.BaseConstant;
import org.le.base.rbac.permission.service.cmd.GetPermissionCmd;
import org.le.base.rbac.permission.service.impl.PermissionQueryImpl;
import org.le.base.rbac.role.service.po.RoleEntity;
import org.le.base.rbac.user.service.po.UserEntity;
import org.le.model.Query;
import org.le.model.mybatis.dao.BaseDao;
import org.le.model.po.LePo;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class PermissionEntity extends LePo {

    /**
     * 父ID
     */
    private Long pid;
    /**
     * 多语言字符串
     */
    private String code;
    /**
     * 操作
     */
    private String operation;
    /**
     * 操作
     */
    private String icon;
    /**
     * 显示路径
     */
    private String route;
    /**
     * 权限路径
     */
    private String url;
    /**
     * 权限路径
     */
    private String param;
    /**
     * 权限表达式,额外的权限限制
     */
    private String expression;
    /**
     * 是否是菜单
     */
    private boolean menu;
    /**
     * 排序
     */
    private int sort;
    /**
     * 父对象信息
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PermissionEntity parent;
    /**
     * 子对象集合
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<PermissionEntity> children;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public PermissionEntity getParent() {
        if (parent == null && pid != null && !BaseConstant.SYSTEM_PERMISSION_ROOT_ID.equals(pid)) {
            synchronized (this) {
                if (parent == null) {
                    parent = BaseDao.getInstance().execute(new GetPermissionCmd(pid));
                }
            }
        }
        return parent;
    }

    public void setParent(PermissionEntity parent) {
        this.parent = parent;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<PermissionEntity> getChildren() {
        if (children == null) {
            synchronized (this) {
                if (children == null) {
                    children = new PermissionQueryImpl(BaseDao.getInstance()).pid(getId()).orderBySort(Query.Direction.ASCENDING).list();
                    if (children == null) {
                        children = new ArrayList<>();
                    } else {
                        children.forEach(e -> e.parent = this);
                    }
                }
            }
        }
        return children;
    }

    public boolean belongToRole(RoleEntity entity) {
        List<PermissionEntity> permissionEntities = entity.getPermissionEntities();
        return permissionEntities.stream().anyMatch(p -> p.equals(this));
    }

    /**
     * 验证权限
     *
     * @param waitForAuth 待验证的权限
     * @param args        表达式参数
     * @return true 验证通过 | false 验证不提供
     */
    @SafeVarargs
    public final boolean verify(String waitForAuth, Map.Entry<String, Object>... args) {
        if (Strings.isNullOrEmpty(waitForAuth) || getUrl() == null) {
            return false;
        }

        if (BaseConstant.PATH_MATCHER.match(getUrl(), waitForAuth)) {
            try {
                if (!Strings.isNullOrEmpty(getExpression())) {
                    StandardEvaluationContext context = new StandardEvaluationContext();
                    for (Map.Entry<String, Object> arg : args) {
                        context.setVariable(arg.getKey(), arg.getValue());
                    }
                    ExpressionParser parser = new SpelExpressionParser();
                    Expression exp = parser.parseExpression(getExpression());
                    return exp.getValue(context, Boolean.class);
                } else {
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public PermissionEntity() {
        super();
    }

    public PermissionEntity(Long id) {
        super(id);
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public boolean isMenu() {
        return menu;
    }

    public void setMenu(boolean menu) {
        this.menu = menu;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    /**
     * filter no access
     *
     * @param userEntity user entity
     */
    public PermissionEntity removeNoAccessNodeCascade(UserEntity userEntity) {
        if (userEntity != null) {
            List<PermissionEntity> permissionEntities = userEntity.getPermissionEntities();
            if (permissionEntities.contains(this)) {
                List<PermissionEntity> children = this.getChildren();
                List<PermissionEntity> temp = new ArrayList<>();
                for (PermissionEntity child : children) {
                    if (permissionEntities.contains(child)) {
                        temp.add(child);
                        child.removeNoAccessNodeCascade(userEntity);
                    }
                }
                this.children = temp;
            } else {
                return null;
            }
        }
        return this;
    }

    public void removeNoAccessMenu(UserEntity userEntity) {
        if (userEntity != null) {
            List<PermissionEntity> permissionEntities = userEntity.getPermissionEntities();
            if (permissionEntities.contains(this) && this.isMenu()) {
                List<PermissionEntity> children = this.getChildren();
                List<PermissionEntity> temp = new ArrayList<>();
                for (PermissionEntity child : children) {
                    if (permissionEntities.contains(child) && child.isMenu()) {
                        child.removeNoAccessMenu(userEntity);
                        temp.add(child);
                    }
                }
                this.children = temp;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof PermissionEntity && this.getId() != null) {
                return this.getId().equals(((PermissionEntity) obj).getId());
            }
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        //While override the equals method, orerride the hashCode method
        //based the id value
        return super.hashCode();
    }
}