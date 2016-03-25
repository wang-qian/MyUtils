package wangqian.com.library.threadmanager;

/**
 * TODO
 * WQ on 2016/3/15
 * wq@jjshome.com
 */
public interface ThreadCallback<T> {
    void onComplete(T t);
    void onError(Exception e);
}
