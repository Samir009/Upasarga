package com.samir.upasarga.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.GsonBuilder;
import com.samir.upasarga.R;
import com.samir.upasarga.helpers.AppActivity;
import com.samir.upasarga.helpers.AppRecyclerViewAdapter;
import com.samir.upasarga.helpers.MyApplication;
import com.samir.upasarga.models.BookDetailsModel;
import com.samir.upasarga.presenters.BookDetailsPresenter;
import com.samir.upasarga.utils.UtilitiesFunctions;

public class BookDetailsActivity extends AppActivity implements BookDetailsPresenter.View, View.OnClickListener {

    private ImageView book_img;
    private TextView book_name;
    private TextView published_date;
    private TextView writer_name;
    private TextView book_category;
    private TextView book_description;

    private LinearLayout backarrow;
    private  BookDetailsModel bookdata;

    private BookDetailsPresenter bookDetailsPresenter;

    int bookid;
    String pdfurl;

    FloatingActionButton readMe;


//    similar books
    private RecyclerView similarBooksRecyclerView;
    SimilarBooksRecyclerViewAdapter similarBooksRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        initializeView();
        initializeListeners();
        getDataFromIntent();
        prepareSimilarBooksRecyclerView();
    }

    @Override
    protected void initializeView() {
        book_img = findViewById(R.id.id_book_details_img);
        book_name = findViewById(R.id.id_book_details_book_name);
        published_date = findViewById(R.id.id_book_details_published_date);
        writer_name = findViewById(R.id.id_book_details_writer_name);
        book_category = findViewById(R.id.id_book_details_book_category);
        book_description = findViewById(R.id.id_book_description);
        backarrow = findViewById(R.id.bookdetails_back_arrow_layout);
        readMe = findViewById(R.id.fab_reading_icon);
        similarBooksRecyclerView = findViewById(R.id.similar_books_recyclerview);
    }

    @Override
    protected void initializeListeners() {
        backarrow.setOnClickListener(this);
        bookDetailsPresenter = new BookDetailsPresenter(this);
        readMe.setOnClickListener(this);
    }

    private void getDataFromIntent() {

        bookid = getIntent().getIntExtra("book_id",0);
//        ShowToast.withLongMessage(" "+bookid);
        pdfurl = getIntent().getStringExtra("pdf_url");
        bookDetailsPresenter.bookDetails(bookid);
    }

    @Override
    public void onBookDetailsResponseSuccess(BookDetailsModel bookDetailsModel) {

        Log.e("setUpData: ", new GsonBuilder().create().toJson(bookDetailsModel));
        setUpData(bookDetailsModel);
    }

    private void setUpData(BookDetailsModel bookDetailModel) {

        Glide.with(MyApplication.getAppContext()).load(bookDetailModel.getBook().getFeaturedImage()).into(book_img);
        book_name.setText(bookDetailModel.getBook().getName());
        published_date.setText(UtilitiesFunctions.formatToClientFullDateTime(bookDetailModel.getBook().getPublishedDate()));
        writer_name.setText(bookDetailModel.getBook().getAuthorName());
        book_category.setText(bookDetailModel.getBook().getCategory());
        book_description.setText(UtilitiesFunctions.fromHtml(bookDetailModel.getBook().getDescription()));
    }

    @Override
    public void onBookDetailsResponseFailure(String message) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bookdetails_back_arrow_layout:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.fab_reading_icon:
                Toast.makeText(this, pdfurl, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfurl));
//                Intent intent = new Intent(BookDetailsActivity.this, PdfViewer.class);

                startActivity(intent);
                break;
        }
    }

//    similar books

    private void prepareSimilarBooksRecyclerView(){
        similarBooksRecyclerViewAdapter = new SimilarBooksRecyclerViewAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
        similarBooksRecyclerView.setLayoutManager(linearLayoutManager);
        similarBooksRecyclerView.setAdapter(similarBooksRecyclerViewAdapter);
    }

    private class SimilarBooksRecyclerViewAdapter extends AppRecyclerViewAdapter {

        @Override
        public void add(Object object) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_similar_books, parent, false);
            return new VHItem(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        private class VHItem extends RecyclerView.ViewHolder {
            private LinearLayout openSimilarBook;
            private ImageView imageView;
            private TextView title;
            public VHItem(@NonNull View itemView) {
                super(itemView);
                openSimilarBook = itemView.findViewById(R.id.item_similar_book);
                imageView = itemView.findViewById(R.id.similar_book_img);
                title = itemView.findViewById(R.id.similar_book_title);
            }
        }
    }
}
