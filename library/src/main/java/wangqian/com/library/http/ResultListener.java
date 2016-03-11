package wangqian.com.library.http;

/**
 * http响应结果监听器
 * WQ on 2016/1/15
 * wq@jjshome.com
 */
public interface ResultListener {
    /**
     * 返回的json值
     * @param result
     */
    void onSuccess(String result);

    /**
     * 错误信息
     * @param responseCode 类似500之类的服务码
     */
    void onFailure(int responseCode);
}
