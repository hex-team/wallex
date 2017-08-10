package team.hex.wallex.common.utils.helper;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import team.hex.wallex.api.model.Collection;
import team.hex.wallex.api.model.Photo;
import team.hex.wallex.application.AppController;
import team.hex.wallex.common.BuildConfigs;
import team.hex.wallex.common.utils.animator.FadeAnimator;
import team.hex.wallex.common.utils.ModernAsyncTask;
import team.hex.wallex.ui.widget.PanoramaImageView;

/**
 * Created by alireza on 7/4/17.
 */

public class ImageHelper {



    public static void loadRegularPhoto(Context context, final PanoramaImageView view, Photo photo,
                                        @Nullable OnLoadImageListener l) {
        if (photo != null && photo.getUrls() != null
                && photo.getWidth() != 0 && photo.getHeight() != 0) {
            DrawableRequestBuilder<String> smallRequest = Glide
                    .with(context)
                    .load(photo.getUrls().getSmall())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                                   boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                       boolean isFromMemoryCache, boolean isFirstResource) {
                            view.setEnabled(true);
                            return false;
                        }
                    });
            if (l != null && !photo.isHasFadedIn() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             /*   ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                view.setColorFilter(filter);
                view.setEnabled(false);*/
            }
            loadImage(
                    context, view,
                    photo.getUrls().getRegular().replace("w=1080" , "w=1600"), photo.getRegularWidth(), photo.getRegularHeight(), false, false,
                    l == null ? null : smallRequest, null, l == null ? null : new FadeAnimator(),
                    l);
        }
    }

    public static void loadFullPhoto(Context context, PanoramaImageView view, String url, String thumbnail,
                                     @Nullable OnLoadImageListener l) {
        DrawableRequestBuilder<String> thumbnailRequest = Glide
                .with(context)
                .load(thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE);
        loadImage(context, view, url, 0, 0, false, false, thumbnailRequest, null, null, l);
    }

    public static void loadPhoto(Context context, PanoramaImageView view, String url, boolean lowPriority,
                                 @Nullable OnLoadImageListener l) {
        loadImage(context, view, url, 0, 0, false, lowPriority, null, null, null, l);
    }

    // collection cover.

    public static void loadCollectionCover(Context context, PanoramaImageView view, Collection collection,
                                           @Nullable OnLoadImageListener l) {
        if (collection != null) {
            loadRegularPhoto(context, view, collection.getCover_photo(), l);
        }
    }

    // avatar.

   /* public static void loadAvatar(Context context, ImageView view, User user,
                                  @Nullable OnLoadImageListener l) {
        if (user != null && user.profile_image != null) {
            loadAvatar(context, view, user.profile_image.large, l);
        } else {
            loadImage(
                    context, view,
                    R.drawable.default_avatar, 128, 128, false,
                    new CircleTransformation(context),
                    l);
        }
    }

    public static void loadAvatar(Context context, ImageView view, @NotNull String url,
                                  @Nullable OnLoadImageListener l) {
        DrawableRequestBuilder<Integer> thumbnailRequest = Glide.with(context)
                .load(R.drawable.default_avatar)
                .override(128, 128)
                .transform(new CircleTransformation(context))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE);
        loadImage(
                context, view,
                url, 128, 128, false, false,
                thumbnailRequest, new CircleTransformation(context), null,
                l);
    }

    // icon.

    public static void loadIcon(Context context, ImageView view, int resId) {
        loadImage(context, view, resId, 0, 0, true, null, null);
    }
*/
    // builder.

    private static void loadImage(Context context, PanoramaImageView view,
                                  String url, int width, int height, boolean dontAnimate, boolean lowPriority,
                                  @Nullable DrawableRequestBuilder thumbnail,
                                  @Nullable BitmapTransformation transformation,
                                  @Nullable ViewPropertyAnimation.Animator animator,
                                  @Nullable final OnLoadImageListener l) {
        DrawableRequestBuilder<String> builder = Glide
                .with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e,
                                               String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (l != null) {
                            l.onLoadFailed();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        if (l != null) {
                            l.onLoadSucceed();
                        }
                        return false;
                    }
                });
        if (width != 0 && height != 0) {
            builder.override(width, height);
        }
        if (dontAnimate) {
            builder.dontAnimate();
        }
        if (lowPriority) {
            builder.priority(Priority.LOW);
        } else {
            builder.priority(Priority.NORMAL);
        }
        if (thumbnail != null) {
            builder.thumbnail(thumbnail);
        }
        if (transformation != null) {
            builder.transform(transformation);
        }
        if (animator != null) {
            builder.animate(animator);
        }
        builder.into(view);
    }

    private static void loadImage(Context context, PanoramaImageView view,
                                  int resId, int width, int height, boolean dontAnimate,
                                  @Nullable BitmapTransformation transformation,
                                  @Nullable final OnLoadImageListener l) {
        DrawableRequestBuilder<Integer> builder = Glide
                .with(context)
                .load(resId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<Integer, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e,
                                               Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (l != null) {
                            l.onLoadFailed();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        if (l != null) {
                            l.onLoadSucceed();
                        }
                        return false;
                    }
                });
        if (width != 0 && height != 0) {
            builder.override(width, height);
        }
        if (dontAnimate) {
            builder.dontAnimate();
        }
        if (transformation != null) {
            builder.transform(transformation);
        }
        builder.into(view);
    }

    public static void loadBitmap(Context context, Target<Bitmap> target, Bitmap bitmap) {
        Glide.with(context)
                .load(bitmapToBytes(bitmap))
                .asBitmap()
                .into(target);
    }

    public static void loadImage(Context context, ImageView view, Bitmap bitmap) {
        Glide.with(context)
                .load(bitmapToBytes(bitmap))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view);
    }

    private static byte[] bitmapToBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    // animation.

    /**
     * Execute a saturation animation to make a image from white and black into color.
     *
     * @param c      Context.
     * @param target ImageView which will execute saturation animation.
     * */


    /**
     * Release the {@link ImageView} from {@link Glide}.
     * A ViewHolder in {@link android.support.v7.widget.RecyclerView} need to call this method in
     * {@link android.support.v7.widget.RecyclerView.Adapter#onViewRecycled(RecyclerView.ViewHolder)}.
     * Otherwise, there might be a OOM problem.
     *
     * @param view The ImageView to be released.
     * */
    public static void releaseImageView(ImageView view) {
        Glide.clear(view);
    }

    // interface.

    // on load image listener.

    public interface OnLoadImageListener {
        void onLoadSucceed();
        void onLoadFailed();
    }



    public static void startSaturationAnimation(final PanoramaImageView imageView) {
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




    public static float calculateAspectRatio(float width, float height) {
        return truncateDecimal(width/height, 1).floatValue();
    }

    public static BigDecimal truncateDecimal(float x, int numberofDecimals) {
        if ( x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }

    public static void saveBitmap(final ImageView imageView, final Photo photo, final BitmapInterface bitmapInterface) {

        new ModernAsyncTask<Void, Void, File>() {
            @Override
            protected File doInBackground(Void... params) {
                String root = BuildConfigs.MAIN_PATH;
                File myDir = new File(root);
                myDir.mkdirs();
                String fname = "photo-" + photo.getId() + ".jpg";
                File file = new File(myDir, fname);
                if (file.exists())
                    file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    ((GlideBitmapDrawable) imageView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    //add to gallery
                    MediaScannerConnection.scanFile(AppController.getContext(),
                            new String[] { file.toString() }, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> uri=" + uri);
                                }
                            });
                    return file;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(File file) {
                if (file != null) {
                    bitmapInterface.onSaveBitmap(file);
                } else
                    bitmapInterface.onErrorSaveBitmap(photo.getId());
            }
        }.execute();

    }


    public static Bitmap getResizedBitmap(ImageView imageView, Photo photo) {
        Bitmap bm = ((GlideBitmapDrawable) imageView.getDrawable()).getBitmap();
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) 1920) / width;
        float scaleHeight = ((float) ((int) (1.0 * photo.getHeight() * 1920 / photo.getWidth()))) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }



    public interface BitmapInterface {
        void onSaveBitmap(File file);
        void onErrorSaveBitmap(String name);
    }


}
