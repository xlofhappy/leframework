package org.le.core.properties;

/**
 * http properties for le
 * <p>
 * Created at 2019-05-28 12:24:00
 *
 * @author xiaole
 */
public class LePropertyHttp {

    /**
     * if set appDomain request attribute or not
     * eg: https://www.baidu.com
     */
    private boolean appDomain = true;
    /**
     * if set appUri request attribute or not
     * eg: {@link javax.servlet.http.HttpServletRequest#getRequestURI()}
     */
    private boolean appUri = true;

    /**
     * focus to reload the resource
     */
    private String resourceVersion = "_";

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public boolean isAppDomain() {
        return appDomain;
    }

    public void setAppDomain(boolean appDomain) {
        this.appDomain = appDomain;
    }

    public boolean isAppUri() {
        return appUri;
    }

    public void setAppUri(boolean appUri) {
        this.appUri = appUri;
    }
}
