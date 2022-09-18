import org.junit.Test;
import org.junit.runner.RunWith;
import org.le.base.rbac.permission.service.PermissionService;
import org.le.base.rbac.role_permission.service.RolePermissionService;
import org.le.cms.CmsApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmsApplication.class)
public class InitDBTest {

    @Autowired
    private PermissionService     permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;

    @Test
    public void init() {

    }
}
