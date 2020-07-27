package study.fangsong.com.androidskin.skin;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * =================================
 * <p>
 * 版权：北京畅行信息技术有限公司
 * <p>
 * 作者：Anson
 * <p>
 * 版本：
 * <p>
 * 创建日期：2019/9/24  13:42
 * <p>
 * 描述：
 * <p>
 * 修改历史：
 * <p>
 * =================================
 */
public class SkinPreference {
    private static final String SKIN_SHARED = "skins";

    private static final String KEY_SKIN_PATH = "skin-path";
    private static final String IS_CAN_SKIN = "is_can_skin";
    private static SkinPreference instance;
    private final SharedPreferences mPref;

    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinPreference.class) {
                if (instance == null) {
                    instance = new SkinPreference(context.getApplicationContext());
                }
            }
        }
    }

    public static SkinPreference getInstance() {
        return instance;
    }

    private SkinPreference(Context context) {
        mPref = context.getSharedPreferences(SKIN_SHARED, Context.MODE_PRIVATE);
    }

    public void setSkin(String skinPath) {
        mPref.edit().putString(KEY_SKIN_PATH, skinPath).apply();
    }


    public String getSkin() {
        return mPref.getString(KEY_SKIN_PATH, null);
    }

    public void setIsCanSkin(boolean isCanSkin) {
        mPref.edit().putBoolean(IS_CAN_SKIN, isCanSkin).apply();
    }

    public boolean getIsCanSkin() {
       return mPref.getBoolean(IS_CAN_SKIN, false);
    }

}
