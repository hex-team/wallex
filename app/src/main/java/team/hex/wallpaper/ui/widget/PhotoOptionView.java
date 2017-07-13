package team.hex.wallpaper.ui.widget;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hex.abstractandroidutils.ui.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.hex.wallpaper.R;
import team.hex.wallpaper.api.model.Photo;

/**
 * Created by alireza on 7/1/17.
 */

public class PhotoOptionView extends RelativeLayout implements View.OnClickListener {

    @BindView(R.id.optionLayout)
    LinearLayout optionLayout;
    @BindView(R.id.btnShare)
    CustomButton btnShare;
    @BindView(R.id.btnSave)
    CustomButton btnSave;
    @BindView(R.id.btnWallpaper)
    CustomButton btnWallpaper;

    boolean show = false;

    private Photo photo = null;
    private PhotoOptionInterface photoOptionInterface;


    public PhotoOptionView(Context context) {
        super(context);
        init();
    }

    public PhotoOptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoOptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    public PhotoOptionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        View view = inflate(getContext(), R.layout.layout_photo_option, null);
        ButterKnife.bind(this, view);

        btnShare.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnWallpaper.setOnClickListener(this);

        btnWallpaper.animate().translationY(2000).setDuration(0);
        btnSave.animate().translationY(2000).setDuration(0);
        btnShare.animate().translationY(2000).setDuration(0);

        setGravity(Gravity.CENTER);

        addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    }

    public void setup(Photo photo, PhotoOptionInterface photoOptionInterface) {
        this.photo = photo;
        this.photoOptionInterface = photoOptionInterface;
    }


    public void showWithAnimation() {
        if (!show) {
            setVisibility(VISIBLE);
            AnimationUtils.fadeIn(optionLayout, 300, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    loadButtonSequentially();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            show = !show;
        }
    }


    public void show() {
        if (!show) {
            setVisibility(VISIBLE);
            optionLayout.setAlpha(1);
            btnShare.animate().translationY(0).setDuration(0).start();
            btnSave.animate().translationY(0).setDuration(0).start();
            btnWallpaper.animate().translationY(0).setDuration(0).start();
            show = !show;
        }
    }





    private void loadButtonSequentially() {
        btnShare.animate().translationY(0).setDuration(150).setInterpolator(new DecelerateInterpolator()).start();
        btnSave.animate().translationY(0).setDuration(150).setStartDelay(150).setInterpolator(new DecelerateInterpolator()).start();
        btnWallpaper.animate().translationY(0).setDuration(150).setStartDelay(300).setInterpolator(new DecelerateInterpolator()).start();
    }

    public void hideWithAnimation() {
        if (show) {
            AnimationUtils.fadeOut(optionLayout, 300, 0, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    setVisibility(GONE);
                    btnWallpaper.animate().translationY(2000).setDuration(0);
                    btnSave.animate().translationY(2000).setDuration(0);
                    btnShare.animate().translationY(2000).setDuration(0);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            show = !show;
        }
    }


    public void hide() {
        if (show) {
            optionLayout.setAlpha(0);
            setVisibility(GONE);
            btnWallpaper.animate().translationY(2000).setDuration(0);
            btnSave.animate().translationY(2000).setDuration(0);
            btnShare.animate().translationY(2000).setDuration(0);
            show = !show;
        }
    }







    public boolean isShow() {
        return show;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnShare: {
                photoOptionInterface.onShare(photo);
            }
            break;
            case R.id.btnSave: {
                photoOptionInterface.onSave(photo);
            }
            break;
            case R.id.btnWallpaper: {
                photoOptionInterface.onWallpaper(photo);
            }
            break;
        }
    }


    public interface PhotoOptionInterface {
        void onShare(Photo photo);
        void onSave(Photo photo);
        void onWallpaper(Photo photo);
    }
}
