package com.blowmymind.libgen.MainActivity_MVP;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.blowmymind.libgen.DataLayer.DataLayer;
import com.blowmymind.libgen.R;

import org.jsoup.nodes.Document;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainCallbackInterface{

    @BindView(R.id.am_fab_search)
    FloatingActionButton fab;

    @OnClick(R.id.am_fab_search)
    void searchClicked(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            return;
        }
        ft.addToBackStack(null);
        /*


        // Create and show the dialog.
        DialogFragment newFragment = MyDialogFragment.newInstance(mStackLevel);
        newFragment.show(ft, "dialog");*/
    }

    @BindView(R.id.am_rv_books)
    RecyclerView booksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void setUpRecyclerView() {

    }

    @Override
    public void newSearchTerm(String currentSearchTerm) {
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
