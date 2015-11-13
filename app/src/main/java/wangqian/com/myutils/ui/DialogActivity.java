package wangqian.com.myutils.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import wangqian.com.myutils.R;

/**
 * WQ on 2015/11/13 15:41
 * wendell.std@gmail.com
 */
public class DialogActivity extends AppCompatActivity {
    RelativeLayout rlNoticeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);//点击区域外不消失
        setContentView(R.layout.activity_dialog);
        findViewById(R.id.tv_notice_title);
    }
}
