package com.qweather.leframework.cms.rest;

import com.qweather.leframework.cms.common.enums.ReturnStatusEnum;
import com.qweather.leframework.core.result.LeResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User defaults login
 *
 * @author xiaole
 * @date 2018-08-14 12:34:22
 */
@RestController
public class CommonController {

    @RequestMapping(value = "/unauthorized")
    public LeResult<Void> loginPage() {
        return LeResult.success(ReturnStatusEnum.PERMISSION_DENIED.getCode());
    }

    @RequestMapping(value = {"", "/"})
    public void homePage() {
    }

}
