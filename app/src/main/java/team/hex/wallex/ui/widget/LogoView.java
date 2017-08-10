package team.hex.wallex.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hex.abstractandroidutils.ui.UiUtils;

import team.hex.wallex.R;

/**
 * Created by alireza on 7/31/17.
 */

public class LogoView extends RelativeLayout {

    private int viewWidth, viewHeight;


    public LogoView(Context context) {
        super(context);
        init();
    }

    public LogoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LogoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    public LogoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        setBackgroundColor(Color.TRANSPARENT);
        ImageView logo = new ImageView(getContext());
        logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        logo.setImageResource(R.drawable.full_size_logo);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.LogoSize),
                getResources().getDimensionPixelSize(R.dimen.LogoSize));
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        if (!UiUtils.isTablet(getContext()))
            params.setMargins(0, (int) UiUtils.convertDpToPixel(100f, getContext()), 0, 0);
        else
            params.addRule(RelativeLayout.CENTER_VERTICAL);
        addView(logo, params);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
    }


    private Paint getFullPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }


    private Paint getRectPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setColor(Color.TRANSPARENT);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    private Rect getRect() {

        int rectWidth = getResources().getDimensionPixelSize(R.dimen.LogoSize);
        int rectHeight = rectWidth;

        return new Rect(((viewWidth - rectWidth) / 2),
               UiUtils.isTablet(getContext()) ? ((viewHeight - rectHeight) / 2) : (int) UiUtils.convertDpToPixel(100f, getContext()),
                ((viewWidth - rectWidth) / 2) + rectWidth, (UiUtils.isTablet(getContext()) ? ((viewHeight - rectHeight) / 2) : (int) UiUtils.convertDpToPixel(100f, getContext())) + rectHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(getFullPaint());
        canvas.drawRect(getRect(), getRectPaint());
    }
}
