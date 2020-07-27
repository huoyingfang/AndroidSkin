package study.fangsong.com.androidskin.skin;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * =================================
 * <p>
 * 版权：北京畅行信息技术有限公司
 * <p>
 * 作者：Anson
 * <p>
 * 版本：
 * <p>
 * 创建日期：2019/9/22  10:42
 * <p>
 * 描述：
 * <p>
 * 修改历史：
 * <p>
 * =================================
 */

public class SkinAttribute {
    private static final List<String> mAttributes = new ArrayList<>();
    private  Typeface typeface;
    List<SkinView> skinViews = new ArrayList<>();
    static {
        mAttributes.add("background");
        mAttributes.add("src");
        mAttributes.add("textColor");
        mAttributes.add("drawableLeft");
        mAttributes.add("drawableTop");
        mAttributes.add("drawableRight");
        mAttributes.add("drawableBottom");
        mAttributes.add("skinTypeface");
    }
    public SkinAttribute(Typeface typeface) {
        this.typeface = typeface;
    }

    public void load(View view, AttributeSet attrs) {
        List<SkinPair> skinPairs = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //获得属性名
            String attributeName = attrs.getAttributeName(i);
            //是否符合需要筛选的属性名
            if (mAttributes.contains(attributeName)){
               String attributeValue  = attrs.getAttributeValue(i);
               //布局文件写死了，不管了
               if (attributeValue.startsWith("#")){ continue; }
               //资源id
               int resId;
               if (attributeValue.startsWith("?")){
                    int attrId = Integer.parseInt(attributeValue.substring(1));
                    //获得主题style中的对应attr的资源id值
                    resId =  SkinThemeUtils.getResId(view.getContext(), new int[]{attrId})[0];
                }else{
                    resId = Integer.parseInt(attributeValue.substring(1));
                }
               if (resId != 0){
                   //可以被替换的属性
                   SkinPair skinPair = new SkinPair(attributeName,resId);
                   skinPairs.add(skinPair);
               }
            }
        }
       //将View与之对应的可以动态替换的属性集合放入集合中
        if (!skinPairs.isEmpty() || view instanceof TextView || view instanceof SkinViewSupport){
            SkinView skinView = new SkinView(view,skinPairs);
            //新打开Activity换肤
            skinView.applySkin(typeface);
            skinViews.add(skinView);
        }
    }
     
    //当前页面更换皮肤
    public void applySkin(Typeface typeface) {
        for (SkinView skinView : skinViews) {
            skinView.applySkin(typeface);
        }
    }

    static class SkinPair{
        String attributeName;
        int resId;
        public SkinPair(String attributeName,int resId){
            this.attributeName = attributeName;
            this.resId = resId;
        }
    }
    static class SkinView{
        View view;
        List<SkinPair> skinPairs;
        public SkinView(View view,List<SkinPair> skinPairs){
            this.view = view;
            this.skinPairs =skinPairs;
        }

        public void applySkin(Typeface typeface) {
            applySkinTypeface(typeface);
            applySkinViewSupport();
            for (SkinPair skinPair : skinPairs) {
                Drawable left = null, top = null, right = null, bottom = null;
                switch (skinPair.attributeName){
                    case "background":
                       Object background =  SkinResources.getInstance().getBackground(skinPair.resId);
                       if (background instanceof Integer){
                            //Color
                           view.setBackgroundColor((Integer) background);
                       }else{
                           ViewCompat.setBackground(view, (Drawable) background);
                       }
                        break;
                    case "src":
                        background = SkinResources.getInstance().getBackground(skinPair
                                .resId);
                        if (background instanceof Integer) {
                            ((ImageView) view).setImageDrawable(new ColorDrawable((Integer)
                                    background));
                        } else {
                            ((ImageView) view).setImageDrawable((Drawable) background);
                        }
                        break;
                    case "textColor":
                        ((TextView) view).setTextColor(SkinResources.getInstance().getColorStateList
                                (skinPair.resId));
                        break;
                    case "drawableLeft":
                        left = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableTop":
                        top = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableRight":
                        right = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableBottom":
                        bottom = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "skinTypeface":
                        Typeface typeface1 = SkinResources.getInstance().getTypeface(skinPair.resId);
                        applySkinTypeface(typeface1);
                        break;
                    default:
                        break;
                }
                if (null != left || null != right || null != top || null != bottom) {
                    ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
                }
            }
        }

        private void applySkinViewSupport() {
            if (view instanceof SkinViewSupport){
                ((SkinViewSupport)view).applySkin();
            }
        }

        private void applySkinTypeface(Typeface typeface) {
            if (view instanceof TextView){
                ((TextView)view).setTypeface(typeface);
            }
        }
    }
}
