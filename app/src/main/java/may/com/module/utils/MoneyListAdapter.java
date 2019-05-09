package may.com.module.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import may.com.module.R;
import may.com.module.bean.MangerMoneyListModel;

/**
 * Created by Administrator on 2019/5/9.
 */

public class MoneyListAdapter extends BaseAdapter {
    private ArrayList<MangerMoneyListModel> mList;
    private Context mContext;

    public MoneyListAdapter(ArrayList<MangerMoneyListModel> list, Context context) {
        mList = list;
        mContext = context;
    }
public void setList(ArrayList<MangerMoneyListModel> list){
        mList = list;
        notifyDataSetChanged();
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
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_addmoney_list,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_money.setText(mList.get(position).getMangerMoney()+"元");
        viewHolder.tv_remark.setText(mList.get(position).getRemarks());
        viewHolder.tv_time.setText(mList.get(position).getTime());
        return convertView;
    }

    private class ViewHolder {
        TextView tv_money, tv_time, tv_remark;

        public ViewHolder(View view) {
            tv_money = (TextView) view.findViewById(R.id.tv_money);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_remark = (TextView) view.findViewById(R.id.tv_remark);
        }
    }
}
