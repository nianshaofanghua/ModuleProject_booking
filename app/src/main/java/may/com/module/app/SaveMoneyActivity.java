package may.com.module.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import may.com.module.R;
import may.com.module.bean.MangerMoneyAimModel;
import may.com.module.utils.DBApi;
import may.com.module.utils.SaveMoneyListAdapter;

public class SaveMoneyActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_add;
    private ListView mListView;
    private SaveMoneyListAdapter mListAdapter;
    private long userId;
    private ArrayList<MangerMoneyAimModel> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_money);
        initView();
        initData();
    }


    public void initView() {

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_add = (TextView) findViewById(R.id.tv_add);
        mListView = (ListView) findViewById(R.id.list_view);
        tv_add.setOnClickListener(this);

    }

    public void initData() {
        userId = getIntent().getLongExtra("userId", 0);
        mList = new ArrayList<>();
        mList.addAll(DBApi.getSaveMoneyList(userId));
        mListAdapter = new SaveMoneyListAdapter(mList, this);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tv_add:
                intent = new Intent(this,AddMoneyAimActivity.class);
                startActivityForResult(intent,1001);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001){
            mList.clear();
            mList.addAll(DBApi.getSaveMoneyList(userId));
            mListAdapter.notifyDataSetChanged();
        }
    }
}
