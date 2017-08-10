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
import team.hex.wallex.ui.activity.MainActivity;

/**
 * Created by alireza on 7/17/17.
 */

public class GetPermissionFragment extends BaseFragment {


    private GetPermissionFragmentInterface getPermissionFragmentInterface;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_get_permission, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setGetPermissionFragmentInterface(GetPermissionFragmentInterface getPermissionFragmentInterface) {
        this.getPermissionFragmentInterface = getPermissionFragmentInterface;
    }

    @OnClick(R.id.btnGetPermission)
    public void getPermission() {
        ((MainActivity) getActivity()).checkStoragePermission(getContext(), MainActivity.UserAction.Undefined, new MainActivity.ActivityInterface() {
            @Override
            public void onPermissionResult(MainActivity.UserAction userAction) {
                getPermissionFragmentInterface.onPermission();
            }
        });
    }


    @Override
    public void onClick(View view) {

    }


    public interface GetPermissionFragmentInterface {
        void onPermission();
    }
}
