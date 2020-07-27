package study.fangsong.com.androidskin.skin;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * =================================
 * <p>
 * 版权：北京畅行信息技术有限公司
 * <p>
 * 作者：Anson
 * <p>
 * 版本：
 * <p>
 * 创建日期：2019/9/22  17:42
 * <p>
 * 描述：
 * <p>
 * 修改历史：
 * <p>
 * =================================
 */

public class DownUtils {
    /**
     * 把Assets里面得文件复制到 /data/data/files 目录下
     */
    public static void extractAssets(Context context, String sourceName) {
        AssetManager am;
        am = context.getAssets();
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = am.open(sourceName);
            File extractFile = context.getFileStreamPath(sourceName);
            fos = new FileOutputStream(extractFile);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(is);
            closeSilently(fos);
        }
    }
    private static void closeSilently(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Throwable e) {
            // ignore
        }
    }
}
