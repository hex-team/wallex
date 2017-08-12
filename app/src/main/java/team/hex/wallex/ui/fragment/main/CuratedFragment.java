package team.hex.wallex.ui.fragment.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hex.abstractandroidutils.tools.AppUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;
import team.hex.wallex.R;
import team.hex.wallex.abstracts.BasePhotoFragment;
import team.hex.wallex.api.model.Photo;
import team.hex.wallex.common.MessageEvent;
import team.hex.wallex.ui.widget.SnappyRecyclerView;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by alireza on 6/30/17.
 */

public class CuratedFragment extends BasePhotoFragment {

    @BindView(R.id.lstFeatured)
    public SnappyRecyclerView lstFeatured;
    @BindView(R.id.listLoading)
    AVLoadingIndicatorView listLoading;

    private boolean isOnOption = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curated, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }


    private void init() {
        setPhotoRequestType(PhotoReqType.FEATURED);
        lstFeatured.setLayoutManager(layoutManager);
        lstFeatured.setAdapter(photoAdapter);
        lstFeatured.addOnScrollListener(scrollListener);
        lstFeatured.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == SCROLL_STATE_IDLE && isOnOption)
                    layoutManager.setScrollEnabled(false);
            }
        });
        loadData(1);
    }


    @Override
    public void onLoadData(Call<List<Photo>> call, Response<List<Photo>> response, int page) {
        photoAdapter.insertMultipleItems(response.body());
        if(listLoading.getVisibility() != View.VISIBLE)
            listLoading.setVisibility(View.VISIBLE);
    }



    @Override
    public void onOption(boolean onOption, int adapterPosition) {
        if(onOption) {
            isOnOption = onOption;
            lstFeatured.smoothScrollToPosition(adapterPosition);
            if(lstFeatured.getVerticalScrollOffset() % photoAdapter.getViewHeight() == 0)
                layoutManager.setScrollEnabled(false);
        } else {
            isOnOption = onOption;
            layoutManager.setScrollEnabled(true);
        }
        EventBus.getDefault().post(new MessageEvent(MessageEvent.Message.onOption));
    }


    public boolean isOnOption() {
        return isOnOption;
    }


    @Override
    public void noDataFound(int page) {
        if(listLoading.getVisibility() == View.VISIBLE)
            listLoading.setVisibility(View.GONE);
    }

    @Override
    public void onLoadFailed(Call<List<Photo>> call, Throwable t, int page) {
        t.printStackTrace();
        if (!AppUtils.isOnline(getContext()))
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Message.loadError));
    }


    @Override
    public void onClick(View view) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getMessage()) {
            case backPress: {
                if(event.getTextMessage() == CuratedFragment.class.getName()) {
                    lstFeatured.smoothScrollToPosition(0);
                }
            }
            break;
        }
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


    // not use here
    @Override
    public void onFirstPhotoLoad() {

    }
}
