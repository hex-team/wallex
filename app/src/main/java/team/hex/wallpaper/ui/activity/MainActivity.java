package team.hex.wallpaper.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.hex.wallpaper.R;
import team.hex.wallpaper.abstracts.BaseActivity;
import team.hex.wallpaper.common.MessageEvent;
import team.hex.wallpaper.common.utils.GyroscopeObserver;
import team.hex.wallpaper.ui.adapter.PagerAdapterCreator;
import team.hex.wallpaper.ui.fragment.CollectionFragment;
import team.hex.wallpaper.ui.fragment.FeaturedFragment;
import team.hex.wallpaper.ui.fragment.NewFragment;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.mainPager)
    ViewPager mainPager;

    private ActivityInterface activityInterface;
    private GyroscopeObserver gyroscopeObserver;

    public UserAction userAction;

    public enum UserAction {
        share(0), save(1), wallpaper(3), Undefiend(-1);

        private final int value;

        private UserAction(int value) {
            this.value = value;
        }

        public static UserAction getOperator(int operator) {
            switch (operator) {
                case 0:
                    return UserAction.share;
                case 1:
                    return UserAction.save;
                case 2:
                    return UserAction.wallpaper;
                case -1:
                    return UserAction.Undefiend;
                default:
                    return UserAction.Undefiend;
            }
        }

        public int getValue() {
            return value;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, SplashActivity.class));
        ButterKnife.bind(this);

        setStatusBarTranslucent(true);


        init();
    }


    private void init() {
        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 2);

        mainPager.setAdapter(PagerAdapterCreator.init(getSupportFragmentManager())
                .addFragment(new NewFragment())
                .addFragment(new FeaturedFragment())
                .addFragment(new CollectionFragment()).create());
        mainPager.setOffscreenPageLimit(2);
        mainPager.addOnPageChangeListener(this);
    }


    public boolean checkStoragePermission(Context context, UserAction userAction, ActivityInterface activityInterface) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ((Activity) context).requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
                this.userAction = userAction;
                this.activityInterface = activityInterface;
                return false;
            }
            return true;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        if (mainPager.getCurrentItem() == 0 && ((NewFragment) ((PagerAdapterCreator.PagerAdapter) mainPager.getAdapter()).getItem(0)).lstNew.getVerticalScrollOffset() > ((NewFragment) ((PagerAdapterCreator.PagerAdapter) mainPager.getAdapter()).getItem(0)).photoAdapter.getViewHeight())
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Message.backPress, NewFragment.class.getName()));
        else if (mainPager.getCurrentItem() == 1 && ((FeaturedFragment) ((PagerAdapterCreator.PagerAdapter) mainPager.getAdapter()).getItem(1)).lstFeatured.getVerticalScrollOffset() > ((FeaturedFragment) ((PagerAdapterCreator.PagerAdapter) mainPager.getAdapter()).getItem(1)).photoAdapter.getViewHeight())
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Message.backPress, FeaturedFragment.class.getName()));
        else
            super.onBackPressed();
    }


    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (activityInterface != null && userAction != null) {
                        activityInterface.onPermissionResult(userAction);
                        userAction = null;
                        activityInterface = null;
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Register GyroscopeObserver.
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister GyroscopeObserver.
        gyroscopeObserver.unregister();
    }

    public GyroscopeObserver getGyroscopeObserver() {
        return gyroscopeObserver;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface ActivityInterface {
        void onPermissionResult(UserAction userAction);
    }
}
