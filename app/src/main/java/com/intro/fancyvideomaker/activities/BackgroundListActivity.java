package com.intro.fancyvideomaker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.intro.fancyvideomaker.R;
import com.intro.fancyvideomaker.adapters.TitleListAdapter;
import com.intro.fancyvideomaker.utils.listdecorators.Helper;

import java.util.ArrayList;

public class BackgroundListActivity extends AppCompatActivity implements TitleListAdapter.OnAnimationClickListener {

    RecyclerView bgRecyclerView;
    TitleListAdapter bgListAdapter;
    Context context;
    ArrayList<String> bgAssetsPath;

    boolean fromVideoMaker;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_list);
        context = this;

        fromVideoMaker = getIntent().getBooleanExtra("fromVideoMaker", false);

        bgRecyclerView = findViewById(R.id.titleRecyclerView);
        ImageView ivback = findViewById(R.id.ivback);
        ImageView ivoption = findViewById(R.id.ivoption);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(getColor(R.color.bg4));
        }

        if (ivback.getVisibility() == View.VISIBLE) {
            ivback.setVisibility(View.GONE);
            ivoption.setVisibility(View.GONE);
        }

        loadBannerAd();
        init();
    }

    private void loadBannerAd() {
        adView = new AdView(this, getResources().getString(R.string.facebookBannerAd), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
    }

    private void init() {

        bgAssetsPath = new ArrayList<>();
        bgAssetsPath.addAll(Helper.getAssetData(context, "background"));

        bgListAdapter = new TitleListAdapter(context, bgAssetsPath, "background", this);
        bgRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        bgRecyclerView.setAdapter(bgListAdapter);
    }

    @Override
    public void onAnimationCLick(int position, final String animationAssetPath) {

        if (fromVideoMaker) {
            Intent intent = new Intent();
            intent.putExtra("bgPath", animationAssetPath);
            setResult(RESULT_OK, intent);
            finish();
            return;
        }

        final Intent intent = new Intent(context, TitleListActivity.class);
        intent.putExtra("bgPath", animationAssetPath);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
