package org.le.cms.rest;

import org.le.cms.common.interfaces.VerifyCodeGenerator;
import org.le.cms.common.util.CmsConstant;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created at 2018-08-14 12:34:22
 *
 * @author xiaole
 */
@RestController
public class VerifyCodeController {

    private final VerifyCodeGenerator verifyCodeGenerator;

    public VerifyCodeController(VerifyCodeGenerator verifyCodeGenerator) {
        this.verifyCodeGenerator = verifyCodeGenerator;
    }

    @GetMapping(value = "/verify", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] imageCode(HttpSession session, int w, int h, int l) {
        try {
            l = l < 4 ? 4 : Math.min(l, 10);
            VerifyCodeGenerator.Code code = this.verifyCodeGenerator.generate(w, h, l);
            session.setAttribute(CmsConstant.VERIFY_CODE, code.getCode());
            return code.getOut().toByteArray();
        } catch (Exception ignored) {
        }
        return null;
    }

}
