package com.jason.csdnreader.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jason.csdnreader.R;
import com.jason.csdnreader.util.CommonUtil;
import com.jason.csdnreader.util.LoginUtil;

/**
 * Created by zzmiao on 2015/10/30.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        etAccount = (EditText) findViewById(R.id.et_login_account);
        etPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
//            String account = etAccount.getText().toString().trim();
//            String password = etPassword.getText().toString().trim();
            String account = "bagecelia@163.com";
            String password = "mcmzz925424";
            if (TextUtils.isEmpty(account)) {
                CommonUtil.showToast(this, getString(R.string.no_input_account));
                return;
            }
            if (TextUtils.isEmpty(password)) {
                CommonUtil.showToast(this, getString(R.string.no_input_password));
                return;
            }
            new AsyncTask<String, Void, Integer>() {
                @Override
                protected Integer doInBackground(String... params) {
                    int result = LoginUtil.doLogin(params[0], params[1]);
                    return result;
                }

                @Override
                protected void onPostExecute(Integer result) {
                    if (result == LoginUtil.SUCCESS) {
                        CommonUtil.showToast(LoginActivity.this, getString(R.string.login_success));
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else if (result == LoginUtil.FAILURE)
                        CommonUtil.showToast(LoginActivity.this, getString(R.string.login_failure));
                    else if (result == LoginUtil.BUSY)
                        CommonUtil.showToast(LoginActivity.this, getString(R.string.login_busy));
                }
            }.execute(account, password);
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