package team.hex.wallex.ui.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hex.abstractandroidutils.ui.UiUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.hex.wallex.R;
import team.hex.wallex.abstracts.AbstractRecyclerViewAdapter;
import team.hex.wallex.api.model.Photo;
import team.hex.wallex.common.MessageEvent;
import team.hex.wallex.common.utils.CustomTypefaceSpan;
import team.hex.wallex.common.utils.Font;
import team.hex.wallex.common.utils.helper.AppHelper;
import team.hex.wallex.common.utils.helper.ImageHelper;
import team.hex.wallex.common.utils.helper.ColorHelper;
import team.hex.wallex.common.utils.GyroscopeObserver;
import team.hex.wallex.ui.activity.MainActivity;
import team.hex.wallex.ui.widget.PanoramaImageView;
import team.hex.wallex.ui.widget.PhotoOptionView;

/**
 * Created by alireza on 6/30/17.
 */

public class PhotoAdapter extends AbstractRecyclerViewAdapter<Photo> {
    private Context context;
    private GyroscopeObserver gyroscopeObserver;
    PhotoAdapterInterface photoAdapterInterface;

    int viewHeight = 1;

    public PhotoAdapter(Context context, List<Photo> mDataSet, GyroscopeObserver gyroscopeObserver, PhotoAdapterInterface photoAdapterInterface) {
        super(mDataSet);
        this.context = context;
        this.gyroscopeObserver = gyroscopeObserver;
        this.photoAdapterInterface = photoAdapterInterface;
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        return new PhotoAdapter.PhotoViewHolder(inflater.inflate(R.layout.layout_photo_item, parent, false));
    }


    class PhotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photoView)
        PanoramaImageView photoView;
        @BindView(R.id.photoLoading)
        AVLoadingIndicatorView photoLoading;
        @BindView(R.id.photoOption)
        PhotoOptionView photoOption;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.profileContainer)
        LinearLayout profileContainer;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void onBindView(final int position) {

            final boolean isScalable = ImageHelper.calculateAspectRatio(getItem(position).getRegularWidth(),
                    getItem(position).getRegularHeight()) != UiUtils.getScreenAspectRatio(context);

            if (getItem(position).getUser().getName() != null) {
                SpannableString text = new SpannableString(getItem(position).getUser().getName() + ((getItem(position).getUser().getLocation() != null) ? (" / " + getItem(position).getUser().getLocation()) : ""));
                text.setSpan(new RelativeSizeSpan(1.0f), 0, getItem(position).getUser().getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                text.setSpan(new CustomTypefaceSpan("", Font.fontBuilder(Font.Fonts.IRANSans_Bold, context)), 0, getItem(position).getUser().getName().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                if (getItem(position).getUser().getLocation() != null) {
                    text.setSpan(new RelativeSizeSpan(0.6f), getItem(position).getUser().getName().length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new CustomTypefaceSpan("", Font.fontBuilder(Font.Fonts.IRANSans_UltraLight, context)), getItem(position).getUser().getName().length(), text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                }
                txtName.setText(text);
                txtName.setSelected(true);
            }

            photoView.setGyroscopeObserver(gyroscopeObserver);
            photoView.setEnablePanoramaMode(true);

            profileContainer.setPadding((int) UiUtils.convertDpToPixel(15, context), 0, 0, (int) UiUtils.convertDpToPixel(15, context));

            if (getItem(position).isLoadPhotoSuccess()) {
                photoView.setEnableScrollbar(true);
                photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                photoView.setInvertScrollDirection(false);
                if (isScalable)
                    profileContainer.setPadding((int) UiUtils.convertDpToPixel(15, context), 0, 0, (int) UiUtils.convertDpToPixel(35, context));
            } else {
                photoView.setEnableScrollbar(false);
                photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                photoView.setInvertScrollDirection(false);
            }


            if (!isScalable) {
                photoView.setGyroscopeObserver(null);
                photoView.setEnablePanoramaMode(false);
                photoView.setEnableScrollbar(false);
                photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                photoView.setInvertScrollDirection(false);
            }

            photoView.setOnPanoramaScrollListener(new PanoramaImageView.OnPanoramaScrollListener() {
                @Override
                public void onScrolled(PanoramaImageView view, float offsetProgress) {

                }
            });

            photoView.setBackgroundColor(ColorHelper.adjustAlpha(Color.parseColor(getItem(position).getColor()), 0.6f));
            photoOption.setup(getItem(position), new PhotoOptionView.PhotoOptionInterface() {
                @Override
                public void onShare(Photo photo) {
                    if (((MainActivity) context).checkStoragePermission(context, MainActivity.UserAction.share, new MainActivity.ActivityInterface() {
                        @Override
                        public void onPermissionResult(MainActivity.UserAction userAction) {
                            share(position);
                        }
                    })) {
                        share(position);
                    }
                }

                @Override
                public void onSave(final Photo photo) {
                    if (((MainActivity) context).checkStoragePermission(context, MainActivity.UserAction.save, new MainActivity.ActivityInterface() {
                        @Override
                        public void onPermissionResult(MainActivity.UserAction userAction) {
                            save(photoView, photo, position);
                        }
                    })) {
                        save(photoView, photo, position);
                    }
                }

                @Override
                public void onWallpaper(final Photo photo) {
                    if (((MainActivity) context).checkStoragePermission(context, MainActivity.UserAction.wallpaper, new MainActivity.ActivityInterface() {
                        @Override
                        public void onPermissionResult(MainActivity.UserAction userAction) {
                            setWallpaper(photoView, photo, position);
                        }
                    })) {
                        setWallpaper(photoView, photo, position);
                    }
                }
            });


            photoLoading.setVisibility(View.VISIBLE);


            ImageHelper.loadRegularPhoto(context, photoView, getItem(position), new ImageHelper.OnLoadImageListener() {
                @Override
                public void onLoadSucceed() {
                    if(position == 0 && !getItem(position).isLoadPhotoSuccess())
                        photoAdapterInterface.onFirstPhotoLoad();
                    getItem(position).setLoadPhotoSuccess(true);
                    photoLoading.setVisibility(View.GONE);
                    if (!getItem(position).isHasFadedIn()) {
                        getItem(position).setHasFadedIn(true);
                        if (isScalable) {
                            photoView.setGyroscopeObserver(gyroscopeObserver);
                            photoView.setEnablePanoramaMode(true);
                            photoView.setEnableScrollbar(true);
                            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            photoView.setInvertScrollDirection(false);

                            ValueAnimator animator = ValueAnimator.ofInt((int) UiUtils.convertDpToPixel(15, context), (int) UiUtils.convertDpToPixel(35, context));
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    profileContainer.setPadding((int) UiUtils.convertDpToPixel(15, context), 0, 0, (Integer) valueAnimator.getAnimatedValue());
                                }
                            });
                            animator.setDuration(200);
                            animator.start();
                        }
                    }
                }

                @Override
                public void onLoadFailed() {
                    // do nothing.
                    photoLoading.setVisibility(View.GONE);
                }
            });


            if (getItem(position).isOnOption()) {
                photoOption.show();
            } else {
                photoOption.hide();
            }

            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (!getItem(position).isOnOption() && getItem(position).isLoadPhotoSuccess()) {
                        photoOption.showWithAnimation();
                        getItem(position).setOnOption(true);
                        photoAdapterInterface.onOption(true, getAdapterPosition() != 0 ? getAdapterPosition() - 1 : 0);
                    }
                    return true;
                }
            });

            photoOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getItem(position).isOnOption()) {
                        photoOption.hideWithAnimation();
                        getItem(position).setOnOption(false);
                        photoAdapterInterface.onOption(false, getAdapterPosition() != 0 ? getAdapterPosition() - 1 : 0);
                    }
                }
            });


            profileContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getResources().getString(R.string.unsplashUrl) +
                            "@" + getItem(position).getUser().getUsername() + "?utm_source=hexteam&utm_medium=referral&utm_campaign=api-credit"));
                    context.startActivity(browserIntent);
                }
            });
        }


        private void share(int position) {
            photoOption.hideWithAnimation();
            getItem(position).setOnOption(false);
            photoAdapterInterface.onOption(false, getAdapterPosition() != 0 ? getAdapterPosition() - 1 : 0);
            AppHelper.shareLink(context, getItem(position).getLinks().getHtml());
        }


        private void setWallpaper(PanoramaImageView photoView, Photo photo, int position) {
            AppHelper.setAsWallpaper(photoView, photo, new AppHelper.AppHelperInterface() {
                @Override
                public void onSetAsWallpaper() {

                }

                @Override
                public void onSetAsWallpaperFailed() {

                }
            });
            photoOption.hideWithAnimation();
            getItem(position).setOnOption(false);
            photoAdapterInterface.onOption(false, getAdapterPosition() != 0 ? getAdapterPosition() - 1 : 0);
        }

        private void save(PanoramaImageView photoView, Photo photo, int position) {
            ImageHelper.saveBitmap(photoView, photo, new ImageHelper.BitmapInterface() {
                @Override
                public void onSaveBitmap(File file) {

                }

                @Override
                public void onErrorSaveBitmap(String name) {

                }
            });
            photoOption.hideWithAnimation();
            getItem(position).setOnOption(false);
            photoAdapterInterface.onOption(false, getAdapterPosition() != 0 ? getAdapterPosition() - 1 : 0);
        }


        void onRecycled() {
            ImageHelper.releaseImageView(photoView);
            System.gc();
        }


    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PhotoViewHolder && position < getItemCount()) {
            ((PhotoViewHolder) holder).onBindView(position);
            if (viewHeight == 1)
                holder.itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        holder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        viewHeight = holder.itemView.getHeight(); //height is ready
                    }
                });
        }
    }


    public int getViewHeight() {
        return viewHeight;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof PhotoViewHolder) {
            ((PhotoViewHolder) holder).onRecycled();
        }
    }

    public interface PhotoAdapterInterface {
        void onOption(boolean onOption, int adapterPosition);
        void onFirstPhotoLoad();
    }


}

