package may.com.module.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import may.com.module.R;


/**
 * Created by Administrator on 2019/4/19.
 */

public class DialogDeleteBuyCar extends Dialog{

    private RelativeLayout relativeLayout;
    private Context context;

    private SelectedItem selectedItem;


    public DialogDeleteBuyCar(Context context, SelectedItem selectedItem) {
        super(context, R.style.style_dialog);
        this.context = context;
        this.selectedItem = selectedItem;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_remind);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getViews();
    }

    private void getViews() {
        TextView tv_ok = (TextView) findViewById(R.id.tv_ok);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItem!=null){
                    dismiss();
                    selectedItem.selectedPosition(0);
                }
            }
        });



        findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
