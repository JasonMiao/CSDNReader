package com.jason.csdnreader.ui.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jason.csdnreader.R;
import com.jason.csdnreader.util.LoginUtil;

/**
 * Created by zzmiao on 2015/10/30.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        etUsername = (EditText) findViewById(R.id.et_login_username);
        etPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
//            String username = etUsername.getText().toString();
//            String password = etPassword.getText().toString();
            String username = "bagecelia@163.com";
            String password = "mcmzz925424";
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this, R.string.no_input_account, Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, R.string.no_input_password, Toast.LENGTH_SHORT).show();
                return;
            }
            int result = LoginUtil.doLogin(username, password);
            if (result == LoginUtil.SUCCESS)
                Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
            else if (result == LoginUtil.FAILURE)
                Toast.makeText(this, R.string.login_failure, Toast.LENGTH_SHORT).show();
            else if (result == LoginUtil.BUSY)
                Toast.makeText(this, R.string.login_busy, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 注册界面用户点击两次返回则退出应用
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}