package team.hex.wallpaper.application;

import android.app.Application;
import android.content.Context;


/**
 * Created by alireza on 6/30/17.
 */

public class AppController extends Application {


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
