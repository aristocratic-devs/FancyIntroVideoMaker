package com.intro.fancyvideomaker.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.intro.fancyvideomaker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    Context context;
    private final String TAG = MainActivity.class.getSimpleName();

//    private LinearLayout adView;
//    private NativeBannerAd nativeBannerAd;

    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(getColor(R.color.bg4));
        }

        if (SplashActivity.interstitialAd != null && SplashActivity.interstitialAd.isAdLoaded()) {
            SplashActivity.interstitialAd.show();
        }

        NativeAdLayout nativeAdLayout = findViewById(R.id.native_ad_container);

        loadNativeAd(nativeAdLayout);

        //        Bottom sheet
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.exit_layout);
        AppCompatButton btnExit = bottomSheetDialog.findViewById(R.id.btnExit);
//        NativeAdLayout exitBanner = bottomSheetDialog.findViewById(R.id.adsviewBannerExit);

//        if (exitBanner != null) {
//            loadNativeAd(exitBanner);
//        }

        btnExit.setOnClickListener(v -> finishAffinity());
    }


    private void loadNativeAd(NativeAdLayout nativeAdLayout) {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        final NativeAd nativeAd = new NativeAd(this, getString(R.string.facebook_native_ad));
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd, nativeAdLayout);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    private void inflateAd(NativeAd nativeAd, NativeAdLayout nativeAdLayout) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        ConstraintLayout adView = (ConstraintLayout) inflater.inflate(R.layout.facebook_ad_container_200,
                nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(MainActivity.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_advertiser_name);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView, nativeAdMedia, nativeAdIcon, clickableViews);
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
        Intent intent5 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/fancy-intro-video-maker"));
        startActivity(intent5);
    }

    public void shareapp(View view) {
        Intent intent1 = new Intent();
        intent1.setAction(Intent.ACTION_SEND);
        String sb = "Download Fancy Intro Video Maker \n https://play.google.com/store/apps/details?id=" +
                getPackageName();
        intent1.putExtra(Intent.EXTRA_TEXT, sb);
        intent1.setType("text/plain");
        startActivity(intent1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        final int min = 1;
        final int max = 4;

        final int random = new Random().nextInt((max - min) + 1) + min;

        if (random == 2) {
            showRateUsDialog();
            return;
        }
        bottomSheetDialog.show();
    }

    private void showRateUsDialog() {
        Rect displayRectangle = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.rate_dia);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout((int) (displayRectangle.width() * 0.8), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        ImageView imageView = dialog.findViewById(R.id.iv_rate_gif);

        Glide.with(this).load(R.drawable.rate_gif_new).into(imageView);

        TextView tv_exit = dialog.findViewById(R.id.tv_exit);
        TextView tv_rate = dialog.findViewById(R.id.tv_rate_us);

        tv_rate.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
        });
        tv_exit.setOnClickListener(v -> {
            dialog.dismiss();
            finishAffinity();
        });
    }

}