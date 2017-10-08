package com.blowmymind.libgen.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blowmymind.libgen.R;
import com.blowmymind.libgen.pojo.Book;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksAdapterViewHolder> {

    private final ArrayList<Book> books;

    public BooksAdapter(ArrayList<Book> books){
        this.books = books;
    }

    @Override
    public BooksAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_view, parent, false);
        return new BooksAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BooksAdapterViewHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BooksAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bv_tv_title)
        TextView bvTvTitle;
        @BindView(R.id.bv_tv_authors)
        TextView bvTvAuthors;
        @BindView(R.id.bv_tv_publisher)
        TextView bvTvPublisher;
        @BindView(R.id.bv_tv_pages)
        TextView bvTvPages;
        @BindView(R.id.bv_tv_year)
        TextView bvTvYear;
        @BindView(R.id.bv_tv_extension)
        TextView bvTvExtension;
        @BindView(R.id.bv_tv_lang)
        TextView bvTvLang;
        @BindView(R.id.bv_tv_size)
        TextView bvTvSize;

        @OnClick({R.id.bv_btn_link1, R.id.bv_btn_link2, R.id.bv_btn_link3, R.id.bv_btn_link4})
        public void onViewClicked(View view) {
            String bookUrl = null;
            switch (view.getId()) {
                case R.id.bv_btn_link1:
                    bookUrl = books.get(getAdapterPosition()).getMirrors().get(0);
                    break;
                case R.id.bv_btn_link2:
                    bookUrl = books.get(getAdapterPosition()).getMirrors().get(1);
                    break;
                case R.id.bv_btn_link3:
                    bookUrl = books.get(getAdapterPosition()).getMirrors().get(2);
                    break;
                case R.id.bv_btn_link4:
                    bookUrl = books.get(getAdapterPosition()).getMirrors().get(3);
                    break;
            }
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(bookUrl));
            view.getContext().startActivity(i);
        }

        public BooksAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(int position) {
            bvTvTitle.setText(books.get(position).getTitle());
            bvTvAuthors.setText(books.get(position).getAuthors());
            bvTvExtension.setText(books.get(position).getExtension());
            bvTvLang.setText(books.get(position).getLanguage());
            bvTvPublisher.setText(books.get(position).getPublisher());
            bvTvYear.setText(books.get(position).getYear());
            bvTvSize.setText(books.get(position).getSize());
            bvTvPages.setText(books.get(position).getPages());
        }
    }

}