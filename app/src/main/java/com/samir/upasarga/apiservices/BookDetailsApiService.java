package com.samir.upasarga.apiservices;

import com.samir.upasarga.models.BookDetailsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookDetailsApiService {
    @GET("book/{id}")
    Call<BookDetailsModel> getBookDetails(
            @Path("id") int BookId
    );

}
