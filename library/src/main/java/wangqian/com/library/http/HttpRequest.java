package wangqian.com.library.http;

import java.util.HashMap;
import java.util.Map;

/**
 * request
 * WQ on 2016/1/15
 * wq@jjshome.com
 */
public class HttpRequest {
    private String url;
    private int                 connectTimeout;
    private int                 readTimeout;
    private Map<String, String> parasMap;
    private Map<String, String> requestProperties;

    public HttpRequest(String url) {
        this.url = url;
        this.connectTimeout = -1;
        this.readTimeout = -1;
        requestProperties = new HashMap<String, String>();
    }

    public HttpRequest(String url, Map<String, String> parasMap) {
        this.url = url;
        this.parasMap = parasMap;
        this.connectTimeout = -1;
        this.readTimeout = -1;
        requestProperties = new HashMap<String, String>();
    }

    public String getUrl() {
        return url;
    }

    /**
     * @return
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * @param timeoutMillis
     */
    public void setConnectTimeout(int timeoutMillis) {
        if (timeoutMillis < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        connectTimeout = timeoutMillis;
    }

    /**
     * @return
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * @param timeoutMillis
     */
    public void setReadTimeout(int timeoutMillis) {
        if (timeoutMillis < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        readTimeout = timeoutMillis;
    }

    /**
     * get paras map
     *
     * @return
     */
    public Map<String, String> getParasMap() {
        return parasMap;
    }

    /**
     * set paras map
     *
     * @param parasMap
     */
    public void setParasMap(Map<String, String> parasMap) {
        this.parasMap = parasMap;
    }

    /**
     * @return paras as string
     */
    public String getParas() {
        return HttpUtils.joinParasWithEncodedValue(parasMap);
    }

    /**
     * @param field
     * @param newValue
     */
    public void setRequestProperty(String field, String newValue) {
        requestProperties.put(field, newValue);
    }

    /**
     * @param field
     */
    public String getRequestProperty(String field) {
        return requestProperties.get(field);
    }

    /**
     * same to {@link #setRequestProperty(String, String)} filed is User-Agent
     *
     * @param value
     */
    public void setUserAgent(String value) {
        requestProperties.put("User-Agent", value);
    }

    /**
     * @return
     */
    public Map<String, String> getRequestProperties() {
        return requestProperties;
    }

    /**
     * @param requestProperties
     */
    public void setRequestProperties(Map<String, String> requestProperties) {
        this.requestProperties = requestProperties;
    }
}
