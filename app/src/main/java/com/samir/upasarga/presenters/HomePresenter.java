package com.samir.upasarga.presenters;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.samir.upasarga.apiservices.ApiClient;
import com.samir.upasarga.apiservices.HomeApiService;
import com.samir.upasarga.apiservices.NotificationApiService;
import com.samir.upasarga.models.HomeModel;
import com.samir.upasarga.models.NotificationModel;
import com.samir.upasarga.utils.UtilitiesFunctions;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {
    private WeakReference<HomePresenter.View> view;

    public HomePresenter(HomePresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private HomePresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public interface View {
        void onHomeResponeSuccess(HomeModel homeModel);

        void onHomeResponseFailure(String message);
    }

    public void getHome() {
        Log.e("showNotification: ", "Home presenter called");
        HomeApiService homeApiService = ApiClient.getClient().create(HomeApiService.class);
        homeApiService.getHome().enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (getView() != null) {
                            getView().onHomeResponeSuccess(response.body());

                            Log.e("onResponse: ",new GsonBuilder().create().toJson(response.body()));
                        }
                    }
                } else {
                    Log.e("showNotification: ", "home API response failed");
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                Log.e("showNotification: ", UtilitiesFunctions.handleApiError(t));

                getView().onHomeResponseFailure(UtilitiesFunctions.handleApiError(t));
            }
        });
    }
}
