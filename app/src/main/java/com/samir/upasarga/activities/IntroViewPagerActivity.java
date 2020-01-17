package com.samir.upasarga.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.samir.upasarga.R;
import com.samir.upasarga.helpers.AppActivity;
import com.samir.upasarga.helpers.MyApplication;
import com.samir.upasarga.utils.Utilities;
import com.samir.upasarga.vpitems.ScreenItem;

import java.util.ArrayList;
import java.util.List;

public class IntroViewPagerActivity extends AppActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private TabLayout tabIndicator;
    private Button btnnext;
    private Button btngetstarted;

    int position = 0;

    Animation btnAnim;

    ViewPagerAdapter viewPagerAdapter;
    List<ScreenItem> mList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout .activity_intro_view_pager);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        if(isIntroOpened()){
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

//        setContentView(R.layout.activity_intro_view_pager);

        mList.add(new ScreenItem("Welcome to e-Library", "e-Library is the final project submission of android training at Upasarga Technologies. Digital library, it can be accessed from all over the world.", R.drawable.ic_welcome));
        mList.add(new ScreenItem("Increase your knowledge", "You can read any genre of books here whenever you want. The objective of this app development is to make study accessable to all.", R.drawable.ic_about_us));
        mList.add(new ScreenItem("Enjoy the study", "Only you have to do is register and do login in this app and learn with joy. \n \n Thank you", R.drawable.ic_start));

        initializeView();
        initializeListeners();
        prepareViewPager();
//        continuePage();

    }

    @Override
    protected void initializeView() {
        viewPager = findViewById(R.id.view_pager);
        tabIndicator = findViewById(R.id.id_tablayout);
        btnnext = findViewById(R.id.btn_next);
        btngetstarted = findViewById(R.id.btn_get_started);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_animation);
    }

    @Override
    protected void initializeListeners() {
        btnnext.setOnClickListener(this);
        btngetstarted.setOnClickListener(this);
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size() - 1){
                    loadLastPage();
                }
                else {
                    continuePage();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size() - 1){
                    loadLastPage();
                }
                else {
                    continuePage();
                }
            }
        });

    }

    private void prepareViewPager() {

        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
//        viewPager.setPageTransformer(true, new AntiClockSpinTransformation());
        tabIndicator.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                position = viewPager.getCurrentItem();

                //        when user clicks next button to slide the page
                if(position < mList.size()){
                    position++;
                    viewPager.setCurrentItem(position);
                }
                if(position == mList.size()-1){
                    //show the get started button and hide the indicator button.
                    loadLastPage();
                }
                break;
            case R.id.btn_get_started:
                Utilities.setIntroCompleted(true);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }

    private void continuePage(){
        btnnext.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.VISIBLE);
        btngetstarted.setVisibility(View.INVISIBLE);
    }

    private void loadLastPage() {
        btnnext.setVisibility(View.INVISIBLE);
        btngetstarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE );
        btngetstarted.setAnimation(btnAnim);
    }

//    private void savePrefData(){
//        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("isIntroOpened",true);
//        editor.commit();
//    }

//    private void isIntroOpened(){
//        if(Utilities.saveIsIntroOpened();){
//
//        }
//    }

//        private boolean isIntroOpened(){
//        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
//        Boolean isIntroActivityOpenedBefore = preferences.getBoolean("isIntroOpened",false);
//        return isIntroActivityOpenedBefore;
//    }

    private class ViewPagerAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup collection, int position) {

            LayoutInflater inflater = LayoutInflater.from(IntroViewPagerActivity.this);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_view_pager, collection, false);

            ImageView imageView = layout.findViewById(R.id.vp_img);
            TextView title = layout.findViewById(R.id.vp_title);
            TextView description = layout.findViewById(R.id.vp_description);

            title.setText(mList.get(position).getTitle());
            description.setText(mList.get(position).getDescription());

            Glide.with(MyApplication.getAppContext()).load(mList.get(position).getScreenImg()).into(imageView);

//            imageView.setImageResource(mList.get(position).getScreenImg());

            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
            collection.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

}
