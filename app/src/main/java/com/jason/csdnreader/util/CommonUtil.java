package com.jason.csdnreader.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by iNanHu on 2015/10/31.
 */
public class CommonUtil {
    /**
     * 弹出Toast
     * @param context
     */
    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
