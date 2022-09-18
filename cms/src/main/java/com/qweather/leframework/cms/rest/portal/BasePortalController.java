package com.qweather.leframework.cms.rest.portal;

import com.qweather.leframework.cms.rest.BaseController;
import com.qweather.leframework.core.properties.LeProperties;

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
