package may.com.module.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import may.com.module.R;
import may.com.module.utils.DBApi;
import may.com.module.utils.ToastUtils;


public class LoginActivity extends Activity {
    private Context ctx;
    public static LoginActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        ctx = this;
        setContentView(R.layout.activity_login);
        initView();
    }

    private EditText etPhone, etPwd;

    private void initView() {
        etPhone = (EditText) findViewById(R.id.et_account);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    //登录
    private void login() {
        String phone = etPhone.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        if ("".equals(phone) || "".equals(pwd)) {
            ToastUtils.showToast(ctx, "用户名或密码为空");
            return;
        }
        long id = DBApi.login(phone, pwd);
        if (id == -1) {
            ToastUtils.showToast(ctx, "用户名或密码错误");
            return;
        }

        Intent it = new Intent(LoginActivity.this, MainActivity.class);
        it.putExtra("id", id);
        startActivity(it);
        finish();
    }
}
