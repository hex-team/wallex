package team.hex.wallex.ui.fragment.introduction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.hex.wallex.R;
import team.hex.wallex.abstracts.BaseFragment;

/**
 * Created by alireza on 7/17/17.
 */

public class IntroductionFragment extends BaseFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_introduction, container, false);
        return view;
    }


    @Override
    public void onClick(View view) {

    }
}
