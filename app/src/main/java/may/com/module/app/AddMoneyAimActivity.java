package may.com.module.app;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import may.com.module.R;
import may.com.module.bean.MangerMoneyAimModel;
import may.com.module.utils.BasisTimesUtils;
import may.com.module.utils.ToastUtils;

public class AddMoneyAimActivity extends Activity implements View.OnClickListener {

    private TextView tv_save, tv_begin_time, tv_end_time;
    private EditText et_money_num;
    private ImageView iv_back;
    private String beginTime;
    private String endTime;
    private int type;
    private long userId;
    private int beginTimeIsChose;
    private int endTimeIsChose;
private boolean textChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_aim);
        initView();
    }

    public void initView() {
        userId = getIntent().getLongExtra("userId", 0);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_begin_time = (TextView) findViewById(R.id.tv_begin_time);
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);
        et_money_num = (EditText) findViewById(R.id.et_money_num);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_save.setOnClickListener(this);
        tv_begin_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        iv_back.setOnClickListener(this);
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


    /**
     * 年月日选择
     */
    private void showYearMonthDayPicker() {
        BasisTimesUtils.showDatePickerDialog(this, BasisTimesUtils.THEME_HOLO_DARK, "请选择年月日", BasisTimesUtils.getYear(), BasisTimesUtils.getMonth(), 1, new BasisTimesUtils.OnDatePickerListener() {

            @Override
            public void onConfirm(int year, int month, int dayOfMonth) {
                //  toast(year + "-" + month + "-" + dayOfMonth);
                if (type == 0) {
                    beginTime = year + "-" + month + "-" + dayOfMonth;
                  //  tv_begin_time.setText(beginTime);

                } else {
                    endTime = year + "-" + month + "-" + dayOfMonth;

                   // tv_end_time.setText(beginTime);
                }
                showTimerPicker();


            }

            @Override
            public void onCancel() {
                // toast("cancle");
            }
        });
    }

    /**
     * 时间选择
     */
    private void showTimerPicker() {
        BasisTimesUtils.showTimerPickerDialog(this, true, "请选择时间", 00, 00, true, new BasisTimesUtils.OnTimerPickerListener() {
            @Override
            public void onConfirm(int hourOfDay, int minute) {
                ///   toast(hourOfDay + ":" + minute);
                if (type == 0) {
                    beginTime = beginTime + " " + hourOfDay + ":" + minute;
                    tv_begin_time.setText(beginTime);
                    beginTimeIsChose = 1;
                } else {

                    endTime = endTime + " " + hourOfDay + ":" + minute;
                    if (!TextUtils.isEmpty(beginTime)) {
                        long begin = BasisTimesUtils.getLongTime(beginTime);
                        long end = BasisTimesUtils.getLongTime(endTime);
                        if (end - begin < 0) {
                            ToastUtils.showToast(AddMoneyAimActivity.this, "结束时间必须大于开始时间");
                        } else {
                            endTimeIsChose = 1;
                            tv_end_time.setText(endTime);
                        }
                    }else {
                        ToastUtils.showToast(AddMoneyAimActivity.this, "请选择开始时间");
                    }


                }


            }

            @Override
            public void onCancel() {
                //   toast("cancle");
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_money_num.getText().toString())) {
                    ToastUtils.showToast(this, "请输入存款金额");
                    return;
                }
                if (beginTimeIsChose == 0 || endTimeIsChose == 0) {
                    ToastUtils.showToast(this, "请选择时间");
                    return;
                }
                MangerMoneyAimModel model = new MangerMoneyAimModel();
                model.setSaveMoneyList("");
                model.setUserId(userId);
                model.setSaveMoney(et_money_num.getText().toString());
                model.setBeginTime(beginTime);
                model.setEndTime(endTime);
                model.save();
                setResult(1);
                finish();

                break;
            case R.id.tv_begin_time:
                type = 0;
                showYearMonthDayPicker();
                break;
            case R.id.tv_end_time:
                type = 1;
                showYearMonthDayPicker();
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
            et_money_num. setSelection(2);
        }

    }


}
