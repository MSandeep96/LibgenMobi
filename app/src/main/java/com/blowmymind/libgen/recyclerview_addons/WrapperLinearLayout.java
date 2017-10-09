package com.blowmymind.libgen.recyclerview_addons;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Bug in RecyclerView. This class handles the crash.
 */

public class WrapperLinearLayout extends LinearLayoutManager {
    public WrapperLinearLayout(Context context) {
        super(context);
    }

    public WrapperLinearLayout(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public WrapperLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        }catch(IndexOutOfBoundsException e){
            Log.i("Bug","That bug occurred again");
        }
    }
}
