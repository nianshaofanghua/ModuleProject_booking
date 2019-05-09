package may.com.module.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import may.com.module.R;
import may.com.module.bean.User;
import may.com.module.utils.DBApi;
import may.com.module.utils.ToastUtils;


public class RegisterActivity extends Activity {
    private Context ctx;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ctx = this;
        initTop();
        initView();
    }

    private EditText et_account, et_pwd;



    private void initView() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    //注册
    private void register() {
        String account = et_account.getText().toString().trim();
        String pwd = et_pwd.getText().toString();
        if ("".equals(account)) {
            ToastUtils.showToast(ctx, "用户名不能为空");
            return;
        }

        if ("".equals(pwd)) {
            ToastUtils.showToast(ctx, "密码不能为空");
            return;
        }


        User user = new User();
        user.setAccount(account);
        user.setPassword(pwd);

        long id = DBApi.register(user);
        if (id == -1) {
            ToastUtils.showToast(ctx, "该用户已注册！");
        } else {
            ToastUtils.showToast(ctx, "注册成功！");
            Intent it = new Intent(RegisterActivity.this, MainActivity.class);
            it.putExtra("id", id);
            startActivity(it);
            LoginActivity.instance.finish();
            finish();
        }
    }

    private void initTop() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.back).setVisibility(View.VISIBLE);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("注册");
    }
}
