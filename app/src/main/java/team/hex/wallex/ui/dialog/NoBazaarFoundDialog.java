package team.hex.wallex.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import team.hex.wallex.R;

/**
 * Created by alirezarashidi on 8/12/17.
 */

public class NoBazaarFoundDialog extends DialogFragment {


    @Override
    public void onStart()
    {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null)
        {
            dialog.setContentView(R.layout.dialog_no_bazaar_found);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setDimAmount(0.7f);
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
            dialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            dialog.findViewById(R.id.btnInstallBazaar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.cafeBazaarUrl)));
                    getActivity().startActivity(browserIntent);
                    dismiss();
                }
            });
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogWindowAnimation;

    }

    public boolean isShowing() {
        return getDialog().isShowing();
    }
}

