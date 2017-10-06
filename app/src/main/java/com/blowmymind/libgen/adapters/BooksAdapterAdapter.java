package com.blowmymind.libgen.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


public class BooksAdapterAdapter extends RecyclerView.Adapter<BooksAdapterAdapter.BooksAdapterViewHolder> {

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
        return 0;
    }

    public class BooksAdapterViewHolder extends RecyclerView.ViewHolder {

        public BooksAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(int position) {
        }
    }

}