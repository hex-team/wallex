package team.hex.wallex.ui.fragment.introduction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import team.hex.wallex.R;
import team.hex.wallex.abstracts.BaseFragment;

/**
 * Created by alireza on 7/17/17.
 */

public class StartFragment extends BaseFragment {

    private StartFragmentInterface startFragmentInterface;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_start, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.btnStart)
    public void start() {
        startFragmentInterface.onStart();
    }


    public void setStartFragmentInterface(StartFragmentInterface startFragmentInterface) {
        this.startFragmentInterface = startFragmentInterface;
    }

    @Override
    public void onClick(View view) {

    }

    public interface StartFragmentInterface {
        void onStart();
    }
}
