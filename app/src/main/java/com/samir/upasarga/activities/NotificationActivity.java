package com.samir.upasarga.activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.samir.upasarga.R;
import com.samir.upasarga.helpers.AppActivity;
import com.samir.upasarga.helpers.AppRecyclerViewAdapter;
import com.samir.upasarga.helpers.DefineClassType;
import com.samir.upasarga.models.NotificationModel;
import com.samir.upasarga.presenters.NotificationPresenter;
import com.samir.upasarga.utils.UtilitiesFunctions;

import java.io.Serializable;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class NotificationActivity extends AppActivity implements NotificationPresenter.View, View.OnClickListener{

    SmoothProgressBar smoothProgressBar;

    LinearLayout back_arrow;
    RecyclerView recyclerView;

    private NotificationRecyclerViewAdapter notificationRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    NotificationPresenter notificationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initializeView();
        initializeListeners();
        prepareRecyclerView();
    }

    @Override
    protected void initializeView() {
        back_arrow = findViewById(R.id.back_arrow_layout);
        recyclerView = findViewById(R.id.notification_activity_recycler_view);
        smoothProgressBar = findViewById(R.id.notificationProgressBarId);
    }

    @Override
    protected void initializeListeners() {
        back_arrow.setOnClickListener(this);
        notificationPresenter = new NotificationPresenter(this);
        notificationPresenter.showNotification();
        showProgressBar();
    }

    private void prepareRecyclerView(){
        notificationRecyclerViewAdapter = new NotificationRecyclerViewAdapter();
        linearLayoutManager = new LinearLayoutManager(NotificationActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notificationRecyclerViewAdapter);
    }

    private void showProgressBar(){
        if(smoothProgressBar!=null){
            Log.e( "showProgressBar: ", "show progressbar function is invoked");
            smoothProgressBar.setVisibility(View.VISIBLE);
        }
    }
    private void hideProgressBar(){
        if(smoothProgressBar!=null){
            Log.e( "showProgressBar: ", "hide progressbar function is invoked");
            smoothProgressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_arrow_layout:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
    }

    @Override
    public void onNotificationResponeSuccess(NotificationModel notificationModel) {

        Log.e("onNotificationRes", "notification response success");
        notificationRecyclerViewAdapter.add(notificationModel);
        notificationRecyclerViewAdapter.notifyDataSetChanged();
        hideProgressBar();
    }

    @Override
    public void onNotificationResponseFailure(String message) {

        Log.e("onNotificationFai", "notification response error");
    }

    private class NotificationRecyclerViewAdapter extends AppRecyclerViewAdapter{

        NotificationModel notificationModelData;

        @Override
        public void add(Object object) {
            notificationModelData = DefineClassType.getType(object, NotificationModel.class);
            Log.e("add: ", new GsonBuilder().create().toJson(notificationModelData));
        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_content,parent,false);
            return new VHItem(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VHItem vhItem =(VHItem) holder;
            NotificationModel.Datum data = notificationModelData.getData().get(position);
            vhItem.notification_date.setText(UtilitiesFunctions.simpleFormatToClientDateOnly(data.getCreatedAt()));
            vhItem.notification_title.setText(UtilitiesFunctions.fromHtml(data.getName()));
            vhItem.notification_description.setText(UtilitiesFunctions.fromHtml(data.getDescription()));
            vhItem.notification_seen_unseen.setText(UtilitiesFunctions.fromHtml(String.valueOf(data.getSeen())));
        }

        @Override
        public int getItemCount() {
            if(notificationModelData != null){
                return notificationModelData.getData().size();
            }
            return 0;
        }

        private class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener{

            private LinearLayout open_book_details;
            private TextView notification_title;
            private TextView notification_description;
            private TextView notification_date;
            private TextView notification_seen_unseen;
            public VHItem(@NonNull View itemView) {
                super(itemView);

                open_book_details = itemView.findViewById(R.id.open_book_details_from_notification);
                notification_title = itemView.findViewById(R.id.notification_title);
                notification_description = itemView.findViewById(R.id.notification_description);
                notification_date = itemView.findViewById(R.id.notification_date);
                notification_seen_unseen = itemView.findViewById(R.id.seen_unseen_notification);
                initializeListener();
            }

            private void initializeListener(){
                    open_book_details.setOnClickListener(this);
            }
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.open_book_details_from_notification:
//                        NotificationModel.Datum notify = notificationModelData.getData().get(getAdapterPosition());

                        Intent intent = new Intent(NotificationActivity.this, BookDetailsActivity.class);
                        intent.putExtra("book_id",notificationModelData.getData().get(getAdapterPosition()).getBookId());
//                        intent.putExtra("pdf_url", notificationModelData.getData().get(getAdapterPosition()).)
//                        intent.putExtra("book_id", notify.getBookId());
                        startActivity(intent);
                }
            }
        }
    }
}
