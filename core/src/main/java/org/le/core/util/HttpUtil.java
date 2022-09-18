package org.le.core.util;

import com.google.common.base.Strings;
import com.google.common.collect.Multimap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaole
 * @since 15/11/4.
 */
public class HttpUtil {

    private static PoolingHttpClientConnectionManager cm;
    private static IdleConnectionMonitorThread monitor;

    private HttpRequestBase request;
    private HttpClientBuilder clientBuilder;
    private CookieStore cookieStore;
    /**
     * 请求的相关配置
     */
    private RequestConfig.Builder configBuilder;

    public static PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
        if (cm == null) {
            /// 如果需要屏蔽SSL的验证，则需要放开以下代码
            // ConnectionSocketFactory           csf      = PlainConnectionSocketFactory.getSocketFactory();
            // LayeredConnectionSocketFactory    lsf      = createSSLConnSocketFactory();
            // Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", csf).register("https", lsf).build();
            cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(200);
            cm.setDefaultMaxPerRoute(20);
            SocketConfig config = SocketConfig.custom().setSoTimeout(10000).build();
            cm.setDefaultSocketConfig(config);

            monitor = new IdleConnectionMonitorThread(cm);
            monitor.start();
        }
        return cm;
    }

    private HttpUtil() {
        this.clientBuilder = HttpClients.custom().setConnectionManager(getHttpClientConnectionManager());
        this.configBuilder = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(10000).setCookieSpec(CookieSpecs.STANDARD);
        this.cookieStore = new BasicCookieStore();
    }

    private static List<NameValuePair> convertParameters(Multimap<String, String> params) {
        List<NameValuePair> pairs = new ArrayList<>(params.size());
        for (Entry<String, String> entry : params.entries()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return pairs;
    }

    public static HttpUtil newInstance() {
        return new HttpUtil();
    }

    public static HttpUtil getHttp(String url, Multimap<String, String> paramMap, Map<String, String> headerMap) {
        HttpUtil   httpUtil   = newInstance();
        URIBuilder uriBuilder = new URIBuilder(URI.create(url));
        uriBuilder.setCharset(StandardCharsets.UTF_8);
        if ( paramMap != null ) {
            List<NameValuePair> pairs = convertParameters(paramMap);
            uriBuilder.setParameters(pairs);
        }
        HttpGet request = new HttpGet(uriBuilder.toString());
        if ( headerMap != null && headerMap.size() > 0 ) {
            for ( Map.Entry<String, String> param : headerMap.entrySet() ) {
                request.addHeader(param.getKey(), param.getValue());
            }
        }

        httpUtil.request = request;
        return httpUtil;
    }

    public static String get(String url) throws IOException {
        HttpUtil http = getHttp(url, null, null);
        return http.execute().getString();
    }

    public static String get(String url, Multimap<String, String> paramMap) throws IOException {
        HttpUtil http = getHttp(url, paramMap, null);
        return http.execute().getString();
    }

    public static String get(String url, Multimap<String, String> paramMap, Map<String, String> headerMap) throws IOException {
        HttpUtil httputil = getHttp(url, paramMap, headerMap);
        return httputil.execute().getString();
    }

    public static HttpUtil postHttp(String url, Multimap<String, String> paramMap, Map<String, File> fileMap,
                                    Map<String, String> headerMap) {
        HttpUtil httpUtil = newInstance();
        HttpPost request  = new HttpPost(url);

        if ( fileMap != null && fileMap.size() > 0 ) {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setCharset(StandardCharsets.UTF_8).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            for ( Map.Entry<String, File> param : fileMap.entrySet() ) {
                multipartEntityBuilder.addBinaryBody(param.getKey(), param.getValue());
            }

            if ( paramMap != null && paramMap.size() > 0 ) {
                for ( Entry<String, String> param : paramMap.entries() ) {
                    multipartEntityBuilder.addTextBody(param.getKey(), param.getValue(), ContentType.WILDCARD.withCharset(StandardCharsets.UTF_8));
                }
            }
            request.setEntity(multipartEntityBuilder.build());
        } else {
            EntityBuilder entityBuilder = EntityBuilder.create().setContentType(ContentType.APPLICATION_FORM_URLENCODED.withCharset(StandardCharsets.UTF_8));
            if ( paramMap != null && paramMap.size() > 0 ) {
                List<NameValuePair> nameValuePairs = convertParameters(paramMap);
                entityBuilder.setParameters(nameValuePairs);
            }
            request.setEntity(entityBuilder.build());
        }

        if ( headerMap != null ) {
            for ( Map.Entry<String, String> param : headerMap.entrySet() ) {
                request.addHeader(param.getKey(), param.getValue());
            }
        }
        httpUtil.request = request;
        return httpUtil;
    }

    public static HttpUtil postJsonHttp(String url, String jsonParam, Map<String, String> headerMap) {
        HttpUtil httpUtil = newInstance();
        HttpPost request  = new HttpPost(url);

        if ( !Strings.isNullOrEmpty(jsonParam) ) {
            HttpEntity entity = EntityBuilder.create().setText(jsonParam).setContentType(ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8)).setContentEncoding(StandardCharsets.UTF_8.name()).build();
            request.setEntity(entity);
        }

        if ( headerMap != null ) {
            for ( Map.Entry<String, String> param : headerMap.entrySet() ) {
                request.addHeader(param.getKey(), param.getValue());
            }
        }
        httpUtil.request = request;
        return httpUtil;
    }

    public static HttpUtil postXmlHttp(String url, String xmlParam, Map<String, String> headerMap) {
        HttpUtil httpUtil = newInstance();
        HttpPost request  = new HttpPost(url);

        if ( !Strings.isNullOrEmpty(xmlParam) ) {
            HttpEntity entity = EntityBuilder.create().setText(xmlParam).setContentType(ContentType.TEXT_XML.withCharset(StandardCharsets.UTF_8)).setContentEncoding(StandardCharsets.UTF_8.name()).build();
            request.setEntity(entity);
        }

        if ( headerMap != null ) {
            for ( Map.Entry<String, String> param : headerMap.entrySet() ) {
                request.addHeader(param.getKey(), param.getValue());
            }
        }
        httpUtil.request = request;
        return httpUtil;
    }

    public static String post(String url) throws IOException {
        HttpUtil httputil = postHttp(url, null, null, null);
        return httputil.execute().getString();
    }

    public static String post(String url, Multimap<String, String> map) throws IOException {
        HttpUtil httputil = postHttp(url, map, null, null);
        return httputil.execute().getString();
    }

    public static String post(String url, Multimap<String, String> paramMap, Map<String, String> headerMap) throws IOException {
        HttpUtil httputil = postHttp(url, paramMap, null, headerMap);
        return httputil.execute().getString();
    }

    public static String postFile(String url, Map<String, File> fileMap) throws IOException {
        HttpUtil httputil = postHttp(url, null, fileMap, null);
        return httputil.execute().getString();
    }

    public static String postFile(String url, File file) throws IOException {
        Map<String, File> fileMap = new HashMap<>(1);
        fileMap.put(file.getName(), file);
        HttpUtil httputil = postHttp(url, null, fileMap, null);
        return httputil.execute().getString();
    }

    public static String postFile(String url, Multimap<String, String> paramMap, Map<String, File> fileMap) throws IOException {
        HttpUtil httputil = postHttp(url, paramMap, fileMap, null);
        return httputil.execute().getString();
    }

    public static String postFile(String url, Multimap<String, String> paramMap, File file) throws IOException {
        Map<String, File> fileMap = new HashMap<>(1);
        fileMap.put(file.getName(), file);
        HttpUtil httputil = postHttp(url, paramMap, fileMap, null);
        return httputil.execute().getString();
    }

    public static String postFile(String url, Multimap<String, String> paramMap, List<File> fileList) throws IOException {
        Map<String, File> fileMap = new HashMap<>(fileList.size());
        for ( int i = 0, length = fileList.size(); i < length; i++ ) {
            fileMap.put(fileList.get(i).getName(), fileList.get(i));
        }
        HttpUtil httputil = postHttp(url, paramMap, fileMap, null);
        return httputil.execute().getString();
    }

    public static HttpUtil putHttp(String url, Multimap<String, String> paramMap, Map<String, File> fileMap,
                                   Map<String, String> headerMap) {
        HttpUtil httpUtil = newInstance();
        HttpPut  request  = new HttpPut(url);

        if ( fileMap != null && fileMap.size() > 0 ) {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setCharset(StandardCharsets.UTF_8).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            for ( Map.Entry<String, File> param : fileMap.entrySet() ) {
                multipartEntityBuilder.addBinaryBody(param.getKey(), param.getValue());
            }

            if ( paramMap != null && paramMap.size() > 0 ) {
                for ( Map.Entry<String, String> param : paramMap.entries() ) {
                    multipartEntityBuilder.addTextBody(param.getKey(), param.getValue(), ContentType.WILDCARD.withCharset(StandardCharsets.UTF_8));
                }
            }
            request.setEntity(multipartEntityBuilder.build());
        } else {
            EntityBuilder entityBuilder = EntityBuilder.create().setContentType(ContentType.APPLICATION_FORM_URLENCODED.withCharset(StandardCharsets.UTF_8)).setParameters();
            if ( paramMap != null && paramMap.size() > 0 ) {
                List<NameValuePair> nameValuePairs = convertParameters(paramMap);
                entityBuilder.setParameters(nameValuePairs);
            }
            request.setEntity(entityBuilder.build());
        }

        if ( headerMap != null ) {
            for ( Map.Entry<String, String> param : headerMap.entrySet() ) {
                request.addHeader(param.getKey(), param.getValue());
            }
        }
        httpUtil.request = request;
        return httpUtil;
    }

    public static String put(String url) throws IOException {
        HttpUtil httputil = putHttp(url, null, null, null);
        return httputil.execute().getString();
    }

    public static String put(String url, Multimap<String, String> map) throws IOException {
        HttpUtil httputil = putHttp(url, map, null, null);
        return httputil.execute().getString();
    }

    public static String put(String url, Multimap<String, String> paramMap, Map<String, String> headerMap) throws IOException {
        HttpUtil httputil = putHttp(url, paramMap, null, headerMap);
        return httputil.execute().getString();
    }

    public static String putFile(String url, Map<String, File> fileMap) throws IOException {
        HttpUtil httputil = postHttp(url, null, fileMap, null);
        return httputil.execute().getString();
    }

    public static HttpUtil deleteHttp(String url, Map<String, String> headerMap) {
        HttpUtil   httpUtil = newInstance();
        HttpDelete request  = new HttpDelete(url);
        if ( headerMap != null ) {
            for ( Map.Entry<String, String> param : headerMap.entrySet() ) {
                request.addHeader(param.getKey(), param.getValue());
            }
        }
        httpUtil.request = request;
        return httpUtil;
    }

    public static String delete(String url) throws IOException {
        HttpUtil httputil = deleteHttp(url, null);
        return httputil.execute().getString();
    }

    public static String delete(String url, Map<String, String> headerMap) throws IOException {
        HttpUtil httputil = deleteHttp(url, headerMap);
        return httputil.execute().getString();
    }

    public void setSocketTimeout(int socketTimeout) {
        this.configBuilder.setSocketTimeout(socketTimeout);
    }

    public void setConnectTimeout(int connectTimeout) {
        this.configBuilder.setConnectTimeout(connectTimeout);
    }

    public ResponseWrap execute() throws IOException {
        this.request.setConfig(this.configBuilder.build());

        CloseableHttpClient httpClient = clientBuilder.build();

        HttpClientContext context = HttpClientContext.create();
        if ( !this.cookieStore.getCookies().isEmpty() ) {
            context.setCookieStore(cookieStore);
        }

        CloseableHttpResponse response = httpClient.execute(request, context);
        return new ResponseWrap(response, context);
    }

    public class ResponseWrap {

        private CloseableHttpResponse response;
        private HttpClientContext     context;
        private HttpEntity            entity;

        public ResponseWrap(CloseableHttpResponse response, HttpClientContext context) {
            this.response = response;
            this.context = context;
            this.entity = response.getEntity();
        }

        public void close() {
            HttpClientUtils.closeQuietly(response);
        }

        public String getString() {
            return getString(StandardCharsets.UTF_8);
        }

        public String getString(Charset defaultCharset) {
            try {
                return entity == null ? "" : EntityUtils.toString(entity, defaultCharset);
            } catch ( IOException e ) {
                throw new RuntimeException(e);
            } finally {
                HttpClientUtils.closeQuietly(response);
            }
        }

        public byte[] getByteArray() {
            try {
                return entity == null ? null : EntityUtils.toByteArray(entity);
            } catch ( IOException e ) {
                throw new RuntimeException(e);
            } finally {
                HttpClientUtils.closeQuietly(response);
            }
        }

        public Header[] getAllHeaders() {
            return response.getAllHeaders();
        }

        public Header[] getHeaders(String name) {
            return response.getHeaders(name);
        }

        public StatusLine getStatusLine() {
            return response.getStatusLine();
        }

        public int getStatusCode() {
            return response.getStatusLine().getStatusCode();
        }

        public boolean containsHeader(String name) {
            return response.containsHeader(name);
        }

        public List<Cookie> getCookies() {
            return context.getCookieStore().getCookies();
        }

        public Cookie getCookie(String name) {
            List<Cookie> cookies = context.getCookieStore().getCookies();
            if ( cookies != null ) {
                for ( Cookie cookie : cookies ) {
                    if ( name.equals(cookie.getName()) ) {
                        return cookie;
                    }
                }
            }
            return null;
        }

        public InputStream getInputStream() {
            try {
                return entity == null ? null : entity.getContent();
            } catch ( IOException e ) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void addCookie(String name, String value) {
        cookieStore.addCookie(new BasicClientCookie2(name, value));
    }

    public void addCookie(Map<String, String> cookieMap) {
        for ( Entry<String, String> entry : cookieMap.entrySet() ) {
            cookieStore.addCookie(new BasicClientCookie2(entry.getKey(), entry.getValue()));
        }
    }

    static class IdleConnectionMonitorThread extends Thread {

        private final    HttpClientConnectionManager connMgr;
        private volatile boolean                     shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while ( !shutdown ) {
                    synchronized (this) {
                        wait(5000);
                        // Close expired connections
                        connMgr.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 30 sec
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch ( InterruptedException ex ) {
                // terminate
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }

    //    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
    //        SSLConnectionSocketFactory sslsf = null;
    //        try {
    //            SSLContext context = new SSLContextBuilder().loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true).build();
    //            sslsf = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
    //        } catch ( Exception ignored ) {}
    //        return sslsf;
    //    }
}
