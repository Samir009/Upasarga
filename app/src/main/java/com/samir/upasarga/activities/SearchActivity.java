package com.samir.upasarga.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.samir.upasarga.R;
import com.samir.upasarga.helpers.AppActivity;
import com.samir.upasarga.helpers.AppRecyclerViewAdapter;
import com.samir.upasarga.helpers.DefineClassType;
import com.samir.upasarga.helpers.MyApplication;
import com.samir.upasarga.models.SearchModel;
import com.samir.upasarga.presenters.SearchPresenter;
import com.samir.upasarga.utils.UtilitiesFunctions;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class SearchActivity extends AppActivity implements SearchPresenter.View, View.OnClickListener {

    SmoothProgressBar smoothProgressBar;
    LinearLayout backarrow;
    LinearLayout search_icon;
    EditText content;

    SearchPresenter searchPresenter;
    RecyclerView recyclerView;

    SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeView();
        initializeListeners();
        onSearchText();
        prepareRecyclerView();
    }

    @Override
    protected void initializeView() {
        search_icon = findViewById(R.id.search_layout_icon);
        smoothProgressBar = findViewById(R.id.SearchProgressBarId);
        backarrow = findViewById(R.id.search_back_arrow_layout);
        content = findViewById(R.id.search_content);
        recyclerView = findViewById(R.id.search_recycler_view);
    }

    @Override
    protected void initializeListeners() {
        backarrow.setOnClickListener(this);
        search_icon.setOnClickListener(this);
        searchPresenter = new SearchPresenter(this);

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
            case R.id.search_layout_icon:
                showProgressBar();
                Toast.makeText(this, "search button clicked", Toast.LENGTH_SHORT).show();
                searchPresenter.search(content.getText().toString());

//                doSearch(content);
                break;
            case R.id.search_back_arrow_layout:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
    }

    @Override
    public void onSearchResponeSuccess(SearchModel searchModel) {

        searchRecyclerViewAdapter.add(searchModel);
        searchRecyclerViewAdapter.notifyDataSetChanged();
        hideProgressBar();
    }

    @Override
    public void onSearchResponseFailure(String something_went_wrong) {
        Toast.makeText(this, "response failure", Toast.LENGTH_SHORT).show();
    }

    private void onSearchText(){
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showProgressBar();
                searchPresenter.search(content.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

//    private void doSearch(EditText content){
//        if(content.getText().toString().isEmpty()){
//            Toast.makeText(this, "input words to search", Toast.LENGTH_SHORT).show();
//        } else {
//            if(UtilitiesFunctions.isNetworkAvailable(SearchActivity.this)){
//                Log.e( "doSearch: ","search: internet available" );
//
//                showProgressBar();
//                searchPresenter.search(content.getText().toString());
//            } else {
//                Toast.makeText(this, "please connect to internet", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }


    private void prepareRecyclerView(){
        searchRecyclerViewAdapter =  new SearchRecyclerViewAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchRecyclerViewAdapter);
    }

    private class SearchRecyclerViewAdapter extends AppRecyclerViewAdapter {

        SearchModel searchModel;

        @Override
        public void add(Object object) {

        searchModel = DefineClassType.getType(object, SearchModel.class);

//            String[] myTitleList = new String[]{"Hot Destination", "Popular Places", "Our Tradition", "Popular Hotels", "Yummy! Foods", "Let's Buy ", "QR Places", "QR Places", "QR Places", "QR Places"};

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(content.getContext()).inflate(R.layout.item_search_content, parent, false);
            return new VHItem(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VHItem vhItem = (VHItem) holder;
//            vhItem.title.setText(myTitleList[position]);
            SearchModel.Datum datum = searchModel.getData().get(position);
            Glide.with(MyApplication.getAppContext()).load(datum.getFeaturedImage()).into(vhItem.imageView);
            vhItem.title.setText(UtilitiesFunctions.fromHtml(datum.getName()));
            vhItem.description.setText(UtilitiesFunctions.fromHtml(datum.getDescription()));
            vhItem.notificationTime.setText(UtilitiesFunctions.durationEnglish(datum.getPublishedDate()));
        }

        @Override
        public int getItemCount() {
            if(searchModel != null){
                return searchModel.getData().size();
            }
            return 0;
        }

        private class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {
            private LinearLayout getSearchedItem;
            private ImageView imageView;
            private TextView title;
            private TextView description;
            private TextView notificationTime;

            public VHItem(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.notification_image);
                title = itemView.findViewById(R.id.notification_book_title);
                description = itemView.findViewById(R.id.notification_book_description);
                notificationTime = itemView.findViewById(R.id.notification_time);
                getSearchedItem = itemView.findViewById(R.id.get_searched_item);
                initializeListener();
            }

            private void initializeListener(){
                getSearchedItem.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.get_searched_item:
//                        Toast.makeText(SearchActivity.this, p + " item clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SearchActivity.this, BookDetailsActivity.class);
                        intent.putExtra("book_id",searchModel.getData().get(getAdapterPosition()).getId());
                        startActivity(intent);
                }
            }
        }
    }
}
