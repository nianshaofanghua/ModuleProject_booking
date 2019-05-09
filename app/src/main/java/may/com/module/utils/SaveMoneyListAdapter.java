package may.com.module.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import may.com.module.R;
import may.com.module.bean.MangerMoneyListModel;
import may.com.module.bean.MangerMoneyAimModel;

/**
 * Created by Administrator on 2019/5/6.
 */

public class SaveMoneyListAdapter extends BaseAdapter {

    private ArrayList<MangerMoneyAimModel> mList;
    private Context mContext;

    public SaveMoneyListAdapter(ArrayList<MangerMoneyAimModel> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_save_money, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ArrayList<MangerMoneyListModel> list = null;
        if (!TextUtils.isEmpty(mList.get(position).getSaveMoneyList())) {
            list = new Gson().fromJson(mList.get(position).getSaveMoneyList(), new TypeToken<List<MangerMoneyListModel>>() {
            }.getType());

        } else {
            list = new ArrayList<>();
        }
        double saveMoney = 0;
        for (int i = 0; i < list.size(); i++) {
            saveMoney = CalcUtils.add(saveMoney, Double.valueOf(list.get(i).getMangerMoney()));
        }
        viewHolder.tv_aim_money.setText(mList.get(position).getSaveMoney() + "元");
        viewHolder.tv_time.setText(mList.get(position).getBeginTime() + "-" + mList.get(position).getEndTime());
        viewHolder.tv_save_money.setText(saveMoney + "元");
        if (saveMoney >= Double.valueOf(mList.get(position).getSaveMoney())) {
            viewHolder.tv_is_finish.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tv_is_finish.setVisibility(View.GONE);
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView tv_aim_money, tv_save_money, tv_time, tv_is_finish;

        public ViewHolder(View view) {
            tv_aim_money = (TextView) view.findViewById(R.id.tv_aim_money);
            tv_save_money = (TextView) view.findViewById(R.id.tv_save_money);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_is_finish = (TextView) view.findViewById(R.id.tv_is_finish);
        }
    }
}
