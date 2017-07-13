package team.hex.wallpaper.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import team.hex.wallpaper.R;
import team.hex.wallpaper.common.utils.Font;


/**
 * Created by alireza on 7/3/17.
 */

public class CustomTextView extends AppCompatTextView {

    Font.Fonts font;

    public CustomTextView(Context context) {
        super(context);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithAttrs(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithAttrs(context, attrs);
    }



    private void init(Context context) {
        setTypeface(Font.fontBuilder(Font.Fonts.IRANSans_UltraLight, context));
    }

    private void initWithAttrs(Context context , AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView, 0, 0);
        try {
            switch (ta.getInt(R.styleable.CustomTextView_font, 22)) {
                case 0: {
                    this.font = Font.Fonts.IRANSans;
                }
                break;
                case 1: {
                    this.font = Font.Fonts.IRANSans_Light;
                }
                break;
                case 2: {
                    this.font = Font.Fonts.IRANSans_UltraLight;
                }
                break;
                case 3: {
                    this.font = Font.Fonts.IRANSans_Medium;
                }
                break;
                case 4: {
                    this.font = Font.Fonts.IRANSans_Bold;
                }
                break;
                default: this.font = null;
            }
        } finally {
            if(this.font != null)
                this.setTypeface(Font.fontBuilder(font, getContext()));
            ta.recycle();
        }
    }

    public void setFont(Font.Fonts font, boolean isBold) {
        this.setTypeface(Font.fontBuilder(font, getContext()), isBold ? Typeface.BOLD : Typeface.NORMAL);
    }

}

