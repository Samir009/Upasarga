package com.samir.upasarga.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.samir.upasarga.Bottomsheets.ThemeChangeBottomSheetDialog;
import com.samir.upasarga.R;
import com.samir.upasarga.activities.BookDetailsActivity;
import com.samir.upasarga.activities.MainActivity;
import com.samir.upasarga.activities.SearchActivity;
import com.samir.upasarga.helpers.AppFragment;
import com.samir.upasarga.helpers.AppRecyclerViewAdapter;
import com.samir.upasarga.helpers.DefineClassType;
import com.samir.upasarga.helpers.MyApplication;
import com.samir.upasarga.helpers.ShowToast;
import com.samir.upasarga.models.HomeModel;
import com.samir.upasarga.presenters.HomePresenter;
import com.samir.upasarga.utils.UtilitiesFunctions;

import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends AppFragment implements HomePresenter.View {

    RecyclerView recyclerView;
    HomeRecylerViewAdapter homeRecylerViewAdapter;
    VHFooterRecyclerViewAdapter vhFooterRecyclerViewAdapter;
    HomePresenter homePresenter;

    SwipeRefreshLayout swipeRefreshLayout;
    SmoothProgressBar smoothProgressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeView(view);
        initializeListeners();
        prepareRecyclerView();

        showProgressBar();
        return view;
    }

    @Override
    protected void initializeView(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefreshHomeFrag);
        recyclerView = view.findViewById(R.id.recyclerview_home_fragment);
        smoothProgressBar = view.findViewById(R.id.homeFragProgressBarId);

    }

    @Override
    protected void initializeListeners() {
        homePresenter = new HomePresenter(this);
        homePresenter.getHome();
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorContainerBackground));
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.customColor));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homePresenter.getHome();
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

    @Override
    public void onHomeResponeSuccess(HomeModel homeModel) {
        Log.e("onHomeResponeSuccess: ", new GsonBuilder().create().toJson(homeModel));
        homeRecylerViewAdapter.add(homeModel);
        homeRecylerViewAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
        hideProgressBar();
    }

    @Override
    public void onHomeResponseFailure(String message) {
        Log.e("onHomeResponseFailure: ", "Home presenter response failure");
    }

    private void prepareRecyclerView() {
        homeRecylerViewAdapter = new HomeRecylerViewAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(homeRecylerViewAdapter);
    }

    private void prepareItemContentRecyclerView(RecyclerView innerRecyclerView, List<HomeModel.CategoriesList> categoriesLists) {
        Log.e("prepareItemContent: ", "prepare item content RecyclerView invoked");
        VHItemRecyclerViewAdapter vhItemRecyclerViewAdapter = new VHItemRecyclerViewAdapter(categoriesLists);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        innerRecyclerView.setLayoutManager(linearLayoutManager);
        innerRecyclerView.setAdapter(vhItemRecyclerViewAdapter);
    }

    private void prepareFooterContentRecyclerView(RecyclerView recyclerView, List<HomeModel.HomeList.Booklist> homeLists) {
        vhFooterRecyclerViewAdapter = new VHFooterRecyclerViewAdapter(homeLists);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(vhFooterRecyclerViewAdapter);
    }

//    clickable components


    private class HomeRecylerViewAdapter extends AppRecyclerViewAdapter {

        private final int TYPE_HEADER = 1;
        private final int TYPE_ITEM = 2;
        private final int TYPE_FOOTER = 3;

        private HomeModel homeModel;

        @Override
        public void add(Object object) {
            homeModel = DefineClassType.getType(object, HomeModel.class);
        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if (viewType == 1) {
                View itemHeader = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_content_homefrag, parent, false);
                return new VHHeader(itemHeader);
            } else if (viewType == 2) {
                View vhItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content_homefrag, parent, false);
                return new VHItem(vhItem);
            } else if (viewType == 3) {
                View itemFooter = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_content_homefrag, parent, false);
                return new VHFooter(itemFooter);
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        @Override
        public int getItemViewType(int position) {
            if (isTypeHeader(position)) {
                return TYPE_HEADER;
            } else if (isCategoryType(position)) {
                return TYPE_ITEM;
            }
            return TYPE_FOOTER;
        }
        private boolean isTypeHeader(int position) {
            return position == 0;
        }

        private boolean isCategoryType(int postion) {
            return postion == 1;
        }

        //==================================================================================================
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof VHHeader) {

                VHHeader vhHeader = (VHHeader) holder;

            } else if (holder instanceof VHItem) {

                VHItem vhItem = (VHItem) holder;
                prepareItemContentRecyclerView(vhItem.innerItemContentRecyclerView, homeModel.getCategoriesList());

            } else if (holder instanceof VHFooter) {

                VHFooter vhFooter = (VHFooter) holder;

                Log.e("onBindViewHolder: ", "VHFooter onBindViewHolder");
                List<HomeModel.HomeList> homeLists = homeModel.getHomeList();

                vhFooter.booktype.setText(UtilitiesFunctions.fromHtml(homeLists.get(position-2).getTitle()));

                // latestbook ko textview set

                prepareFooterContentRecyclerView(vhFooter.innerFooterRecyclerView, homeModel.getHomeList().get(position-2).getBooklists());
            }
        }

        @Override
        public int getItemCount() {
            if (homeModel != null) {
                return homeModel.getHomeList().size() + 2;
            }
            return 0;
        }
//====================================================================================================
    }

    private class VHItemRecyclerViewAdapter extends AppRecyclerViewAdapter {

        private List<HomeModel.CategoriesList> mainCategoryList;

        public VHItemRecyclerViewAdapter(List<HomeModel.CategoriesList> categoriesLists) {
            this.mainCategoryList = categoriesLists;
        }

        @Override
        public void add(Object object) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View innerItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content_homefrag_inner_item, parent, false);
            return new VHItemContent(innerItem);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VHItemContent vhItemContent = (VHItemContent) holder;
            HomeModel.CategoriesList categoriesList = mainCategoryList.get(position);
            vhItemContent.catName.setText(categoriesList.getName());
            Glide.with(MyApplication.getAppContext()).load(categoriesList.getImage()).into(vhItemContent.catImg);
        }

        @Override
        public int getItemCount() {
            return mainCategoryList.size();
        }

        private class VHItemContent extends RecyclerView.ViewHolder {

            TextView catName;
            ImageView catImg;

            public VHItemContent(@NonNull View itemView) {
                super(itemView);
                catName = itemView.findViewById(R.id.id_category_name);
                catImg = itemView.findViewById(R.id.id_category_img);
            }
        }
    }

    private class VHFooterRecyclerViewAdapter extends AppRecyclerViewAdapter {

        private List<HomeModel.HomeList.Booklist> homeLists;

        public VHFooterRecyclerViewAdapter(List<HomeModel.HomeList.Booklist> homeLists) {
            this.homeLists = homeLists;
        }

        @Override
        public void add(Object object) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View innerFooterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_content_homefrag_inner_item, parent, false);
            return new VHFooterContent(innerFooterView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Log.e("onBindViewHolder: ", "footerRecyclerViewAdapterd invoked ");
            VHFooterContent vhFooterContent = (VHFooterContent) holder;
            HomeModel.HomeList.Booklist homeList = homeLists.get(position);
            vhFooterContent.book_title.setText(homeList.getName());
            Glide.with(MyApplication.getAppContext()).load(homeList.getFeaturedImage()).into(vhFooterContent.imageView);
            vhFooterContent.book_author.setText(UtilitiesFunctions.fromHtml(homeList.getAuthorName()));
        }

        @Override
        public int getItemCount() {
            if (homeLists != null) {
                return homeLists.size();
            }
            return 0;
        }

        private class VHFooterContent extends RecyclerView.ViewHolder implements View.OnClickListener {
            LinearLayout getLatestBook;
            private ImageView imageView;
            private TextView book_title;
            private TextView book_author;

            public VHFooterContent(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.latest_book_img);
                book_title = itemView.findViewById(R.id.latest_book_title);
                book_author = itemView.findViewById(R.id.latest_book_author);
                getLatestBook = itemView.findViewById(R.id.get_latest_book);
                getLatestBook.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.get_latest_book:
                        Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
                        intent.putExtra("book_id",homeLists.get(getAdapterPosition()).getId());
                        intent.putExtra("pdf_url",homeLists.get(getAdapterPosition()).getUrl());
                        startActivity(intent);
                }
            }
        }
    }

    //=====================================================================================================
    private class VHHeader extends RecyclerView.ViewHolder implements View.OnClickListener{

        public VHHeader(@NonNull View itemView) {
            super(itemView);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
            }
        }
    }

    private class VHItem extends RecyclerView.ViewHolder {

        RecyclerView innerItemContentRecyclerView;

        public VHItem(@NonNull View itemView) {
            super(itemView);
            innerItemContentRecyclerView = itemView.findViewById(R.id.item_content_recycler_view);
        }
    }

    private class VHFooter extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView booktype;
        TextView viewall;
        RecyclerView innerFooterRecyclerView;

        public VHFooter(@NonNull View itemView) {
            super(itemView);
            booktype = itemView.findViewById(R.id.footer_title_id);
            viewall = itemView.findViewById(R.id.id_view_all_latest_book);
//            viewall.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    MainActivity mainActivity = new MainActivity();
//                    mainActivity.latestBookFun();
////                    FragmentManager fragmentManager = getFragmentManager();
////                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                    LatestBookFragment latestBookFragment = new LatestBookFragment();
////                    fragmentTransaction.replace(R.id.containerId, latestBookFragment);
////                    fragmentTransaction.commit();
//
//                }
//            });
            innerFooterRecyclerView = itemView.findViewById(R.id.footer_recycler_view);
        }
        @Override
        public void onClick(View v) {

        }
    }
}
