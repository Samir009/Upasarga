package com.samir.upasarga.presenters;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.samir.upasarga.apiservices.ApiClient;
import com.samir.upasarga.apiservices.LoginApiService;
import com.samir.upasarga.models.UserLoginModel;
import com.samir.upasarga.utils.Utilities;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter {

    private WeakReference<View> view;

    public LoginPresenter(LoginPresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private LoginPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public interface View {

        void onLoginResponeSuccess(UserLoginModel body);

        void onResponseFailure(String something_went_wrong);
    }

    public void userLogin(String email, String password){
        Log.e("userLogin: ","login presenter" );
        LoginApiService loginApiService = ApiClient.getClient().create(LoginApiService.class);
        loginApiService.login(email,password).enqueue(new Callback<UserLoginModel>() {
            @Override
            public void onResponse(Call<UserLoginModel> call, Response<UserLoginModel> response) {
                if(response.isSuccessful())
                {
                    if(response.body() != null)
                    {
                        Log.e("onResponse: new ", new GsonBuilder().create().toJson(response.body()));
                        getView().onLoginResponeSuccess(response.body());
                        Utilities.saveLoginResponse(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<UserLoginModel> call, Throwable t) {
                getView().onResponseFailure("Something went wrong");
                Log.e("onFailure: ","error");
            }
        });
    }
}
