package com.samir.upasarga.apiservices;

import com.samir.upasarga.models.HomeModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HomeApiService {
    @GET("home")
    Call<HomeModel> getHome();
}
