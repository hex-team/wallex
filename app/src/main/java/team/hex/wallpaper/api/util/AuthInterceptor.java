package team.hex.wallpaper.api.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static team.hex.wallpaper.common.BuildConfigs.CLIENT_ID;

/**
 * Auth interceptor.
 *
 * A interceptor for {@link retrofit2.Retrofit}, it can add authorization information into the
 * HTTP request header.
 *
 * */

public class AuthInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request;
            request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Client-ID " + CLIENT_ID)
                    .build();
        return chain.proceed(request);
    }
}
