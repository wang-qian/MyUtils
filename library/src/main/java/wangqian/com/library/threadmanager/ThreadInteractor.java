package wangqian.com.library.threadmanager;

import wangqian.com.library.threadmanager.impl.MainThreadImpl;

/**
 * TODO
 * WQ on 2016/3/25
 * wq@jjshome.com
 */
public abstract class ThreadInteractor<T> {
    Runnable runnable;
    ThreadCallback callback;
    MainThread mainThread;

    public ThreadInteractor(ThreadCallback callback) {
        this.callback = callback;
        mainThread = MainThreadImpl.getInstance();
    }

    public abstract T run();

    public void onFinished(final T t) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onComplete(t);
            }
        });
    }

    public void onError(final Exception e) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(e);
            }
        });
    }

}
