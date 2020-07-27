package study.fangsong.com.androidskin.skin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * =================================
 * <p>
 * 版权：北京畅行信息技术有限公司
 * <p>
 * 作者：Anson
 * <p>
 * 版本：
 * <p>
 * 创建日期：2019/9/23  14:42
 * <p>
 * 描述：
 * <p>
 * 修改历史：
 * <p>
 * =================================
 */

public class SkinLayoutFactory implements LayoutInflater.Factory2,Observer {
    private static final String[] mClassPrefixList = {"android.widget.","android.view.","android.webkit."};
    private static final Class<?>[] mConstructorSignature = new Class[] {Context.class, AttributeSet.class};
    //缓存已经找到的控件
    private static final HashMap<String, Constructor<? extends View>> sConstructorMap = new HashMap<String, Constructor<? extends View>>();
    //属性处理类
    SkinAttribute skinAttribute;
    private Activity activity;
    public SkinLayoutFactory(Activity activity, Typeface typeface){
        this.activity = activity;
        skinAttribute = new SkinAttribute(typeface);
    }
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        //反射classloader
       View view =  createViewFromTag(name,context,attrs);
       //自定义View 不需要拼接包名
       if (null == view){
         view = createView(name,context,attrs);
       }
      //筛选符合属性的view
        skinAttribute.load(view ,attrs);
        return view;
    }

    private View createViewFromTag(String name, Context context, AttributeSet attrs) {
        //包含了点自定义控件不处理
        if ( -1 != name.indexOf(".")){
            return null;
        }
        View view = null;
        for (int i = 0; i < mClassPrefixList.length; i++) {
            view = createView(mClassPrefixList[i]+name,context,attrs);
            if (null != view){
                break;
            }
        }
        return view;
    }
    private View createView(String name, Context context, AttributeSet attrs) {
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        if (null == constructor){
            try {
                Class<? extends View> aClass =  context.getClassLoader().loadClass(name).asSubclass(View.class);
                //获取构造函数
                constructor = aClass.getConstructor(mConstructorSignature);
                sConstructorMap.put(name,constructor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (null != constructor){
            try {
                return constructor.newInstance(context,attrs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        //更改状态栏颜色
        SkinThemeUtils.updateStatusBar(activity);
        Typeface typeface = SkinThemeUtils.getSkinTypeface(activity);
        //更换皮肤
        skinAttribute.applySkin(typeface);
    }
}
