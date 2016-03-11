package wangqian.com.library.http;

import android.os.Handler;
import android.os.Looper;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import wangqian.com.library.StringUtil;

/**
 * http 工具类
 * WQ on 2016/1/15
 * wq@jjshome.com
 */
public class HttpUtils {
    /** url and para separator **/
    public static final String URL_AND_PARA_SEPARATOR = "?";
    /** parameters separator **/
    public static final String PARAMETERS_SEPARATOR   = "&";
    /** paths separator **/
    public static final String PATHS_SEPARATOR        = "/";
    /** equal sign **/
    public static final String EQUAL_SIGN             = "=";

    private HttpUtils() {
        throw new AssertionError();
    }

    /**
     * http get synchronous
     * <ul>
     * <li>use gzip compression default</li>
     * <li>use bufferedReader to improve the reading speed</li>
     * </ul>
     *
     * @param request
     * @return the response of the url, if null represents http error
     */
    public static HttpResponse httpGet(HttpRequest request) {
        if (request == null) {
            return null;
        }

        BufferedReader input = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL(request.getUrl());
            try {
                HttpResponse response = new HttpResponse(request.getUrl());
                // default gzip encode
                con = (HttpURLConnection)url.openConnection();
                setURLConnection(request, con);
                input = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = input.readLine()) != null) {
                    sb.append(s).append("\n");
                }
                response.setResponseBody(sb.toString());
                setHttpResponse(con, response);
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } finally {
            // close buffered
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // disconnecting releases the resources held by a connection so they may be closed or reused
            if (con != null) {
                con.disconnect();
            }
        }

        return null;
    }

    /**
     * http get synchronous
     *
     * @param httpUrl
     * @return the response of the url, if null represents http error
     * @see HttpUtils#httpGet(HttpRequest)
     */
    public static HttpResponse httpGet(String httpUrl) {
        return httpGet(new HttpRequest(httpUrl));
    }

    public static void httpAsyncGet(final HttpRequest request, final ResultListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HttpResponse response = httpGet(request);
                if(response.getResponseCode()!=200){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure(response.getResponseCode());
                        }
                    });

                }else{
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess(response.getResponseBody());
                        }
                    });

                }
            }
        }).start();

    }

    /**
     * http get synchronous
     *
     * @param request
     * @return the content of the url, if null represents http error
     * @see HttpUtils#httpGet(HttpRequest)
     */
    public static String httpGetString(HttpRequest request) {
        HttpResponse response = httpGet(request);
        return response == null ? null : response.getResponseBody();
    }

    /**
     * http get synchronous
     *
     * @param httpUrl
     * @return the content of the url, if null represents http error
     * @see HttpUtils#httpGet(HttpRequest)
     */
    public static String httpGetString(String httpUrl) {
        HttpResponse response = httpGet(new HttpRequest(httpUrl));
        return response == null ? null : response.getResponseBody();
    }



    public static HttpResponse httpPost(HttpRequest request) {
        if (request == null) {
            return null;
        }

        BufferedReader input = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL(request.getUrl());
            try {
                HttpResponse response = new HttpResponse(request.getUrl());
                // default gzip encode
                con = (HttpURLConnection)url.openConnection();
                setURLConnection(request, con);
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                String paras = request.getParas();
                if (!StringUtil.isEmpty(paras)) {
                    con.getOutputStream().write(paras.getBytes());
                }
                input = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = input.readLine()) != null) {
                    sb.append(s).append("\n");
                }
                response.setResponseBody(sb.toString());
                setHttpResponse(con, response);
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } finally {
            // close buffered
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // disconnecting releases the resources held by a connection so they may be closed or reused
            if (con != null) {
                con.disconnect();
            }
        }

        return null;
    }

    /**
     * http post
     *
     * @param httpUrl
     * @return the response of the url, if null represents http error
     * @see HttpUtils#httpPost(HttpRequest)
     */
    public static HttpResponse httpPost(String httpUrl) {
        return httpPost(new HttpRequest(httpUrl));
    }

    /**
     * 异步post方式
     * @param request
     * @param listener
     */
    public static void httpAsyncPost(final HttpRequest request, final ResultListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HttpResponse response = httpPost(request);
                if(response.getResponseCode()!=200){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure(response.getResponseCode());
                        }
                    });

                }else{
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess(response.getResponseBody());
                        }
                    });

                }
            }
        }).start();


    }
    /**
     * http post
     *
     * @param httpUrl
     * @return the content of the url, if null represents http error
     * @see HttpUtils#httpPost(HttpRequest)
     */
    public static String httpPostString(String httpUrl) {
        HttpResponse response = httpPost(new HttpRequest(httpUrl));
        return response == null ? null : response.getResponseBody();
    }

    /**
     * http post
     *
     * @param httpUrl
     * @param parasMap paras map, key is para name, value is para value. will be transfrom to String by
     *        {@link HttpUtils#joinParas(Map)}
     * @return the content of the url, if null represents http error
     * @see HttpUtils#httpPost(HttpRequest)
     */
    public static String httpPostString(String httpUrl, Map<String, String> parasMap) {
        HttpResponse response = httpPost(new HttpRequest(httpUrl, parasMap));
        return response == null ? null : response.getResponseBody();
    }

    /**
     * join url and paras
     *
     * <pre>
     * getUrlWithParas(null, {(a, b)})                        =   "?a=b";
     * getUrlWithParas("baidu.com", {})                       =   "baidu.com";
     * getUrlWithParas("baidu.com", {(a, b), (i, j)})         =   "baidu.com?a=b&i=j";
     * getUrlWithParas("baidu.com", {(a, b), (i, j), (c, d)}) =   "baidu.com?a=b&i=j&c=d";
     * </pre>
     *
     * @param url url
     * @param parasMap paras map, key is para name, value is para value
     * @return if url is null, process it as empty string
     */
    public static String getUrlWithParas(String url, Map<String, String> parasMap) {
        StringBuilder urlWithParas = new StringBuilder(StringUtil.isEmpty(url) ? "" : url);
        String paras = joinParas(parasMap);
        if (!StringUtil.isEmpty(paras)) {
            urlWithParas.append(URL_AND_PARA_SEPARATOR).append(paras);
        }
        return urlWithParas.toString();
    }

    /**
     * join url and encoded paras
     *
     * @param url
     * @param parasMap
     * @return
     * @see #getUrlWithParas(String, Map)
     */
    public static String getUrlWithValueEncodeParas(String url, Map<String, String> parasMap) {
        StringBuilder urlWithParas = new StringBuilder(StringUtil.isEmpty(url) ? "" : url);
        String paras = joinParasWithEncodedValue(parasMap);
        if (!StringUtil.isEmpty(paras)) {
            urlWithParas.append(URL_AND_PARA_SEPARATOR).append(paras);
        }
        return urlWithParas.toString();
    }

    /**
     * join paras
     *
     * @param parasMap paras map, key is para name, value is para value
     * @return join key and value with {@link #EQUAL_SIGN}, join keys with {@link #PARAMETERS_SEPARATOR}
     */
    public static String joinParas(Map<String, String> parasMap) {
        if (parasMap == null || parasMap.size() == 0) {
            return null;
        }

        StringBuilder paras = new StringBuilder();
        Iterator<Map.Entry<String, String>> ite = parasMap.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>)ite.next();
            paras.append(entry.getKey()).append(EQUAL_SIGN).append(entry.getValue());
            if (ite.hasNext()) {
                paras.append(PARAMETERS_SEPARATOR);
            }
        }
        return paras.toString();
    }

    /**
     * join paras with encoded value
     *
     * @param parasMap
     * @return
     * @see #joinParas(Map)
     */
    public static String joinParasWithEncodedValue(Map<String, String> parasMap) {
        StringBuilder paras = new StringBuilder("");
        if (parasMap != null && parasMap.size() > 0) {
            Iterator<Map.Entry<String, String>> ite = parasMap.entrySet().iterator();
            try {
                while (ite.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>)ite.next();
                    paras.append(entry.getKey()).append(EQUAL_SIGN).append(StringUtil.utf8Encode(entry.getValue()));
                    if (ite.hasNext()) {
                        paras.append(PARAMETERS_SEPARATOR);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return paras.toString();
    }

    /**
     * append a key and value pair to url
     *
     * @param url
     * @param paraKey
     * @param paraValue
     * @return
     */
    public static String appendParaToUrl(String url, String paraKey, String paraValue) {
        if (StringUtil.isEmpty(url)) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        if (!url.contains(URL_AND_PARA_SEPARATOR)) {
            sb.append(URL_AND_PARA_SEPARATOR);
        } else {
            sb.append(PARAMETERS_SEPARATOR);
        }
        return sb.append(paraKey).append(EQUAL_SIGN).append(paraValue).toString();
    }

    private static final SimpleDateFormat GMT_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z",
            Locale.ENGLISH);

    /**
     * parse gmt time to long
     *
     * @param gmtTime likes Thu, 11 Apr 2013 10:20:30 GMT
     * @return -1 represents exception otherwise time in milliseconds
     */
    public static long parseGmtTime(String gmtTime) {
        try {
            return GMT_FORMAT.parse(gmtTime).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * set HttpRequest to HttpURLConnection
     *
     * @param request source request
     * @param urlConnection destin url connection
     */
    private static void setURLConnection(HttpRequest request, HttpURLConnection urlConnection) {
        if (request == null || urlConnection == null) {
            return;
        }

        setURLConnection(request.getRequestProperties(), urlConnection);
        if (request.getConnectTimeout() >= 0) {
            urlConnection.setConnectTimeout(request.getConnectTimeout());
        }
        if (request.getReadTimeout() >= 0) {
            urlConnection.setReadTimeout(request.getReadTimeout());
        }
    }

    /**
     * set HttpURLConnection property
     *
     * @param requestProperties
     * @param urlConnection
     */
    public static void setURLConnection(Map<String, String> requestProperties, HttpURLConnection urlConnection) {
        if (requestProperties==null||requestProperties.size()==0 || urlConnection == null) {
            return;
        }

        for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
            if (!StringUtil.isEmpty(entry.getKey())) {
                urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * set HttpURLConnection to HttpResponse
     *
     * @param urlConnection source url connection
     * @param response destin response
     */
    private static void setHttpResponse(HttpURLConnection urlConnection, HttpResponse response) {
        if (response == null || urlConnection == null) {
            return;
        }
        try {
            response.setResponseCode(urlConnection.getResponseCode());
        } catch (IOException e) {
            response.setResponseCode(-1);
        }
        response.setResponseHeader(HttpConstants.EXPIRES, urlConnection.getHeaderField("Expires"));
        response.setResponseHeader(HttpConstants.CACHE_CONTROL, urlConnection.getHeaderField("Cache-Control"));
    }

}
