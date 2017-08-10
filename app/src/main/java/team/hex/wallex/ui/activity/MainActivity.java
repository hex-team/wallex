package team.hex.wallex.ui.activity;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.lang.ref.WeakReference;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import team.hex.wallex.R;
import team.hex.wallex.abstracts.BaseActivity;
import team.hex.wallex.common.MessageEvent;
import team.hex.wallex.common.utils.AppPrefs;
import team.hex.wallex.common.utils.CustomTypefaceSpan;
import team.hex.wallex.common.utils.Font;
import team.hex.wallex.common.utils.GyroscopeObserver;
import team.hex.wallex.common.utils.OnSwipeTouchListener;
import team.hex.wallex.ui.adapter.PagerAdapterCreator;
import team.hex.wallex.ui.dialog.SplashDialog;
import team.hex.wallex.ui.fragment.introduction.GetPermissionFragment;
import team.hex.wallex.ui.fragment.introduction.IntroductionFragment;
import team.hex.wallex.ui.fragment.introduction.StartFragment;
import team.hex.wallex.ui.fragment.main.CuratedFragment;
import team.hex.wallex.ui.fragment.main.NewFragment;
import team.hex.wallex.ui.widget.CustomTextView;
import team.hex.wallex.ui.widget.NoSwipeableViewPager;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.mainPager)
    NoSwipeableViewPager mainPager;
    @BindView(R.id.mainContainer)
    RelativeLayout mainContainer;

    OnSwipeTouchListener swipeTouchListener;

    private ActivityInterface activityInterface;
    private GyroscopeObserver gyroscopeObserver;

    public UserAction userAction;

    WeakReference<SplashDialog> splashDialog;

    public enum UserAction {
        share(0), save(1), wallpaper(3), Undefined(-1);

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
                    return UserAction.Undefined;
                default:
                    return UserAction.Undefined;
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

        logEvent();

        initSplash();

        //  startActivity(new Intent(this, SplashActivity.class));
        ButterKnife.bind(this);

        setStatusBarTranslucent(true);


        init();
    }

    private void initSplash() {
        splashDialog = new WeakReference<>(new SplashDialog());
        splashDialog.get().setCancelable(false);
        splashDialog.get().show(getFragmentManager(), "splash");
    }

    private void init() {

        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 2);

        mainPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mainPager.setAdapter(PagerAdapterCreator.init(getSupportFragmentManager())
                .addFragment(new NewFragment())
                .addFragment(new CuratedFragment()).create());
        mainPager.setOffscreenPageLimit(2);
        mainPager.addOnPageChangeListener(this);


        swipeTouchListener = new OnSwipeTouchListener() {

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.bottomDialog);

            @Override
            public boolean onSwipeRight() {
                if (mainPager.getCurrentItem() == 0 && !((NewFragment) ((PagerAdapterCreator.PagerAdapter) mainPager.getAdapter()).getItem(0)).isOnOption()) {
                    bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet);
                    bottomSheetDialog.setCanceledOnTouchOutside(true);
                    CustomTextView btnGotoOurSite = (CustomTextView) bottomSheetDialog.findViewById(R.id.btnGotoOurSite);

                    SpannableString text = new SpannableString(getResources().getString(R.string.designedByUs));
                    text.setSpan(new RelativeSizeSpan(1.2f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorYellow)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new RelativeSizeSpan(1f), 2, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTextGray)), 2, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new RelativeSizeSpan(1.2f), 33, 34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorYellow)), 33, 34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new CustomTypefaceSpan("", Font.fontBuilder(Font.Fonts.IRANSans, MainActivity.this)), 0, text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    btnGotoOurSite.setText(text);

                    bottomSheetDialog.findViewById(R.id.btnRateUs).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bottomSheetDialog.dismiss();
                        }
                    });
                    btnGotoOurSite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bottomSheetDialog.dismiss();
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.hexUrl)));
                            MainActivity.this.startActivity(browserIntent);
                        }
                    });
                    bottomSheetDialog.show();
                }
                return true;
            }

            @Override
            public boolean onSwipeLeft() {
                if (mainPager.getCurrentItem() != 0) {
                    bottomSheetDialog.dismiss();
                }
                return true;
            }
        };

    }

    private void showIntroduction() {
        StartFragment startFragment;
        GetPermissionFragment getPermissionFragment;
        final ViewPager introductionPager = new ViewPager(this);
        introductionPager.setId(R.id.introductionPagerId);
        introductionPager.setAdapter(PagerAdapterCreator.init(getSupportFragmentManager())
                .addFragment(new IntroductionFragment())
                .addFragment(getPermissionFragment = new GetPermissionFragment())
                .addFragment(startFragment = new StartFragment())
                .create());
        mainContainer.addView(introductionPager, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        final CircleIndicator indicator = new CircleIndicator(this);
        indicator.setViewPager(introductionPager);
        RelativeLayout.LayoutParams indicatorParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        indicatorParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        indicatorParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        indicatorParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.indicatorMargin);
        mainContainer.addView(indicator, indicatorParams);
        swipeTouchListener.setEnable(false);
        startFragment.setStartFragmentInterface(new StartFragment.StartFragmentInterface() {
            @Override
            public void onStart() {
                introductionPager.animate().alpha(0).setDuration(300).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        swipeTouchListener.setEnable(true);
                        mainContainer.removeView(introductionPager);
                        mainContainer.removeView(indicator);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
        getPermissionFragment.setGetPermissionFragmentInterface(new GetPermissionFragment.GetPermissionFragmentInterface() {
            @Override
            public void onPermission() {
                introductionPager.setCurrentItem(introductionPager.getCurrentItem() + 1);
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        swipeTouchListener.getGestureDetector().onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getMessage()) {
            case onOption: {
                mainPager.setPagingEnabled(!(((NewFragment) ((PagerAdapterCreator.PagerAdapter) mainPager.getAdapter()).getItem(0)).isOnOption() ||
                        ((CuratedFragment) ((PagerAdapterCreator.PagerAdapter) mainPager.getAdapter()).getItem(1)).isOnOption()));
            }
            break;
            case loadComplete: {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        splashDialog.get().dismiss();
                        if (AppPrefs.checkAppStart(MainActivity.this) == AppPrefs.AppStart.FIRST_TIME) {
                            showIntroduction();
                        }
                    }
                }, 10);
            }
        }
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
        //check if vertical scroll offset is greater than first item then scroll to start
        if (mainPager.getCurrentItem() == 0 && ((NewFragment) ((PagerAdapterCreator.PagerAdapter) mainPager.getAdapter()).getItem(0)).lstNew.getVerticalScrollOffset() > ((NewFragment) ((PagerAdapterCreator.PagerAdapter) mainPager.getAdapter()).getItem(0)).photoAdapter.getViewHeight())
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Message.backPress, NewFragment.class.getName()));
        else if (mainPager.getCurrentItem() == 1 && ((CuratedFragment) ((PagerAdapterCreator.PagerAdapter) mainPager.getAdapter()).getItem(1)).lstFeatured.getVerticalScrollOffset() > ((CuratedFragment) ((PagerAdapterCreator.PagerAdapter) mainPager.getAdapter()).getItem(1)).photoAdapter.getViewHeight())
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Message.backPress, CuratedFragment.class.getName()));
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


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
