package study.fangsong.com.androidskin.skin;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Observable;

import study.fangsong.com.androidskin.wangyi.MainActivity;

/**
 * =================================
 * <p>
 * 版权：北京畅行信息技术有限公司
 * <p>
 * 作者：Anson
 * <p>
 * 版本：
 * <p>
 * 创建日期：2019/9/22  12:42
 * <p>
 * 描述：
 * <p>
 * 修改历史：
 * <p>
 * =================================
 */

public class SkinManager extends Observable{

    public static volatile SkinManager instance;
    private  SkinActivityLifecycle skinActivityLifecycle;
    public Application application ;
    public static void init(Application application){
        if (null == instance){
            synchronized (SkinManager.class){
                if (null == instance){
                    instance = new SkinManager(application);
                }
            }
        }
    }

    public static SkinManager getInstance(){
        return instance;
    }

    private SkinManager(Application application){
        this.application = application;
        SkinPreference.init(application);
        SkinResources.init(application);
        //注册Activity生命周期回调
        skinActivityLifecycle =new SkinActivityLifecycle();
        application.registerActivityLifecycleCallbacks(skinActivityLifecycle);
        loadSkin(SkinPreference.getInstance().getSkin());
    }

    //加载皮肤包并更新
    public void loadSkin(String path) {
        if (!SkinPreference.getInstance().getIsCanSkin()){
            return;
        }
        if (TextUtils.isEmpty(path)){
            //还原默认皮肤包
//           SkinPreference.getInstance().setSkin("");
           SkinResources.getInstance().reset();
        }else{
            try {
                AssetManager assetManager =  AssetManager.class.newInstance();
                //添加资源进入资源管理器
                Method addAssetPath = assetManager.getClass().getMethod("addAssetPath",String.class);
                addAssetPath.setAccessible(true);
                addAssetPath.invoke(assetManager,path);

                Resources resources = application.getResources();
                //皮肤包Resource，第二/三个参数是设置横竖屏，语言等配置
                Resources skinResource =  new Resources(assetManager,resources.getDisplayMetrics(),resources.getConfiguration());
                //获取外部APK(皮肤包包名)
                PackageManager packageManager = application.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path,PackageManager.GET_ACTIVITIES);
                String packageName = packageInfo.packageName;
                SkinResources.getInstance().applySkin(skinResource,packageName);
                //保存当前使用的皮肤包
                SkinPreference.getInstance().setSkin(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //应用皮肤包
        setChanged();
        //通知观察者
        notifyObservers();
    }

    public void updateSkin(Activity activity) {
        skinActivityLifecycle.updateSkin(activity);
    }
}
