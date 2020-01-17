package com.samir.upasarga.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.samir.upasarga.R;
import com.samir.upasarga.activities.BookDetailsActivity;
import com.samir.upasarga.apiservices.LatestBookApiService;
import com.samir.upasarga.helpers.AppFragment;
import com.samir.upasarga.helpers.AppRecyclerViewAdapter;
import com.samir.upasarga.helpers.DefineClassType;
import com.samir.upasarga.helpers.MyApplication;
import com.samir.upasarga.models.LatestBooksModel;
import com.samir.upasarga.presenters.LatestBookPresenter;
import com.samir.upasarga.utils.UtilitiesFunctions;

import java.util.zip.Inflater;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestBookFragment extends AppFragment implements LatestBookPresenter.View {

    SmoothProgressBar smoothProgressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView recyclerView;
    LatestBookRecyclerViewAdapter latestBookRecyclerViewAdapter;
    LatestBookPresenter latestBookPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest_book, container, false);
        initializeView(view);
        initializeListeners();
        prepareRecylerView();
        return view;
    }

    @Override
    protected void initializeView(View view) {
        smoothProgressBar = view.findViewById(R.id.latestbookProgressBarId);
        swipeRefreshLayout =  view.findViewById(R.id.latestbookSwipeToRefresh);
        recyclerView = view.findViewById(R.id.latest_book_recycler_view);
    }

    @Override
    protected void initializeListeners() {
        latestBookPresenter = new LatestBookPresenter(this);
        latestBookPresenter.showLatestBooks();
        showProgressBar();
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorContainerBackground));
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.customColor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                latestBookPresenter.showLatestBooks();
                Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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

    private void prepareRecylerView(){
        latestBookRecyclerViewAdapter = new LatestBookRecyclerViewAdapter();
//        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(latestBookRecyclerViewAdapter);
    }

    @Override
    public void onLatestBookResponseSuccess(LatestBooksModel latestBooksModel) {
        Log.e("latestbook", "latest book response success");
        latestBookRecyclerViewAdapter.add(latestBooksModel);
        latestBookRecyclerViewAdapter.notifyDataSetChanged();
        hideProgressBar();
    }

    @Override
    public void onLatestBookResponseFailure(String ErrorMessage) {
        Log.e("latestbook", "latest book response failed");
    }

    private class LatestBookRecyclerViewAdapter extends AppRecyclerViewAdapter {

        LatestBooksModel latestBooksModel;

        @Override
        public void add(Object object) {
            latestBooksModel = DefineClassType.getType(object, LatestBooksModel.class);
            Log.e( "add: ", new GsonBuilder().create().toJson(latestBooksModel));
        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_latest_book_rv, parent, false);
            return new VHItem(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VHItem vhItem = (VHItem) holder;
            LatestBooksModel.Datum datum = latestBooksModel.getData().get(position);
            Glide.with(MyApplication.getAppContext()).load(datum.getFeaturedImage()).into(vhItem.imageView);
            vhItem.title.setText(UtilitiesFunctions.fromHtml(datum.getName()));
        }

        @Override
        public int getItemCount() {
            if(latestBooksModel != null){
                return latestBooksModel.getData().size();
            }
            return 0;
        }

        private class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener{
            private LinearLayout bottom_nav_latest_book;
            private ImageView imageView;
            private TextView title;
            public VHItem(@NonNull View itemView) {
                super(itemView);
                bottom_nav_latest_book = itemView.findViewById(R.id.bottom_nav_latest_book);
                imageView = itemView.findViewById(R.id.item_rv_latest_book_img);
                title = itemView.findViewById(R.id.item_rv_latest_book_title);

                bottom_nav_latest_book.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.bottom_nav_latest_book:
                        Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
                        intent.putExtra("pdf_url", latestBooksModel.getData().get(getAdapterPosition()).getUrl());
                        intent.putExtra("book_id",latestBooksModel.getData().get(getAdapterPosition()).getId());
                        startActivity(intent);
                        break;
                }
            }
        }
    }
}
