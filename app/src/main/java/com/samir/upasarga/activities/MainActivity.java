package com.samir.upasarga.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.samir.upasarga.R;
import com.samir.upasarga.Bottomsheets.ThemeChangeBottomSheetDialog;
import com.samir.upasarga.fragments.HomeFragment;
import com.samir.upasarga.fragments.LatestBookFragment;
import com.samir.upasarga.fragments.NewsFeedFragment;
import com.samir.upasarga.helpers.AppActivity;

import static com.samir.upasarga.utils.Utilities.isNightModeOn;

public class MainActivity extends AppActivity implements View.OnClickListener, ThemeChangeBottomSheetDialog.ChangeThemeListener {

    private  BottomSheetBehavior mBottomSheetBehavior;

    private TextView toolbar_title;

    private LinearLayout startImage;

    private ImageView themeIcon;
    private ImageView notificationIcon;

    private AppBarLayout appBarLayout;

//    SwipeRefreshLayout swipeRefreshLayout;
//    int num = 0;
//    TextView textView;

//    for bottom navigation bar

    private LinearLayout homeBottomNavItem;
    private LinearLayout latestBookBottomNavItem;
    private LinearLayout newsBottomNavItem;
    private LinearLayout userBottomNavItem;
    private LinearLayout lastSelectedView;
    private LinearLayout profile_to_home;
    private LinearLayout bottom_nav;

    private EditText name;
    private EditText contact;
    private EditText email;
    private EditText password;

    private Button edit_profile;
    private Button save_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        BottomSheetBehavior();
        initializeListeners();
        setUpFragment(new HomeFragment(), "home");

        if(isNightModeOn()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            ThemeChangeBottomSheetDialog themeChangeBottomSheetDialog = new ThemeChangeBottomSheetDialog();
//            themeChangeBottomSheetDialog.
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initializeView() {

//        textView = findViewById(R.id.swipeCountTest);
//        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        startImage = findViewById(R.id.search_layout);
//        themeIcon = findViewById(R.id.toolbar_day_night_icon);
        notificationIcon = findViewById(R.id.toolbar_end_image_view);

        appBarLayout = findViewById(R.id.notification_app_bar_layout);

        homeBottomNavItem = findViewById(R.id.home_bottom_nav_item);
        latestBookBottomNavItem = findViewById(R.id.latest_book_bottom_nav_item);
        newsBottomNavItem = findViewById(R.id.news_feed_bottom_nav_item);
        userBottomNavItem = findViewById(R.id.user_bottom_nav_item);
        toolbar_title = findViewById(R.id.toolbar_title);
        bottom_nav = findViewById(R.id.bottom_nav_bar);
        profile_to_home = findViewById(R.id.profile_back_to_home);

        name = findViewById(R.id.profile_name);
        contact = findViewById(R.id.profile_contact);
        email = findViewById(R.id.profile_email);
        password = findViewById(R.id.profile_password);

        name.setEnabled(false);
        contact.setEnabled(false);
        email.setEnabled(false);
        password.setEnabled(false);

        edit_profile = findViewById(R.id.profile_edit);
        save_profile = findViewById(R.id.profile_save);

        save_profile.setTextColor(getResources().getColor(R.color.colorDefault));
        save_profile.setEnabled(false);
    }

    private void BottomSheetBehavior(){
        View bottomsheet = findViewById(R.id.bottom_sheet_id);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.e( "onStateChanged: "," bottom nav collapsed." );
                        appBarLayout.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.e( "onStateChanged: "," bottom nav expanded." );
//                        appBarLayout.setVisibility(View.INVISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.e( "onStateChanged: "," bottom nav dragged." );
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.e( "onStateChanged: "," bottom nav hidden." );
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.e( "onStateChanged: "," bottom nav settling." );
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + newState);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    protected void initializeListeners() {
//        to refresh the main page
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//
//                onCreate(null);
////                num ++;
////                textView.setText(String.valueOf(num));
//
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
        startImage.setOnClickListener(this);
//        themeIcon.setOnClickListener(this);
        notificationIcon.setOnClickListener(this);
        homeBottomNavItem.setOnClickListener(this);
        latestBookBottomNavItem.setOnClickListener(this);
        newsBottomNavItem.setOnClickListener(this);
        userBottomNavItem.setOnClickListener(this);
        lastSelectedView = homeBottomNavItem;
        profile_to_home.setOnClickListener(this);
        edit_profile.setOnClickListener(this);
        save_profile.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.search_layout:

                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
                
//            case R.id.toolbar_day_night_icon:
//                ThemeChangeBottomSheetDialog themeChangeBottomSheetDialog = new ThemeChangeBottomSheetDialog();
//                themeChangeBottomSheetDialog.show(getSupportFragmentManager(),"themeBottomSheet");
////                Toast.makeText(this, "themeIcon clicked", Toast.LENGTH_SHORT).show();
//                break;

            case R.id.toolbar_end_image_view:
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                break;

            case R.id.home_bottom_nav_item:
                if (!getVisibleFragment("home")) {
                    setUpBottomNavigation(homeBottomNavItem);
                    setUpFragment(new HomeFragment(), "home");
                }
                break;

            case R.id.latest_book_bottom_nav_item:

//                latestBookFun();

                if (!getVisibleFragment("latest_book")) {
                    setUpBottomNavigation(latestBookBottomNavItem);
                    setUpFragment(new LatestBookFragment(), "latest_book");
                }
                break;

            case R.id.news_feed_bottom_nav_item:

                if (!getVisibleFragment("news_feed")) {
                    setUpBottomNavigation(newsBottomNavItem);
                    setUpFragment(new NewsFeedFragment(), "news_feed");
                }
                break;

            case R.id.user_bottom_nav_item:

                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;

            case R.id.profile_back_to_home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;

            case R.id.profile_edit:
                name.setEnabled(true);
                contact.setEnabled(true);
                email.setEnabled(true);
                password.setEnabled(true);
                edit_profile.setEnabled(false);

                edit_profile.setTextColor(getResources().getColor(R.color.colorDefault));

                save_profile.setEnabled(true);
                save_profile.setTextColor(getResources().getColor(R.color.colorWhite));
                break;

            case R.id.profile_save:
                break;
        }
    }

//    calling latest book function

    public void latestBookFun(){
            setUpBottomNavigation(latestBookBottomNavItem);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.containerId, new LatestBookFragment());
            fragmentTransaction.commit();
    }

    public boolean getVisibleFragment(String tag) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.containerId);
        assert currentFragment.getTag() != null;
        if (currentFragment.getTag().equals(tag)) {
            return true;
        }
        return false;
    }

    /**
     * @param fragment fragment to load
     */
    private void setUpFragment(Fragment fragment, String tag) {

        switch (tag) {
            case "home":
                toolbar_title.setText("Home");
                break;
            case "latest_book":
                toolbar_title.setText("Latest Books");
                break;
            case "news_feed":
                toolbar_title.setText("News Feeds");
                break;
            case "register":
                toolbar_title.setText("register");
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.containerId, fragment, tag);
        fragmentTransaction.commit();
    }

    public void setUpBottomNavigation(LinearLayout view) {
        if (lastSelectedView != null) {
            for (int i = 0; i < lastSelectedView.getChildCount(); i++) {
                View view1 = lastSelectedView.getChildAt(i);
                if (view1 instanceof ImageView) {
                    ((ImageView) view1).setColorFilter(getResources().getColor(R.color.colorDefault));

                } else {
                    ((TextView) view1).setTextColor(getResources().getColor(R.color.colorDefault));
//                    ((TextView) view1).setTextSize(11);
//                    setTextChangeAnimation((TextView) view1, 12, 11);
                }
            }
        }

//        if certain icon or layout is selected at bottom navigation bar
        for (int i = 0; i < view.getChildCount(); i++) {
            View view1 = view.getChildAt(i);
            if (view1 instanceof ImageView) {
                ((ImageView) view1).setColorFilter(ContextCompat.getColor(this, R.color.customColor));

            } else {
                ((TextView) view1).setTextColor(ContextCompat.getColor(this, R.color.customColor));
//                setTextChangeAnimation((TextView) view1, 11, 12);
            }
        }
        lastSelectedView = view;
    }

    @Override
    public void onThemeSelected(String selectedTheme) {
        Log.e( "onThemeSelected: ",selectedTheme );
        Toast.makeText(this, selectedTheme, Toast.LENGTH_SHORT).show();
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.item_options,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
}