package com.jason.csdnreader.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.jason.csdnreader.app.MyApplication;

/**
 * 常用View工具类
 *
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
    public static void showLoadingDialog(Context context, String message) {
        if (null == dialog) {
            dialog = new ProgressDialog(context); // dialog的创建不能用全局application
            // 设置进度条样式
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // 设置提示消息
            dialog.setMessage(message);
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.show();
        }
    }

    public static void dismissLoadingDialog() {
        if (dialog != null)
            dialog.dismiss();
            dialog = null; // 这句话必须加 因为不同界面需要构造不同Activity的dialog
    }
}
