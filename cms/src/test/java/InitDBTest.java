import org.junit.Test;
import org.junit.runner.RunWith;
import com.qweather.leframework.base.rbac.permission.service.PermissionService;
import com.qweather.leframework.base.rbac.role_permission.service.RolePermissionService;
import com.qweather.leframework.cms.CmsApplication;
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
