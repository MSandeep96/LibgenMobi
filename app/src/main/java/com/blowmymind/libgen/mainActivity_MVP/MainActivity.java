package com.blowmymind.libgen.mainActivity_MVP;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.blowmymind.libgen.dataLayer.DataLayer;
import com.blowmymind.libgen.dialogs.SearchDialogFragment;
import com.blowmymind.libgen.R;

import org.jsoup.nodes.Document;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainCallbackInterface, DataCallbackInterface {

    @BindView(R.id.am_fab_search)
    FloatingActionButton fab;
    private String currentSearchTerm = null;

    @OnClick(R.id.am_fab_search)
    void searchClicked() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            return;
        }
        ft.addToBackStack(null);

        SearchDialogFragment searchDialog =
                SearchDialogFragment.newInstance(currentSearchTerm != null,
                        currentSearchTerm);
        searchDialog.show(ft, "dialog");
    }

    @BindView(R.id.am_rv_books)
    RecyclerView booksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void setUpRecyclerView() {

    }

    @Override
    public void newSearchTerm(String currentSearchTerm) {
        this.currentSearchTerm = currentSearchTerm;
        DataLayer mSearchBox = new DataLayer(currentSearchTerm);
        mSearchBox.initSearch(this);
    }

    @Override
    public void searchFailed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setTitle(R.string.error)
                .setMessage(R.string.error_msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @Override
    public void searchSuccess(Document doc) {

    }
}
