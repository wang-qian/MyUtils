package wangqian.com.myutils.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import wangqian.com.myutils.DeviceUtil;
import wangqian.com.myutils.R;
import wangqian.com.myutils.ThemeControl;

/**
 * WQ on 2015/11/10 14:43
 * wq@jjshome.com
 */
public abstract class BaseActivity extends AppCompatActivity {
    ThemeControl themeControl;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        themeControl = new ThemeControl(this);
//        themeControl.setTheme(R.style.LightPink);
        setTheme(themeControl.getTheme());
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initToolBar();
        setupStatusBar();
    }


    /**
     * 设置布局文件
     * @return
     */
    abstract int setLayout();


    /**
     * 设置toolbar替代actionBar
     */
    private void initToolBar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar!=null){
            setSupportActionBar(mToolbar);
        }
    }

    /**
     * 设置状态栏变色,导航栏变色
     */
    @TargetApi(19)
    protected void setupStatusBar() {
        if (Build.VERSION.SDK_INT >= 19) {
            DeviceUtil.setTranslucentStatus(this, true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(ThemeControl.getThemePrimaryColor(this));
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintColor(ThemeControl.getThemePrimaryColor(this));
            tintManager.setNavigationBarTintEnabled(true);
        }
    }
}
