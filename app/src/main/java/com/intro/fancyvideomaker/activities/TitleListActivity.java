package com.intro.fancyvideomaker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.intro.fancyvideomaker.R;
import com.intro.fancyvideomaker.adapters.TitleListAdapter;
import com.intro.fancyvideomaker.utils.listdecorators.Helper;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TitleListActivity extends AppCompatActivity implements TitleListAdapter.OnAnimationClickListener {

    RecyclerView titleRecyclerView;
    TitleListAdapter titleListAdapter;
    Context context;
    ArrayList<String> titleAssetsPath;
    String backgroundPath;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_list);
        context = this;

        if (SplashActivity.interstitialAd != null && SplashActivity.interstitialAd.isAdLoaded()) {
            SplashActivity.interstitialAd.show();
        }

        titleRecyclerView = findViewById(R.id.titleRecyclerView);

        ImageView ivback = findViewById(R.id.ivback);
        ImageView ivoption = findViewById(R.id.ivoption);

        if (ivoption.getVisibility() == View.VISIBLE) {
            ivoption.setVisibility(View.GONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(getColor(R.color.bg4));
        }

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loadBannerAd();
        initView();
    }

    private void initView() {
        backgroundPath = getIntent().getStringExtra("bgPath");
        titleAssetsPath = new ArrayList<>();
        titleAssetsPath.addAll(Helper.getAssetData(context, "tile"));

        titleListAdapter = new TitleListAdapter(context, titleAssetsPath, "tile", this, backgroundPath);
        titleRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        titleRecyclerView.setAdapter(titleListAdapter);
    }


    @Override
    public void onAnimationCLick(int position, String animationAssetPath) {
        final Intent intent = new Intent(context, VideoMaker_Activity.class);
        intent.putExtra("bgPath", backgroundPath);
        intent.putExtra("tileAnimationPath", animationAssetPath);
        startActivity(intent);
    }

    private void loadBannerAd() {
        adView = new AdView(this, getResources().getString(R.string.facebook_banner_ad), AdSize.BANNER_HEIGHT_50);
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
}
