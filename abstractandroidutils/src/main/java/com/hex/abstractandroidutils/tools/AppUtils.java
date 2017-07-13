package com.hex.abstractandroidutils.tools;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by alireza on 3/13/17.
 */

public class AppUtils {




    public static boolean isScreenOrientationLocked(Context context) {
        return android.provider.Settings.System.getInt(
                context.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0
        ) != 1;
    }


    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    public static void enableBroadcastReceiver(Activity activity, Class<? extends BroadcastReceiver> broadcastClass) {
        PackageManager pm  = activity.getPackageManager();
        ComponentName componentName = new ComponentName(activity, broadcastClass);
        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }


    public static void disableBroadcastReceiver(Activity activity, Class<? extends BroadcastReceiver> broadcastClass) {
        PackageManager pm  = activity.getPackageManager();
        ComponentName componentName = new ComponentName(activity, broadcastClass);
        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
