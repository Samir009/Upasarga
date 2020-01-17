package com.samir.upasarga.apiservices;

import com.samir.upasarga.models.LatestBooksModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LatestBookApiService {
    @GET("latest_books")
    Call<LatestBooksModel> getLatestBook();
}
