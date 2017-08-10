package team.hex.wallex.api.util;

import com.hex.abstractandroidutils.tools.AppUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import team.hex.wallex.application.AppController;

/**
 * Created by alireza on 7/3/17.
 */

public class OfflineCacheInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (AppUtils.isOnline(AppController.getContext().getApplicationContext())) {
            int maxAge = 60; // read from cache for 1 minute
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }    }
}
