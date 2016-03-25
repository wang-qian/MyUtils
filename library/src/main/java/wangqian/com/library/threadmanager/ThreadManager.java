package wangqian.com.library.threadmanager;

import wangqian.com.library.threadmanager.impl.ThreadExecutor;

/**
 * TODO
 * WQ on 2016/3/18
 * wq@jjshome.com
 */
public class ThreadManager{
    static Executor executor;
    private static volatile ThreadManager mThreadManager;
    private ThreadManager() {
        executor =  ThreadExecutor.getInstance();
    }
    public static ThreadManager getInstance() {
        if (mThreadManager == null) {
            mThreadManager = new ThreadManager();
        }

        return mThreadManager;
    }

    public static void post(ThreadInteractor t){
        executor.execute(t);
    }

}
