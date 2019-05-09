package may.com.module.app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import may.com.module.R;
import may.com.module.bean.MangerMoneyAimModel;
import may.com.module.bean.MangerMoneyListModel;
import may.com.module.utils.BasisTimesUtils;
import may.com.module.utils.CalcUtils;
import may.com.module.utils.DBApi;
import may.com.module.utils.MoneyListAdapter;
import may.com.module.utils.ToastUtils;

public class StatisticsMoneyActivity extends Activity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private MoneyListAdapter mListAdapter;
    private ArrayList<MangerMoneyListModel> mList;
    private long id;
    private TextView et_start, et_end;
    private TextView tv_result;
    private int type;
    private String beginTime;
    private String endTime;
    private int alarm_hour = 0;
    private int alarm_minute = 0;
    private int alarm_year = 0;
    private int alarm_month = 0;
    private int alarm_day = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satistics_money);
        initView();
    }

    public void initView() {
        id = getIntent().getLongExtra("id", 0);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statistics();
            }
        });
        et_start = (TextView) findViewById(R.id.et_start);
        et_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(1);
            }
        });
        et_end = (TextView) findViewById(R.id.et_end);
        et_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(2);
            }
        });
        tv_result = (TextView) findViewById(R.id.tv_result);

        ListView lv = (ListView) findViewById(R.id.listView);
        mList = new ArrayList<>();
        mListAdapter = new MoneyListAdapter(mList, this);
        lv.setAdapter(mListAdapter);
    }

    private void showTimeDialog(int type) {
        this.type = type;
        new TimePickerDialog(StatisticsMoneyActivity.this, this, BasisTimesUtils.getHour(), BasisTimesUtils.getMinute(), true).show();
        new DatePickerDialog(StatisticsMoneyActivity.this, this, BasisTimesUtils.getYear(), BasisTimesUtils.getMonth(), BasisTimesUtils.getDay()).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        alarm_hour = hourOfDay;
        alarm_minute = minute;
        String alarm = alarm_year + "-" + alarm_month + "-" + alarm_day + " " + alarm_hour + ":" + alarm_minute + ":00";

        if (type == 1) {
            beginTime = alarm;
            et_start.setText(alarm);
        } else {
            endTime = alarm;
            et_end.setText(alarm);
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        alarm_year = year;
        alarm_month = month + 1;
        alarm_day = dayOfMonth;
    }

    public void statistics() {
        if (TextUtils.isEmpty(beginTime)) {
            ToastUtils.showToast(this, "请选择开始时间");
            return;
        }
        if (TextUtils.isEmpty(endTime)) {
            ToastUtils.showToast(this, "请选择结束时间");
            return;
        }
        mList.clear();
        long startTime = parseTime(beginTime);
        long overTime = parseTime(endTime);
        if (startTime > overTime) {
            ToastUtils.showToast(this, "开始时间不能大于结束时间");
            return;
        }
        MangerMoneyAimModel model = DBApi.getOneMangerMoneyAimModel(id);
        ArrayList<MangerMoneyListModel> list;
        if (!TextUtils.isEmpty(model.getSaveMoneyList())) {
            list = new Gson().fromJson(model.getSaveMoneyList(), new TypeToken<List<MangerMoneyListModel>>() {
            }.getType());
        } else {
            list = new ArrayList<>();
        }
        double money = 0;
        for (int i = 0; i < list.size(); i++) {
            long nowTime = parseTime(list.get(i).getTime());
            if (startTime < nowTime && nowTime < overTime) {
                money = CalcUtils.add(money, Double.valueOf(list.get(i).getMangerMoney()));
                mList.add(list.get(i));

            }
        }
        tv_result.setText("已存入金额:" + money + "");
        mListAdapter.notifyDataSetChanged();

    }

    public long parseTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = format.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
