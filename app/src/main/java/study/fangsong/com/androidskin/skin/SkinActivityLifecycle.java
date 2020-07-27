package study.fangsong.com.androidskin.skin;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.Log;
import android.view.LayoutInflater;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * =================================
 * <p>
 * 版权：北京畅行信息技术有限公司
 * <p>
 * 作者：Anson
 * <p>
 * 版本：
 * <p>
 * 创建日期：2019/9/23  17:42
 * <p>
 * 描述：
 * <p>
 * 修改历史：
 * <p>
 * =================================
 */

public class SkinActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    HashMap<Activity,SkinLayoutFactory> skinLayoutFactoryMap = new HashMap<>();
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //更新状态栏
        SkinThemeUtils.updateStatusBar(activity);
        //字体
        Typeface typeface = SkinThemeUtils.getSkinTypeface(activity);
        //获得Activity布局加载器
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        //Android布局加载器使用mFactorySet标记是否设置过Factory,如果设置过Factory抛出异常
        //设置mFactorySet 标签为false
        try {
          Field field =  LayoutInflater.class.getDeclaredField("mFactorySet");
          field.setAccessible(true);
          field.setBoolean(layoutInflater,false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SkinLayoutFactory skinLayoutFactory = new SkinLayoutFactory(activity,typeface);
        LayoutInflaterCompat.setFactory2(layoutInflater,skinLayoutFactory);
        //注册观察者
        SkinManager.getInstance().addObserver(skinLayoutFactory);
        skinLayoutFactoryMap.put(activity,skinLayoutFactory);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //删除观察者
       SkinLayoutFactory skinLayoutFactory =  skinLayoutFactoryMap.remove(activity);
       SkinManager.getInstance().deleteObserver(skinLayoutFactory);
    }

    public void updateSkin(Activity activity) {
        SkinLayoutFactory skinLayoutFactory = skinLayoutFactoryMap.get(activity);
        skinLayoutFactory.update(null,null);
    }
}
