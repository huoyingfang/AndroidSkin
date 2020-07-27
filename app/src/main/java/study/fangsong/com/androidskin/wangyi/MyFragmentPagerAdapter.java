package study.fangsong.com.androidskin.wangyi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
 * 描述：
 * <p>
 * 修改历史：
 * <p>
 * =================================
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitles;
    private List<Fragment> mFragments;


    public MyFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments,
                                  List<String> titles) {
        super(fragmentManager);
        mFragments = fragments;
        mTitles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
