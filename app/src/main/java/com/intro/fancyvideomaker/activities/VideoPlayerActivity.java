package com.intro.fancyvideomaker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.intro.fancyvideomaker.R;

public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView;

    boolean fromVideoMaker = false;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = findViewById(R.id.videoView);

        fromVideoMaker = getIntent().getBooleanExtra("fromVideoMaker", false);
        String path = getIntent().getStringExtra("path");

        videoView.setVideoPath(path);


        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        loadBannerAd();
    }

    private void loadBannerAd() {
        adView = new AdView(this, getResources().getString(R.string.facebookBannerAd), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
    }


    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (fromVideoMaker) {
            Intent intent = new Intent(this, MyCreationActivity.class);
            intent.putExtra("fromVideoMaker", fromVideoMaker);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
