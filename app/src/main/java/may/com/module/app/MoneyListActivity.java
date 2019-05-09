package may.com.module.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import may.com.module.R;
import may.com.module.bean.MangerMoneyAimModel;
import may.com.module.bean.MangerMoneyListModel;
import may.com.module.utils.DBApi;
import may.com.module.utils.MoneyListAdapter;

public class MoneyListActivity extends Activity implements View.OnClickListener {
    private TextView tv_title, tv_right, tv_left;
    private ListView mListView;
    private ArrayList<MangerMoneyListModel> mList;
    private MoneyListAdapter mListAdapter;
    private long id;
    private MangerMoneyAimModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_list);
        initView();
        initData();
    }

    public void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        mListView = (ListView) findViewById(R.id.list_view);
        tv_left = (TextView) findViewById(R.id.tv_left);
    }

    public void initData() {
        id = getIntent().getLongExtra("id", 0);
        tv_title.setText("历史存款记录");
        tv_right.setText("添加");
        tv_left.setText("预估时间");
        mList = new ArrayList<>();
        model = DBApi.getOneMangerMoneyAimModel(id);
        if (!TextUtils.isEmpty(model.getSaveMoneyList())) {
            mList = new Gson().fromJson(model.getSaveMoneyList(), new TypeToken<List<MangerMoneyListModel>>() {
            }.getType());
        } else {
            mList = new ArrayList<>();
        }

        mListAdapter = new MoneyListAdapter(mList, this);
        mListView.setAdapter(mListAdapter);
        tv_right.setOnClickListener(this);
        tv_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                Intent intent = new Intent(this, AddMoneyActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 1001);
                break;
            case R.id.tv_left:
                intent = new Intent(this, StatisticsMoneyActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            model =   DBApi.getOneMangerMoneyAimModel(id);
            if (!TextUtils.isEmpty(model.getSaveMoneyList())) {
                mList = new Gson().fromJson(model.getSaveMoneyList(), new TypeToken<List<MangerMoneyListModel>>() {
                }.getType());
            } else {
                mList = new ArrayList<>();
            }
            mListAdapter.setList(mList);
        }
    }
}
