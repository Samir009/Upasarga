package com.samir.upasarga.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.TextView;

import com.samir.upasarga.R;

public class SwipeToRefresh extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView;

    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_to_refresh);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        textView = findViewById(R.id.swipeCount);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                num ++;
                textView.setText(String.valueOf(num));
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }
}
