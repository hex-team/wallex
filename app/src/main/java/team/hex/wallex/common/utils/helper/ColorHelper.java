package team.hex.wallex.common.utils.helper;

import android.graphics.Color;

/**
 * Created by alireza on 7/1/17.
 */

public class ColorHelper {

    public static int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
