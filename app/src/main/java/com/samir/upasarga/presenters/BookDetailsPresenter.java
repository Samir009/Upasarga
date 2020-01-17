package com.samir.upasarga.presenters;

import android.util.Log;

import com.samir.upasarga.apiservices.ApiClient;
import com.samir.upasarga.apiservices.BookDetailsApiService;
import com.samir.upasarga.models.BookDetailsModel;
import com.samir.upasarga.utils.UtilitiesFunctions;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsPresenter {

    private WeakReference<View> view;

    public BookDetailsPresenter(BookDetailsPresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private BookDetailsPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public interface View{
        void onBookDetailsResponseSuccess(BookDetailsModel bookDetailsModel);
        void onBookDetailsResponseFailure(String message);
    }

    public void bookDetails(int bookid){
        Log.e("bookDetails: ", "Book details presenter hit.");
        BookDetailsApiService bookDetailsApiService = ApiClient.getClient().create(BookDetailsApiService.class);
        bookDetailsApiService.getBookDetails(bookid).enqueue(new Callback<BookDetailsModel>() {
            @Override
            public void onResponse(Call<BookDetailsModel> call, Response<BookDetailsModel> response) {
                if(response.isSuccessful()){
//                    ShowToast.withLongMessage("book details api response successful");
                    if(response.body() != null){
                        if(getView() != null){
                            getView().onBookDetailsResponseSuccess(response.body());
                        }
                    }
                } else {
                    Log.e( "onResponse: ","Book Details Api response failed" );
                }
            }

            @Override
            public void onFailure(Call<BookDetailsModel> call, Throwable t) {
                Log.e( "onBookDetails: ", UtilitiesFunctions.handleApiError(t));
                getView().onBookDetailsResponseFailure(UtilitiesFunctions.handleApiError(t));
            }
        });
    }
}
