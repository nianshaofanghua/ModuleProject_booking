package may.com.module.f2;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import may.com.module.R;
import may.com.module.app.AddMoneyAimActivity;
import may.com.module.app.MoneyListActivity;
import may.com.module.bean.MangerMoneyAimModel;
import may.com.module.utils.DBApi;
import may.com.module.utils.SaveMoneyListAdapter;

public class Fragment2 extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    private View rootView;
    private Context ctx;
    private ListView mListView;
    private SaveMoneyListAdapter mListAdapter;
    private long userId;
    private ArrayList<MangerMoneyAimModel> mList;
    private TextView tvTitle, tv_right;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTopView();
    }

    private void initTopView() {
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tv_right = (TextView) rootView.findViewById(R.id.tv_right);
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        initData();

    }

    public void initData() {
        tvTitle.setText(R.string.f2);
        tv_right.setText("添加");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(this);
        mList = new ArrayList<>();
        mList.addAll(DBApi.getSaveMoneyList(userId));
        mListAdapter = new SaveMoneyListAdapter(mList, getActivity());
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(this);





    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(ctx).inflate(R.layout.fragment_f2, null);
        return rootView;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), MoneyListActivity.class);
        intent.putExtra("id", mList.get(position).getId());
        startActivityForResult(intent, 1001);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_right:
                intent = new Intent(getActivity(), AddMoneyAimActivity.class);
                intent.putExtra("userId",userId);
                startActivityForResult(intent, 1001);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            mList.clear();
            mList.addAll(DBApi.getSaveMoneyList(userId));
            mListAdapter.notifyDataSetChanged();
        }
    }
}
