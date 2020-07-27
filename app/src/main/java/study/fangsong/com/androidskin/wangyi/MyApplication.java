package study.fangsong.com.androidskin.wangyi;

import android.app.Application;

import study.fangsong.com.androidskin.skin.SkinManager;


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
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }

}
