package com.intro.fancyvideomaker.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.VideoBitmapDecoder;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.ads.AdView;
import com.intro.fancyvideomaker.R;
import com.intro.fancyvideomaker.activities.VideoPlayerActivity;

import java.io.File;
import java.util.HashMap;

public class MyCreationAdapter extends RecyclerView.Adapter<MyCreationAdapter.ViewHolder> {
    Context context;
    File[] allFiles;

    public MyCreationAdapter(Context context, File[] allFiles) {
        this.context = context;
        this.allFiles = allFiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (getItemViewType(position) == 1) {
            try {
                if (
                        allFiles[position].exists()) {

                    Glide.with(context).load(allFiles[position]).transform(new CenterCrop()).into(holder.image_thumbnail);
                }
            } catch (Exception e) {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, VideoPlayerActivity.class);
                    intent.putExtra("path", allFiles[position].getAbsolutePath());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return allFiles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_thumbnail = itemView.findViewById(R.id.image_thumbnail);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}