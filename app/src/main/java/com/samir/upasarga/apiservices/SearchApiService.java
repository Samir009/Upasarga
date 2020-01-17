package com.samir.upasarga.apiservices;

import com.samir.upasarga.models.SearchModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SearchApiService {
    @POST("searchlist")
    @FormUrlEncoded
    Call<SearchModel> getSearchData (
      @Field("name") String content
    );
}
