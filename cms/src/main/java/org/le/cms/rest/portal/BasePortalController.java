package org.le.cms.rest.portal;

import org.le.cms.rest.BaseController;
import org.le.core.properties.LeProperties;

/**
 * @author xiaole
 * @date 2020-08-15 14:41:13
 */
public class BasePortalController extends BaseController {

    static final String PATH = BaseController.PATH + "/portal";

    public BasePortalController(LeProperties leProperties) {
        super(leProperties);
    }
}
