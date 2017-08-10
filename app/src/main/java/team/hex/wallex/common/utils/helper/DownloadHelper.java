package team.hex.wallex.common.utils.helper;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import team.hex.wallex.api.model.Photo;
import team.hex.wallex.ui.widget.PanoramaImageView;


/**
 * Created by alireza on 7/3/17.
 */

public class DownloadHelper {


    public void downloadRawImage(final Context context, Photo photo) {

    }


    public static void loadImage(final Context context, final PanoramaImageView imageView, final Photo photo, final LoadImageInterface loadImageInterface) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        imageView.setColorFilter(filter);
        Picasso.with(context).load(photo.getUrls().getSmall()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                Picasso.with(context).load(photo.getUrls().getRegular()).placeholder(imageView.getDrawable()).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        loadImageInterface.onSuccess();
                        ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
                        animation.setDuration(1000);
                        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                ColorMatrix matrix = new ColorMatrix();
                                matrix.setSaturation(animation.getAnimatedFraction());
                                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                                imageView.setColorFilter(filter);
                            }
                        });
                        animation.start();
                    }

                    @Override
                    public void onError() {
                        loadImageInterface.onError();
                    }
                });
            }

            @Override
            public void onError() {
                loadImageInterface.onError();
            }
        });
    }


    public interface LoadImageInterface {
        void onSuccess();

        void onError();
    }


}
