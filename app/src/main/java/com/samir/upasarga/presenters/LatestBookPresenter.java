package com.samir.upasarga.presenters;

import android.util.Log;

import com.samir.upasarga.apiservices.ApiClient;
import com.samir.upasarga.apiservices.LatestBookApiService;
import com.samir.upasarga.models.LatestBooksModel;
import com.samir.upasarga.utils.UtilitiesFunctions;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestBookPresenter {

    private WeakReference<LatestBookPresenter.View> view;

    public LatestBookPresenter(LatestBookPresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private LatestBookPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public interface View{
        void onLatestBookResponseSuccess(LatestBooksModel latestBooksModel);
        void onLatestBookResponseFailure(String ErrorMessage);
    }

    public void showLatestBooks(){
        Log.e("showLatestBooks: ","latest book presenter invoked" );
        LatestBookApiService latestBookApiService = ApiClient.getClient().create(LatestBookApiService.class);
        latestBookApiService.getLatestBook().enqueue(new Callback<LatestBooksModel>() {
            @Override
            public void onResponse(Call<LatestBooksModel> call, Response<LatestBooksModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        getView().onLatestBookResponseSuccess(response.body());
                    }
                } else {
                    Log.e("onResponse: ", "LATEST BOOK API SERVICE RESPONSE FAILURE");
                }
            }

            @Override
            public void onFailure(Call<LatestBooksModel> call, Throwable t) {
                Log.e( "showNotification: ", UtilitiesFunctions.handleApiError(t ));

                getView().onLatestBookResponseFailure(UtilitiesFunctions.handleApiError(t));
            }
        });
    }
}
