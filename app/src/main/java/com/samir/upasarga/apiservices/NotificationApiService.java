package com.samir.upasarga.apiservices;

import com.samir.upasarga.models.NotificationModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NotificationApiService {
    @GET("notifications")
    Call<NotificationModel> getNotification();
}
