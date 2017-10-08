package com.blowmymind.libgen.mainActivity_MVP;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blowmymind.libgen.R;
import com.blowmymind.libgen.adapters.BooksAdapter;
import com.blowmymind.libgen.dataLayer.DataLayer;
import com.blowmymind.libgen.dialogs.SearchDialogFragment;
import com.blowmymind.libgen.pojo.ScrapedItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * TODO: Move runOnUIThread method to datalayer itself
 */

public class MainActivity extends AppCompatActivity implements MainCallbackInterface, DataCallbackInterface {

    @BindView(R.id.am_fab_search)
    FloatingActionButton fab;

    @BindView(R.id.am_rv_books)
    RecyclerView recyclerView;

    @BindView(R.id.am_prog_loading)
    ProgressBar loadingIcon;

    @BindView(R.id.am_tv_start_search)
    TextView startSearchText;

    LinearLayoutManager mLayoutManager ;

    private ScrapedItem currentSearchItem = null;
    private DataLayer mSearchBox;
    private BooksAdapter mAdapter;

    private boolean isLoading = false;

    RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            if (currentSearchItem.hasMoreItems() && !isLoading ) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    mSearchBox.loadMore(new PaginationCallbackInterface() {
                        @Override
                        public void success(final int startIndex, final int items) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.notifyItemRangeInserted(startIndex,items);
                                }
                            });
                        }

                        @Override
                        public void failed() {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    searchFailed();
                                }
                            });
                        }
                    });
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(mScrollListener);
    }


    @OnClick(R.id.am_fab_search)
    void searchClicked() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            return;
        }
        ft.addToBackStack(null);

        SearchDialogFragment searchDialog =
                SearchDialogFragment.newInstance(currentSearchItem != null,
                        currentSearchItem);
        searchDialog.show(ft, "dialog");
    }

    @Override
    public void newSearchTerm(ScrapedItem currentSearchItem) {
        this.currentSearchItem = currentSearchItem;
        mSearchBox = new DataLayer(currentSearchItem);
        mSearchBox.initSearch(this);
        if(startSearchText.getVisibility()== View.VISIBLE){
            startSearchText.setVisibility(View.GONE);
        }
        loadingIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void searchFailed() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingIcon.setVisibility(View.GONE);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder
                        .setTitle(R.string.error)
                        .setMessage(R.string.error_msg)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void searchSuccess(final ScrapedItem books) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentSearchItem = books;
                mAdapter = new BooksAdapter(books);
                recyclerView.setAdapter(mAdapter);
                loadingIcon.setVisibility(View.GONE);
            }
        });
    }
}
