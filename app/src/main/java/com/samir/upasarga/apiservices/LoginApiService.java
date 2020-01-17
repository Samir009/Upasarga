package com.samir.upasarga.apiservices;

import com.samir.upasarga.models.UserLoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApiService {
    @POST("login")
    @FormUrlEncoded
    Call<UserLoginModel> login(
        @Field("email") String email,
        @Field("password") String password
    );
}
