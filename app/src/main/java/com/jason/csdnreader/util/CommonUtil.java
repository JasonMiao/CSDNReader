package com.jason.csdnreader.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.jason.csdnreader.app.MyApplication;

/**
 * Created by iNanHu on 2015/10/31.
 */
public class CommonUtil {
    static ProgressDialog dialog = null;
    /**
     * 弹出Toast
     * @param context
     */
    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示进度条
     */
    public static void showLoadingDialog() {
        if (null == dialog) {
            dialog = new ProgressDialog(MyApplication.getMyApplication().getApplicationContext());
            // 设置进度条样式
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // 设置提示消息
            dialog.setMessage("数据加载中...");
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.show();
        }
    }

    public static void dismissLoadingDialog() {
        if (dialog != null)
            dialog.dismiss();
    }
}
