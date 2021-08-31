package com.intro.fancyvideomaker.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.intro.fancyvideomaker.R;

public class MainActivity extends AppCompatActivity {

    Context context;

    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(getColor(R.color.bg4));
        }

        loadBannerAd();
    }

    private void loadBannerAd() {
        adView = new AdView(this, getResources().getString(R.string.facebookBannerAd), AdSize.BANNER_HEIGHT_90);
        LinearLayout adContainer = findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
    }

    public void myCreation(View view) {
        Intent intent = new Intent(MainActivity.this, MyCreationActivity.class);
        startActivity(intent);
    }

    public void start(View view) {
        Intent intent = new Intent(MainActivity.this, BackgroundListActivity.class);
        startActivity(intent);
    }

    public void rateus(View view) {
        String sb2 = "http://play.google.com/store/apps/details?id=" +
                getPackageName();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sb2));
        startActivity(intent);
    }

    public void privacypolicy(View view) {
        // TODO: 12-08-2021 Change Privacy policy
        Intent intent5 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/introspectprivacypolicy"));
        startActivity(intent5);
    }

    public void shareapp(View view) {
        Intent intent1 = new Intent();
        intent1.setAction(Intent.ACTION_SEND);
        String sb = "Download IntroSpect - Intro,Outro Video Maker App \n https://play.google.com/store/apps/details?id=" +
                getPackageName();
        intent1.putExtra(Intent.EXTRA_TEXT, sb);
        intent1.setType("text/plain");
        startActivity(intent1);
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}