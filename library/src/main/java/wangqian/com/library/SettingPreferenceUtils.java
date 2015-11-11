package wangqian.com.library;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 相关信息的设置Preference
 * WQ on 2015/10/20 09:50
 * wq@jjshome.com
 */
public class SettingPreferenceUtils {
    public static final String PREFERENCE_NAME = "settingInfo";

    /**
     * 设置主题
     * @param context
     * @param value
     */
    public static void  setTheme(Context context,int value){
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        sp.edit().putInt("theme",value).commit();
    }

    /**
     * 获取主题
     * @param context
     * @param defaultValue
     * @return
     */
    public static int getTheme(Context context,int defaultValue){
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return sp.getInt("theme", defaultValue);
    }
}
