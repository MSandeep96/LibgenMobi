package com.blowmymind.libgen.dialogs;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.blowmymind.libgen.R;
import com.blowmymind.libgen.mainActivity_MVP.MainCallbackInterface;
import com.blowmymind.libgen.pojo.ScrapedItem;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SearchDialogFragment extends DialogFragment {

    private static String ARG_PARAM1 = "param1";
    private static String ARG_PARAM2 = "param2";
    private boolean hasSearchTerm;
    private ScrapedItem previousSearch;

    @BindView(R.id.fsd_btn_clear)
    Button clear;

    @BindView(R.id.fsd_et_search)
    EditText searchTerm;

    @OnClick(R.id.fsd_btn_clear)
    void clearClicked(View view) {
        searchTerm.setText("");
        view.setVisibility(View.GONE);
    }

    @OnTextChanged(R.id.fsd_et_search)
    void onTextChanged(Editable editable) {
        if (editable.toString().length() == 0) {
            clear.setVisibility(View.GONE);
        } else {
            clear.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.fsd_btn_go)
    void startSearch() {
        String currentSearchTerm = searchTerm.getText().toString().trim();
        if (currentSearchTerm.length() == 0 ||
                (previousSearch != null &&
                        previousSearch.getSearchTerm().equals(currentSearchTerm))) {
            dismiss();
            return;
        }
        String encodedSearchTerm;
        try {
            encodedSearchTerm = URLEncoder.encode(currentSearchTerm.toLowerCase(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            searchTerm.setError("Invalid characters");
            dismiss();
            return;
        }
        ScrapedItem item = new ScrapedItem(currentSearchTerm,encodedSearchTerm);
        ((MainCallbackInterface) getContext()).newSearchTerm(item);
        dismiss();
    }

    public SearchDialogFragment() {
        // Required empty public constructor
    }

    /**
     * @param hasSearchTerm  If a search has been performed, contains true
     * @param previousSearch The string of the previous search
     * @return the dialog with inflated with the above values
     */
    public static SearchDialogFragment newInstance(boolean hasSearchTerm, ScrapedItem previousSearch) {
        SearchDialogFragment fragment = new SearchDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, hasSearchTerm);
        args.putParcelable(ARG_PARAM2, previousSearch);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hasSearchTerm = getArguments().getBoolean(ARG_PARAM1);
            if (hasSearchTerm) {
                previousSearch = getArguments().getParcelable(ARG_PARAM2);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow()
                .setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_dialog, container, false);
        ButterKnife.bind(this, view);
        setUpDialog();
        return view;
    }

    private void setUpDialog() {
        if (hasSearchTerm) {
            searchTerm.append(previousSearch.getSearchTerm());
            if (previousSearch.getSearchTerm().length() != 0)
                clear.setVisibility(View.VISIBLE);
        }
    }

}