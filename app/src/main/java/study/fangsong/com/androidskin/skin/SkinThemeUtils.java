package study.fangsong.com.androidskin.skin;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;

import study.fangsong.com.androidskin.R;

/**
 * =================================
 * <p>
 * 版权：北京畅行信息技术有限公司
 * <p>
 * 作者：Anson
 * <p>
 * 版本：
 * <p>
 * 创建日期：2019/9/23  17:30
 * <p>
 * 描述：
 * <p>
 * 修改历史：
 * <p>
 * =================================
 */

public class SkinThemeUtils {
    private static int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {android.support.v7.appcompat.R.attr.colorPrimaryDark};
    private static int[] STATUSBAR_COLOR_ATTRS = {android.R.attr.statusBarColor, android.R.attr.navigationBarColor};
    private static int[] TYPEFACE_ATTR = {R.attr.skinTypeface};

    public static int[] getResId(Context context ,int [] attrs){
        int[] resIds = new int[attrs.length];
         TypedArray typedArray = context.obtainStyledAttributes(attrs);
        for (int i = 0; i < typedArray.length(); i++) {
           resIds[i] =  typedArray.getResourceId(i,0);
        }
        typedArray.recycle();
        return resIds;
    }

    public static void updateStatusBar(Activity activity) {
        //5.0以上才能修改
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        int[] resId = getResId(activity,STATUSBAR_COLOR_ATTRS);
        //如果没有配置属性则获得0
        if (resId[0] == 0){
          int statusBarColors =  getResId(activity,APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS)[0];
          if (statusBarColors != 0){
              activity.getWindow().setStatusBarColor(SkinResources.getInstance().getColor(statusBarColors));
          }
        }else{
            activity.getWindow().setStatusBarColor(SkinResources.getInstance().getColor(resId[0]));
        }

        //修改底部虚拟按键的颜色
        if (resId[1] != 0){
            activity.getWindow().setNavigationBarColor(SkinResources.getInstance().getColor(resId[1]));
        }
    }

    /**
     * 获得字体
     * @param activity
     */
    public static Typeface getSkinTypeface(Activity activity) {
       int skinTypeId = getResId(activity,TYPEFACE_ATTR)[0];
       return SkinResources.getInstance().getTypeface(skinTypeId);
    }
}
