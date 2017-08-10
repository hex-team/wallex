package team.hex.wallex.common;

import android.os.Environment;

import java.io.File;

import team.hex.wallex.R;
import team.hex.wallex.application.AppController;

/**
 * Created by alireza on 6/30/17.
 */

public class BuildConfigs {

    public static final String BASE_URL = "https://api.unsplash.com/";
    public static final String CLIENT_ID = "f9c91e0bb83cf8accdbebb944eaea684149054c76fc287311dc7f3e41b87f19e";
    public static final String DATE_FORMAT = "yyyy/MM/dd";

    public final static String MAIN_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + AppController.getContext().getResources().getString(R.string.app_name);

}
