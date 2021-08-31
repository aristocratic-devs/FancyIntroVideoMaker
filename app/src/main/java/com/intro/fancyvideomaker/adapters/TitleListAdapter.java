package com.intro.fancyvideomaker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.intro.fancyvideomaker.R;


import java.util.ArrayList;

public class TitleListAdapter extends RecyclerView.Adapter<TitleListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> titleAssetsPath;
    private String assetPathName;
    private OnAnimationClickListener onAnimationClickListener;
    private String bgPath = null;

    public TitleListAdapter(Context context, ArrayList<String> titleAssetsPath, String assetPathName, OnAnimationClickListener onAnimationClickListener, String bgPath) {
        this.context = context;
        this.titleAssetsPath = titleAssetsPath;
        this.assetPathName = assetPathName;
        this.onAnimationClickListener = onAnimationClickListener;
        this.bgPath = bgPath;
    }

    public TitleListAdapter(Context context, ArrayList<String> titleAssetsPath, String assetPathName, OnAnimationClickListener onAnimationClickListener) {
        this.context = context;
        this.titleAssetsPath = titleAssetsPath;
        this.assetPathName = assetPathName;
        this.onAnimationClickListener = onAnimationClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.animation_item, parent, false);

        if (viewType == 1) {
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (getItemViewType(position) == 1) {
            final String assetPath = assetPathName + "/" + titleAssetsPath.get(position);
            holder.tileAnimationView.setAnimation(assetPath);
            if (bgPath != null) {
                holder.bgAnimationView.setAnimation(bgPath);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAnimationClickListener.onAnimationCLick(position, assetPath);
                }
            });
        }
    }

    public interface OnAnimationClickListener {
        void onAnimationCLick(int position, String animationAssetPath);
    }

    @Override
    public int getItemCount() {
        return titleAssetsPath.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LottieAnimationView tileAnimationView, bgAnimationView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tileAnimationView = itemView.findViewById(R.id.tileAnimationView);
            bgAnimationView = itemView.findViewById(R.id.bgAnimationView);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}
