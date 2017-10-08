package com.blowmymind.libgen.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blowmymind.libgen.R;
import com.blowmymind.libgen.pojo.ScrapedItem;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BooksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ScrapedItem scrapedItem;
    private final int VIEW_TYPE = 0;
    private final int LOAD_TYPE = 1;


    public BooksAdapter(ScrapedItem item){
        scrapedItem = item;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==scrapedItem.getBooks().size()){
            return LOAD_TYPE;
        }else{
            return VIEW_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE) {
            //actual book view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_view, parent, false);
            return new BooksAdapterViewHolder(view);
        }else{
            //loading icon for pagination
            LinearLayout mLinearLayout = new LinearLayout(parent.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLinearLayout.setLayoutParams(params);
            mLinearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            ProgressBar progressBar = new ProgressBar(parent.getContext());
            mLinearLayout.addView(progressBar);
            return new RecyclerView.ViewHolder(mLinearLayout) {
            };
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BooksAdapterViewHolder){
            ((BooksAdapterViewHolder)holder).bindItem(position);
        }
    }

    @Override
    public int getItemCount() {
        if(scrapedItem.hasMoreItems())
            return scrapedItem.getBooks().size() + 1;
        return scrapedItem.getBooks().size();
    }

    class BooksAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bv_tv_title)
        TextView bvTvTitle;
        @BindView(R.id.bv_tv_authors)
        TextView bvTvAuthors;
        @BindView(R.id.bv_tv_details)
        TextView bvTvDetails;
        @BindView(R.id.bv_exp_details)
        ExpandableLayout detailsExpandable;
        @BindView(R.id.bv_exp_download)
        ExpandableLayout downloadExpandable;

        @OnClick(R.id.bv_btn_details)
        void expandDetails(){
            if(detailsExpandable.isExpanded())
                detailsExpandable.collapse();
            else
                detailsExpandable.expand();
        }

        @OnClick(R.id.bv_btn_download)
        void expandDownload(){
            if(downloadExpandable.isExpanded()){
                downloadExpandable.collapse();
            }else{
                downloadExpandable.expand();
            }
        }

        @OnClick({R.id.bv_btn_link1, R.id.bv_btn_link2, R.id.bv_btn_link3, R.id.bv_btn_link4})
        void onViewClicked(View view) {
            String bookUrl = null;
            switch (view.getId()) {
                case R.id.bv_btn_link1:
                    bookUrl = scrapedItem.getBooks().get(getAdapterPosition()).getMirrors().get(0);
                    break;
                case R.id.bv_btn_link2:
                    bookUrl = scrapedItem.getBooks().get(getAdapterPosition()).getMirrors().get(1);
                    break;
                case R.id.bv_btn_link3:
                    bookUrl = scrapedItem.getBooks().get(getAdapterPosition()).getMirrors().get(2);
                    break;
                case R.id.bv_btn_link4:
                    bookUrl = scrapedItem.getBooks().get(getAdapterPosition()).getMirrors().get(3);
                    break;
            }
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(bookUrl));
            view.getContext().startActivity(i);
        }

        BooksAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindItem(int position) {
            bvTvTitle.setText(scrapedItem.getBooks().get(position).getTitle());
            bvTvAuthors.setText(scrapedItem.getBooks().get(position).getAuthors());
            bvTvDetails.setText(scrapedItem.getBooks().get(position).getDetails());
        }
    }

}