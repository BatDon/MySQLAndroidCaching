package com.test.table.MySQLAndroidCaching3;

import android.graphics.Rect;

//import android.support.annotation.NonNull;
import android.view.View;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class VerticalSpacingItemDecorator extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public VerticalSpacingItemDecorator(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        outRect.top = verticalSpaceHeight;
    }
}

