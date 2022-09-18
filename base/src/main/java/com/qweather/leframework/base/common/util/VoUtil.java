package com.qweather.leframework.base.common.util;

import com.qweather.leframework.base.rbac.user.service.po.UserEntity;
import com.qweather.leframework.core.config.Config;
import com.qweather.leframework.core.constant.DateTimeFormatConstant;
import com.qweather.leframework.model.po.LePo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

/**
 * @author xiaole
 * @see PoUtil
 */
@Deprecated
public class VoUtil {

    public static void setLePoCommonProperty(LePo po) {
        try {
            boolean update = po.getId() != null && po.getAuthorId() != null;

            String nowStr = ZonedDateTime.now().withZoneSameInstant(Config.getLeProperties().getZoneOffset()).format(DateTimeFormatConstant.yyyy_MM_dd_HH_mm_ss);

            UserEntity user = null;
            try {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                user = (UserEntity) request.getSession().getAttribute(BaseConstant.SESSION_USER);
            } catch ( Exception ignored ) {}

            if ( user == null ) {
                user = new UserEntity();
                user.setId(0L);
                user.setUsername("Auto");
            }
            if ( update ) {
                po.setModifyerId(user.getId() == null ? 0L : user.getId());
                po.setModifyTime(nowStr);
            } else {
                po.setAuthorId(user.getId() == null ? 0L : user.getId());
                po.setAuthorTime(nowStr);
            }
        } catch ( Exception ignored ) {}
    }

    /**
     * 清空 pojo对象中的公用属性
     */
    public static void cleanCommonProperty(LePo po) {
        po.setAuthorId(null);
        po.setAuthorTime(null);
        po.setModifyerId(null);
        po.setModifyTime(null);
    }

    /**
     * po 与 dto 之间数据互相拷贝
     *
     * @param target 目标对象
     * @param source 源对象
     */
    public static void copyProperties(Object target, Object source) {
        try {
            BeanUtils.copyProperties(source, target);
        } catch ( Exception ignored ) {}
    }

    /**
     * 复制 pojo对象中的公用属性
     *
     * @param target 目标对象
     * @param source 源对象
     */
    public static void copyCommonProperty(Object target, Object source) {
        LePo po = new LePo();
        copyProperties(po, source);
        copyProperties(target, po);
    }

}
