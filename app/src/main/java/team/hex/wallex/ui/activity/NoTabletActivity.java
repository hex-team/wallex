package team.hex.wallex.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import team.hex.wallex.R;
import team.hex.wallex.abstracts.BaseActivity;

/**
 * Created by alirezarashidi on 8/12/17.
 */

public class NoTabletActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_tablet);
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(this);
    }

    @Override
    public void onClick(View view) {

    }
}
