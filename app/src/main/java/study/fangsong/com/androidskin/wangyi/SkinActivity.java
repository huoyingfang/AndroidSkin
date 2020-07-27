package study.fangsong.com.androidskin.wangyi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import java.io.File;

import study.fangsong.com.androidskin.R;
import study.fangsong.com.androidskin.skin.SkinManager;
import study.fangsong.com.androidskin.skin.SkinPreference;


/**
 * =================================
 * <p>
 * 版权：北京畅行信息技术有限公司
 * <p>
 * 作者：Anson
 * <p>
 * 版本：
 * <p>
 * 描述：
 * <p>
 * 修改历史：
 * <p>
 * =================================
 */
public class SkinActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
    }

    public void change(View view) {
//        SkinManager.getInstance().loadSkin("/storage/emulated/0/app-skin-debug.apk");
//        SkinManager.getInstance().loadSkin(Environment.getExternalStorageState()+"app-skin-debug.apk");
        SkinPreference.getInstance().setIsCanSkin(true);
        String path = SkinPreference.getInstance().getSkin();
        SkinManager.getInstance().loadSkin(path);
    }
    public void restore(View view) {
        SkinManager.getInstance().loadSkin(null);
        SkinPreference.getInstance().setIsCanSkin(false);
    }
}
