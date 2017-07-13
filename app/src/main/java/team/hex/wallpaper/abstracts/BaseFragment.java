package team.hex.wallpaper.abstracts;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import static com.hex.abstractandroidutils.tools.AppUtils.getStackTrace;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                paramThrowable.printStackTrace();
                //Catch your exception
                // Without System.exit() this will not work.

                String stackTrace = getStackTrace(paramThrowable);

            }
        }); */
    }
}

