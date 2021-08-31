package com.intro.fancyvideomaker.utils.listdecorators;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinearListDecorator extends RecyclerView.ItemDecoration {

    private int spacing;

    public LinearListDecorator(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);

        outRect.left = spacing;
        outRect.right = spacing;
        outRect.top = spacing;

        if (position == parent.getChildCount()) {
            outRect.bottom = spacing;
        }
    }
}