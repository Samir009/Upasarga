package com.samir.upasarga.apiservices;

import androidx.annotation.NonNull;

import com.samir.upasarga.utils.Utilities;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (Utilities.getLoginResponse() == null ) {
            return chain.proceed(originalRequest);
        }
        Request request = originalRequest.newBuilder()
                .addHeader("Authorization", Utilities.getLoginResponse().getUserDetails().getToken())
                .addHeader("Accept", "Accept: application/x.school.v1+json")
                .header("Cache-Control", String.format("max-age=%d", 50000))
                .build();
        return chain.proceed(request);
    }
}