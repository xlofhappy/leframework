package com.qweather.leframework.cms.rest.admin;

import com.qweather.leframework.cms.rest.BaseController;
import com.qweather.leframework.core.properties.LeProperties;

/**
 * @author xiaole
 * @date 2020-08-15 14:36:49
 */
public class BaseAdminController extends BaseController {

    protected static final String PATH = BaseController.PATH + "/admin";

    public BaseAdminController(LeProperties leProperties) {
        super(leProperties);
    }
}
