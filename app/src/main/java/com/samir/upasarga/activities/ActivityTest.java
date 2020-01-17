package com.samir.upasarga.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.samir.upasarga.R;

public class ActivityTest extends AppCompatActivity {

    BottomSheetBehavior bottomSheetBehavior;
    Button login, register;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        linearLayout = findViewById(R.id.bottom_sheet_login);
        login = findViewById(R.id.login_test_btn);
        register = findViewById(R.id.register_test_btn);

        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


    }
}
