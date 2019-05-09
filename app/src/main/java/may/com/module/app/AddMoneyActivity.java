package may.com.module.app;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import may.com.module.R;
import may.com.module.bean.MangerMoneyAimModel;
import may.com.module.bean.MangerMoneyListModel;
import may.com.module.utils.BasisTimesUtils;
import may.com.module.utils.DBApi;
import may.com.module.utils.ToastUtils;

public class AddMoneyActivity extends Activity implements View.OnClickListener {
    private TextView tv_save;
    private EditText et_money_num, et_remarks;
    private long id;
    private MangerMoneyAimModel model;
    private boolean textChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        initView();
        initData();
    }

    public void initView() {
        tv_save = (TextView) findViewById(R.id.tv_save);
        et_money_num = (EditText) findViewById(R.id.et_money_num);
        et_remarks = (EditText) findViewById(R.id.et_remarks);

    }

    public void initData() {
        id = getIntent().getLongExtra("id", 0);
        model = DBApi.getOneMangerMoneyAimModel(id);
        tv_save.setOnClickListener(this);
        et_money_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!textChange) {
                    restrictText();
                }
                textChange = false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_money_num.getText().toString())) {
                    ToastUtils.showToast(this, "请输入金额");
                    return;
                }
                if (TextUtils.isEmpty(et_remarks.getText().toString())) {
                    ToastUtils.showToast(this, "请输入备注");
                    return;
                }
                String time = BasisTimesUtils.getDeviceTime();
                ArrayList<MangerMoneyListModel> list = null;
                if (!TextUtils.isEmpty(model.getSaveMoneyList())) {
                    list = new Gson().fromJson(model.getSaveMoneyList(), new TypeToken<List<MangerMoneyListModel>>() {
                    }.getType());
                } else {
                    list = new ArrayList<>();
                }
                list.add(new MangerMoneyListModel(et_money_num.getText().toString(), et_remarks.getText().toString(), time));
                String json = new Gson().toJson(list);
                model.setSaveMoneyList(json);
                model.save();
                setResult(1001);
                finish();
                break;
        }
    }

    private void restrictText() {
        String input = et_money_num.getText().toString();
        if (TextUtils.isEmpty(input)) {
            return;
        }
        if (input.contains(".")) {
            int pointIndex = input.indexOf(".");
            int totalLenth = input.length();
            int len = (totalLenth - 1) - pointIndex;
            if (len > 2) {
                input = input.substring(0, totalLenth - 1);
                textChange = true;
                et_money_num.setText(input);
                et_money_num.setSelection(input.length());
            }
        }

        if (input.toString().trim().substring(0).equals(".")) {
            input = "0" + input;
            et_money_num.setText(input);
            et_money_num.setSelection(2);
        }

    }

}
