package team.hex.wallpaper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import team.hex.wallpaper.R;
import team.hex.wallpaper.abstracts.BaseFragment;
import team.hex.wallpaper.ui.activity.MainActivity;
import team.hex.wallpaper.ui.widget.PanoramaImageView;

/**
 * Created by alireza on 6/30/17.
 */

public class CollectionFragment extends BaseFragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onClick(View view) {

    }
}
