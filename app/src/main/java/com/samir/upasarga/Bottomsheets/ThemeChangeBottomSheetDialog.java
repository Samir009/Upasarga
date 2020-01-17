package com.samir.upasarga.Bottomsheets;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.samir.upasarga.R;

import static com.samir.upasarga.utils.Utilities.setNightMode;

public class ThemeChangeBottomSheetDialog extends BottomSheetDialogFragment {

    private ChangeThemeListener changeThemeListener;

    RadioGroup radioGroup;
    RadioButton getSelectedTheme;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.change_theme_bottomsheet, container,false);
        radioGroup = view.findViewById(R.id.day_night_theme_rbtn);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getSelectedTheme = view.findViewById(checkedId);
//                Toast.makeText(getContext(), getSelectedTheme.getText().toString(), Toast.LENGTH_SHORT).show();
                Log.e( "changeThemeListener : ", getSelectedTheme.getText().toString());

                if(getSelectedTheme.getText().equals("Day Theme")){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    setNightMode(false);

                } else if(getSelectedTheme.getText().equals("Night Theme")){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    setNightMode(true);
                }
                changeThemeListener.onThemeSelected(getSelectedTheme.getText().toString());
                dismiss();
            }
        });

        return view;
    }

    public interface ChangeThemeListener {
        void onThemeSelected(String getTheme);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            changeThemeListener =(ChangeThemeListener)context;
        } catch (ClassCastException ex){
            throw new ClassCastException(context.toString()+"must implement change theme listener");
        }
    }
}