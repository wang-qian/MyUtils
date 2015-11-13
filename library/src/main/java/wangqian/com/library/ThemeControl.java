package wangqian.com.library;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.util.TypedValue;

/**
 * 主题设置相关的类
 * WQ on 2015/11/9 09:41
 * wendell.std@gmail.com
 */
public class ThemeControl {
    private Context mContext;
    private int mCurrentTheme;
    public ThemeControl(Context context){
        this.mContext = context;
        isChanged(); // invalidate stored booleans
    }

    /**
     * 获取主题强调色
     * @param context
     * @return
     */
    public static int getAccentColor(Context context){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.theme_accent_color, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取当前主题对应的暗色调
     * @return
     */
    public static int getThemePrimaryDarkColor(Context context){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.theme_color_dark, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取当前主题色对应色值
     * @param context
     * @return
     */
    public static int getThemePrimaryColor(Context context){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.theme_color, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取color对应的int值
     * @param context Activity
     * @param color 资源颜色id
     * @return 对应的int value
     */
    public static int getColorWarp(Activity context,@ColorRes int color){
        return context.getResources().getColor(color);
//        return context.getResources().getColor(color,context.getTheme());
    }

    /**
     * 当前主题是否改变
     *
     * @return
     */
    public boolean isChanged() {
        int currentTheme = getTheme();
        boolean isChange = mCurrentTheme != currentTheme;
        mCurrentTheme = currentTheme;
        return isChange;
    }

    /**
     * 获取SettingPreference的主题
     * 在super.onCreate(savedInstanceState); 之前调用
     * setTheme(themeControl.getTheme());
     *
     * @param
     * @return
     */
    public int getTheme() {
        return SettingPreferenceUtils.getTheme(mContext, R.style.AppTheme);
    }

    /**
     * 设置主题
     * themeControl.setTheme(R.style.LightPink);
     *
     * @param theme
     */
    public void setTheme(int theme) {
        SettingPreferenceUtils.setTheme(mContext, theme);
    }
}
