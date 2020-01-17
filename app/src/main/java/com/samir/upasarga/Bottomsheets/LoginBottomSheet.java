package com.samir.upasarga.Bottomsheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.samir.upasarga.R;

public class LoginBottomSheet extends BottomSheetDialogFragment {

    BottomSheetListener bottomSheetListener;
    BottomSheetBehavior bottomSheetBehavior;
    LinearLayout loginBottomsheet;
    private TextView dissmiss;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_login,container,false);

        dissmiss = view.findViewById(R.id.login_dismiss);
        dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetListener.onDismissClicked("Fingerprint authentication dismissed.");
                dismiss();
            }
        });

        return view;
    }

    public interface BottomSheetListener{
        void onDismissClicked(String string);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            bottomSheetListener = (BottomSheetListener) context;
        } catch(ClassCastException ex) {
            throw new ClassCastException(context.toString() + "must implement BottomSheetListener ");
        }
    }
}
