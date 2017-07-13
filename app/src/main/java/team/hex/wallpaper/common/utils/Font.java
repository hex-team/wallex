package team.hex.wallpaper.common.utils;

import android.content.Context;

/**
 * Created by alireza on 7/3/17.
 */

public class Font {


    private static final String FONT_PATH = "fonts/";
    private static final String FONT_SUFFIX = ".ttf";

    public enum Fonts {
        IRANSans,IRANSans_Bold,IRANSans_Light,IRANSans_Medium,IRANSans_UltraLight
    }

    public static android.graphics.Typeface fontBuilder(Fonts typeface, Context context) {

        return android.graphics.Typeface.createFromAsset(context.getAssets() , FONT_PATH + typeface.name() + FONT_SUFFIX);

    }


}
