package study.fangsong.com.androidskin.wangyi.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import study.fangsong.com.androidskin.R;
import study.fangsong.com.androidskin.skin.SkinResources;
import study.fangsong.com.androidskin.skin.SkinViewSupport;


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
public class MyTabLayout extends TabLayout implements SkinViewSupport{
    int tabIndicatorColorResId;
    int tabTextColorResId;

    public MyTabLayout(Context context) {
        this(context, null, 0);
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabLayout,
                defStyleAttr, 0);
        tabIndicatorColorResId = a.getResourceId(R.styleable.TabLayout_tabIndicatorColor, 0);
        tabTextColorResId = a.getResourceId(R.styleable.TabLayout_tabTextColor, 0);
        a.recycle();
    }

    @Override
    public void applySkin() {
        if (tabIndicatorColorResId != 0) {
            int tabIndicatorColor = SkinResources.getInstance().getColor(tabIndicatorColorResId);
            setSelectedTabIndicatorColor(tabIndicatorColor);
        }

        if (tabTextColorResId != 0) {
            ColorStateList tabTextColor = SkinResources.getInstance().getColorStateList(tabTextColorResId);
            setTabTextColors(tabTextColor);
        }
    }
}
