package team.hex.wallpaper.abstracts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import team.hex.wallpaper.api.method.PhotoApi;
import team.hex.wallpaper.api.model.Photo;
import team.hex.wallpaper.api.service.PhotoService;
import team.hex.wallpaper.common.utils.CustomLinearLayoutManager;
import team.hex.wallpaper.common.utils.EndlessRecyclerViewScrollListener;
import team.hex.wallpaper.ui.activity.MainActivity;
import team.hex.wallpaper.ui.adapter.PhotoAdapter;

/**
 * Created by alireza on 7/3/17.
 */

public abstract class BasePhotoFragment extends BaseFragment implements PhotoAdapter.PhotoAdapterInterface{

    private final int ITEM_COUNT = 30;

    public enum PhotoReqType {
        NEW, FEATURED
    }

    public PhotoAdapter photoAdapter;
    public CustomLinearLayoutManager layoutManager;
    public EndlessRecyclerViewScrollListener scrollListener;

    private PhotoReqType photoReqType = PhotoReqType.NEW;

    private boolean hasItem = true;

    public String ORDER_BY = PhotoApi.ORDER_BY_LATEST;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutManager = new CustomLinearLayoutManager(getContext());
        photoAdapter = new PhotoAdapter(getContext(), new ArrayList<Photo>(), ((MainActivity) getActivity()).getGyroscopeObserver(), this);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (hasItem)
                    loadData(page+1);
            }
        };
    }

    public void loadData(final int page) {
        switch (photoReqType) {
            case NEW: {
                PhotoService.getService().requestPhotos(page, ITEM_COUNT, ORDER_BY, new PhotoService.OnRequestPhotosListener() {
                    @Override
                    public void onRequestPhotosSuccess(Call<List<Photo>> call, Response<List<Photo>> response) {
                        if (response.body() != null) {
                            onLoadData(call, response, page);
                        } else {
                            if (photoAdapter.getItemCount() == 0)
                                noDataFound(page);
                            else
                                hasItem = false;
                        }
                    }

                    @Override
                    public void onRequestPhotosFailed(Call<List<Photo>> call, Throwable t) {
                        onLoadFailed(call, t, page);
                    }
                });
            }
            break;
            case FEATURED: {
                PhotoService.getService().requestCuratePhotos(page, ITEM_COUNT, ORDER_BY, new PhotoService.OnRequestPhotosListener() {
                    @Override
                    public void onRequestPhotosSuccess(Call<List<Photo>> call, Response<List<Photo>> response) {
                        if (response.body() != null) {
                            onLoadData(call, response, page);
                        } else {
                            if (photoAdapter.getItemCount() == 0)
                                noDataFound(page);
                            else
                                hasItem = false;
                        }
                    }

                    @Override
                    public void onRequestPhotosFailed(Call<List<Photo>> call, Throwable t) {
                        onLoadFailed(call, t, page);
                    }
                });
            }
            break;
        }

    }


    public void setPhotoRequestType(PhotoReqType photoReqType) {
        this.photoReqType = photoReqType;
    }

    public void setOrderBy(String orderBy) {
        if(scrollListener != null)
            scrollListener.resetState();
        ORDER_BY = orderBy;
    }


    public abstract void onLoadData(Call<List<Photo>> call, Response<List<Photo>> response, int page);

    public abstract void noDataFound(int page);

    public abstract void onLoadFailed(Call<List<Photo>> call, Throwable t, int page);
}
