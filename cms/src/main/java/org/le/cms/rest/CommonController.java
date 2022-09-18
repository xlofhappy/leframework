package org.le.cms.rest;

import org.le.cms.common.enums.ReturnStatusEnum;
import org.le.core.result.LeResult;
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
