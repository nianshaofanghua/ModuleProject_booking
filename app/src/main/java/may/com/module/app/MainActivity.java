package may.com.module.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import may.com.module.R;
import may.com.module.f1.Fragment1;
import may.com.module.f2.Fragment2;
import may.com.module.f3.Fragment3;
import may.com.module.utils.DBApi;
import may.com.module.utils.DisplayUtil;

public class MainActivity extends Activity {
    public static long id;
    public static String name;
    private FragmentManager mFragsManager;
    private Fragment1 f1;
    private Fragment2 f2;
    private Fragment3 f3;

    public static String mainColorResource ="#008577";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = getIntent().getLongExtra("id", 0);
        name = DBApi.getUsr(id).getAccount();
        initButton();
        initFrags();
    }


    private void initFrags() {
        f1 = new Fragment1();
        f2 = new Fragment2();
        f3 = new Fragment3();
        f2.setUserId(id);
        mFragsManager = getFragmentManager();
        FragmentTransaction ft = mFragsManager.beginTransaction();
        ft.add(R.id.fl_content, f1);
        ft.add(R.id.fl_content, f2);
        ft.add(R.id.fl_content, f3);

        hideAll(ft);
        ft.show(f1);
        ft.commit();
    }

    //初始化底部按钮
    public void initButton() {
        RadioGroup rg = (RadioGroup) findViewById(R.id.rg_bottom);
        rg.setOnCheckedChangeListener(onCheckedChangeListener);

        RadioButton radioBtns[] = {(RadioButton) findViewById(R.id.rb_1),
                (RadioButton) findViewById(R.id.rb_2),
                (RadioButton) findViewById(R.id.rb_3),
        };
        Drawable drawables[];
        for (int i = 0; i < radioBtns.length; i++) {
            drawables = radioBtns[i].getCompoundDrawables();//通过RadioButton的getCompoundDrawables()方法，拿到图片的drawables,分别是左上右下的图片

            drawables[1].setBounds(0, 0, DisplayUtil.dip2px(this, 25),
                    DisplayUtil.dip2px(this, 25));


            radioBtns[i].setCompoundDrawables(drawables[0], drawables[1], drawables[2],
                    drawables[3]);//将改变了属性的drawable再重新设置回去
        }
        radioBtns[0].setChecked(true);
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            hideFrags(checkedId);
        }
    };

    private void hideAll(FragmentTransaction ft) {
        ft.hide(f1);
        ft.hide(f2);
        ft.hide(f3);

    }

    private void hideFrags(int id) {
        FragmentTransaction ft = mFragsManager.beginTransaction();
        hideAll(ft);

        switch (id) {
            case R.id.rb_1:
                ft.show(f1);
                break;
            case R.id.rb_2:
                ft.show(f2);
                break;
            case R.id.rb_3:
                ft.show(f3);
                break;
            default:
                break;

        }
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1001){
            f2.onActivityResult(requestCode,resultCode,data);
        }
    }
}
