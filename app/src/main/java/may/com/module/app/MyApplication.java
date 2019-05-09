package may.com.module.app;

import android.app.Application;

import com.orm.SugarContext;


public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
