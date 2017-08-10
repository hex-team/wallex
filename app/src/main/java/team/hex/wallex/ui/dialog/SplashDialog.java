package team.hex.wallex.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;

import team.hex.wallex.R;
import team.hex.wallex.abstracts.BaseActivity;
import team.hex.wallex.ui.activity.MainActivity;

/**
 * Created by alireza on 8/1/17.
 */

public class SplashDialog extends DialogFragment {

    @Override
    public void onStart()
    {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null)
        {
            dialog.setContentView(R.layout.dialog_splash);
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setDimAmount(0);
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {

                    if ((i ==  android.view.KeyEvent.KEYCODE_BACK))
                    {
                        // To dismiss the fragment when the back-button is pressed.
                        getActivity().finish();
                        return true;
                    }
                    // Otherwise, do nothing else
                    else return false;
                }
            });
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.fullScreenDialog_WindowAnimation;
    }

    public boolean isShowing() {
        return getDialog().isShowing();
    }
}
