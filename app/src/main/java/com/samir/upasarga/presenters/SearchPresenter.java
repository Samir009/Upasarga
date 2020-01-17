package com.samir.upasarga.presenters;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.samir.upasarga.apiservices.ApiClient;
import com.samir.upasarga.apiservices.SearchApiService;
import com.samir.upasarga.models.SearchModel;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter {
    private WeakReference<SearchPresenter.View> view;

    public SearchPresenter(SearchPresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private SearchPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public interface View {

        void onSearchResponeSuccess(SearchModel searchModel);

        void onSearchResponseFailure(String something_went_wrong);
    }

    public void search(String content){
        Log.e( "search: ", "search API invoked" );
        SearchApiService searchApiService = ApiClient.getClient().create(SearchApiService.class);
        searchApiService.getSearchData(content).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        Log.e( "onResponse: ", new GsonBuilder().create().toJson(response.body()));
                        getView().onSearchResponeSuccess(response.body());
                    }
                }

            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                getView().onSearchResponseFailure("something went wrong");
                Log.e("onFailure: ", "search API response failure");
            }
        });
    }
}
