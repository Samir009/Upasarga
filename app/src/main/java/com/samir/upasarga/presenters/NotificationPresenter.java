package com.samir.upasarga.presenters;

import android.util.Log;
import android.widget.Toast;

import com.samir.upasarga.activities.NotificationActivity;
import com.samir.upasarga.apiservices.ApiClient;
import com.samir.upasarga.apiservices.NotificationApiService;
import com.samir.upasarga.models.NotificationModel;
import com.samir.upasarga.utils.UtilitiesFunctions;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter {

    private WeakReference<View> view;

    public NotificationPresenter(NotificationPresenter.View view) {
        this.view = new WeakReference<>(view);

    }

    private NotificationPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    public interface View {

        void onNotificationResponeSuccess(NotificationModel notificationModel);

        void onNotificationResponseFailure(String message);
    }

    public void showNotification(){
        Log.e( "showNotification: ","notification presenter called" );
        NotificationApiService notificationApiService = ApiClient.getClient().create(NotificationApiService.class);
        notificationApiService.getNotification().enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        if(getView() != null){
//                            ShowToast.withLongMessage("notification response successful");
                            getView().onNotificationResponeSuccess(response.body());
                        }
                    }
                } else {
                    Log.e("showNotification: ", "notification API response failed");
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                Log.e( "showNotification: ", UtilitiesFunctions.handleApiError(t ));

                getView().onNotificationResponseFailure(UtilitiesFunctions.handleApiError(t));
            }
        });
    }
}
