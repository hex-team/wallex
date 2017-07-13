package com.hex.abstractandroidutils.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.AnimRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

/**
 * Created by alireza on 3/13/17.
 */

public class AnimationUtils {

    public static class ResizeAnimation extends Animation {
        private View mView;
        private float mToHeight;
        private float mFromHeight;

        private float mToWidth;
        private float mFromWidth;

        public ResizeAnimation(View v, float fromWidth, float fromHeight, float toWidth, float toHeight , int duration) {
            mToHeight = toHeight;
            mToWidth = toWidth;
            mFromHeight = fromHeight;
            mFromWidth = fromWidth;
            mView = v;
            setDuration(duration);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            float height =
                    (mToHeight - mFromHeight) * interpolatedTime + mFromHeight;
            float width = (mToWidth - mFromWidth) * interpolatedTime + mFromWidth;
            ViewGroup.LayoutParams p = mView.getLayoutParams();
            p.height = (int) height;
            p.width = (int) width;
            mView.requestLayout();
        }
    }


    public static void resizeAnimation(View view, int targetHeight, int duration) {
        com.hex.abstractandroidutils.helper.ResizeAnimation resizeAnimation = new com.hex.abstractandroidutils.helper.ResizeAnimation(view, targetHeight, view.getHeight());
        resizeAnimation.setDuration(duration);
        view.startAnimation(resizeAnimation);
    }

    public static void resizeAnimation(View view, int targetHeight, int duration, Animation.AnimationListener listener) {
        com.hex.abstractandroidutils.helper.ResizeAnimation resizeAnimation = new com.hex.abstractandroidutils.helper.ResizeAnimation(view, targetHeight, view.getHeight());
        resizeAnimation.setDuration(duration);
        resizeAnimation.setAnimationListener(listener);
        view.startAnimation(resizeAnimation);
    }


    public static void fadeOut(View view, int duration, int startDelay, Animator.AnimatorListener listener) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, "alpha",  1, 0);
        fadeOut.setDuration(duration);
        fadeOut.setStartDelay(startDelay);
        if(listener != null)
            fadeOut.addListener(listener);
        fadeOut.start();
    }


    public static void fadeIn(View view, int duration, Animator.AnimatorListener listener) {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha",  0, 1);
        fadeIn.setDuration(duration);
        if(listener != null)
            fadeIn.addListener(listener);
        fadeIn.start();
    }




    public static void resAnimationLoader(@AnimRes int animResId, View view) {
        final Animation anim = android.view.animation.AnimationUtils.loadAnimation(view.getContext(), animResId);
        view.startAnimation(anim);
    }

    public static Animation resAnimationLoader(@AnimRes int animResId, Context context) {
        return android.view.animation.AnimationUtils.loadAnimation(context, animResId);
    }





}
