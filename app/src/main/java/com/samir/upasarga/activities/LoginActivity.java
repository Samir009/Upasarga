package com.samir.upasarga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.samir.upasarga.Bottomsheets.LoginBottomSheet;
import com.samir.upasarga.R;
import com.samir.upasarga.helpers.AppActivity;
import com.samir.upasarga.models.UserLoginModel;
import com.samir.upasarga.presenters.LoginPresenter;
import com.samir.upasarga.utils.Utilities;
import com.samir.upasarga.utils.UtilitiesFunctions;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class LoginActivity extends AppActivity implements LoginPresenter.View, View.OnClickListener, LoginBottomSheet.BottomSheetListener {

    private EditText Email;
    private EditText Password;
    private SwitchCompat aSwitch;
    private Button LoginBtn;
    private LinearLayout fingerprint_Login;
    private LinearLayout register_here;

    private SmoothProgressBar smoothProgressBar;

    private LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeView();
        initializeListeners();
    }

    @Override
    protected void initializeView() {
        Email = findViewById(R.id.Email_Id);
        Password = findViewById(R.id.PasswordId);
        aSwitch = findViewById(R.id.password_switch);
        LoginBtn = findViewById(R.id.LoginBtnId);
        smoothProgressBar = findViewById(R.id.ProgressBarId);
        register_here = findViewById(R.id.register_here);
        fingerprint_Login = findViewById(R.id.fingerprint_id);
    }

    @Override
    protected void initializeListeners() {

        LoginBtn.setOnClickListener(this);
        fingerprint_Login.setOnClickListener(this);
        register_here.setOnClickListener(this);
        loginPresenter = new LoginPresenter(this);
        Email.setText("android@gmail.com");
        Password.setText("android");
        aSwitch.setOnClickListener(this);
        checkIfUserIsLoggedIn();
    }

    private void showHidePassword(){
        if(aSwitch.isChecked()){
            Password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            Password.setSelection(Password.length());
        } else {
            Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            Password.setSelection(Password.length());
        }
    }

//        check whether user is logged in or not

    private void checkIfUserIsLoggedIn(){
        if(Utilities.isLogin()){
            doAfterLoginSuccess();
        }
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
            case R.id.LoginBtnId:
                doLoginWork(Email, Password);
                break;
            case R.id.password_switch:
                showHidePassword();
                break;
            case R.id.fingerprint_id:
                LoginBottomSheet loginBottomSheet = new LoginBottomSheet();
                loginBottomSheet.show(getSupportFragmentManager(),"loginBottomSheet");
                break;

            case R.id.register_here:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void doLoginWork(EditText email, EditText password) {
        if(Email.getText().toString().isEmpty() && Password.getText().toString().isEmpty()){
            Toast.makeText(this, "Username or password empty", Toast.LENGTH_SHORT).show();
        } else {
            if(UtilitiesFunctions.isNetworkAvailable(LoginActivity.this)){
                Toast.makeText(this, "internet connected", Toast.LENGTH_SHORT).show();

                showProgressBar();
                loginPresenter.userLogin(email.getText().toString(), password.getText().toString());

            } else {
                Toast.makeText(this, "please connect to internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLoginResponeSuccess(UserLoginModel body) {
        hideProgressBar();
//        Toast.makeText(this, "response success" + new GsonBuilder().create().toJson(body), Toast.LENGTH_SHORT).show();
        doAfterLoginSuccess();
    }

    @Override
    public void onResponseFailure(String something_went_wrong) {
        Toast.makeText(this, "response failure", Toast.LENGTH_SHORT).show();
    }

    private void doAfterLoginSuccess(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onDismissClicked(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
