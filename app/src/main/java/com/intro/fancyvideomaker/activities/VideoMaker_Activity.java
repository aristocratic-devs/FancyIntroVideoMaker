package com.intro.fancyvideomaker.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.intro.fancyvideomaker.R;
import com.intro.fancyvideomaker.utils.listdecorators.TileEffects.utils.Tile;
import com.intro.fancyvideomaker.utils.listdecorators.renderer.RenderEngine;
import com.intro.fancyvideomaker.utils.listdecorators.renderer.Renderer;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import yuku.ambilwarna.AmbilWarnaDialog;

public class VideoMaker_Activity extends AppCompatActivity {

    private static final String TAG = "VideoMakerTag";
    Context context;

    String backgroundAnimationPath;
    String tileAnimationPath;
    int rewardAdFailedCount = 0;

    LottieAnimationView tileAnimationView, backgroundAnimationView;
    ImageView backgroundImageView, textColorImageView, backgroundColorImageView;

    LinearLayout editTextContainer;
    ConstraintLayout previewVideoContainer, mainLayout;
    Tile tile;

    Dialog dialog;

    private final int CODE_IMAGE_GALLERY = 1;
    private final String SAMPLE_CROPPED_IMG_NAME = "SampleCropImg";

    boolean isRewardAdLoaded = false;
    boolean isRewardOptain = false;

    Dialog loadingDialog;

    private InterstitialAd interstitialAd;
    private String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_maker);
        context = this;

        tileAnimationView = findViewById(R.id.tileAnimationView);
        backgroundAnimationView = findViewById(R.id.backgroudnAnimation);
        backgroundImageView = findViewById(R.id.backgroundImageView);
        editTextContainer = findViewById(R.id.editTextContainer);
        previewVideoContainer = findViewById(R.id.previewVideoContainer);
        textColorImageView = findViewById(R.id.textColorImageView);
        mainLayout = findViewById(R.id.mainLayout);
        textColorImageView = findViewById(R.id.textColorImageView);
        backgroundColorImageView = findViewById(R.id.otherColorImageView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(getColor(R.color.bg4));
        }

        initLoadingDialog();
        loadInterAd();
        initView();

        tileAnimationView.setAnimation(tileAnimationPath);
        backgroundAnimationView.setAnimation(backgroundAnimationPath);
    }

    private void loadInterAd() {
        interstitialAd = new InterstitialAd(this, getResources().getString(R.string.facebookInterstialAd));

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");

                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("path", filePath);
                intent.putExtra("fromVideoMaker", true);
                startActivity(intent);
                finishAffinity();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    private void initLoadingDialog() {
        loadingDialog = new Dialog(context);
        loadingDialog.setContentView(R.layout.ad_loading_dialog);
        loadingDialog.setCancelable(false);
    }

    private void initView() {

        tile = new Tile(context, tileAnimationView, editTextContainer);
        Intent intent = getIntent();
        backgroundAnimationPath = intent.getStringExtra("bgPath");
        tileAnimationPath = intent.getStringExtra("tileAnimationPath");

        tileAnimationView.setAnimation(tileAnimationPath);
        backgroundAnimationView.setAnimation(backgroundAnimationPath);

        initLayout();

    }

    private void initLayout() {
        String className = tileAnimationPath.substring(tileAnimationPath.lastIndexOf("/") + 1);
        className = className.replace(".json", "");
        className = className.substring(0, 1).toUpperCase() + className.substring(1);

        tile.removeAllEditTexts();

        try {
            Class.forName("com.intro.fancyvideomaker.utils.listdecorators.TileEffects." + className)
                    .getConstructor(Context.class,
                            LottieAnimationView.class,
                            LinearLayout.class).newInstance(context, tileAnimationView,
                    editTextContainer);

        } catch (Exception e) {
            Log.e("TAG", "onEffectSelected: ", e);
            Toast.makeText(this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void saveAsVideo(View view) {
        saveVideo();
    }

    private boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = null;
            if (cm != null) {
                netInfo = cm.getActiveNetworkInfo();
            }
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    void saveVideo() {
//        if (Helper.check_permission(context)) {
//            Helper.request_permission(context, 101);
//            Toast.makeText(context, "You need to provide storage permission in order to use this app", Toast.LENGTH_SHORT).show();
//        } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mainLayout.setBackgroundColor(getColor(R.color.bg5));
        }

        int frames = (int) tileAnimationView.getMaxFrame();

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.loading_dialog);
//            loadingadView = dialog.findViewById(R.id.loadingadView);
        dialog.setCancelable(false);

        final ProgressBar progressBar = dialog.findViewById(R.id.renderProgress);
        progressBar.setMax(100);
        progressBar.setIndeterminate(false);
        final Renderer renderer = new Renderer(context, previewVideoContainer, tileAnimationView, backgroundAnimationView
                , frames);
        renderer.setInterfaceRenderEngine(new RenderEngine.InterfaceRenderEngine() {

            @Override
            public void onProgressChange(float f) {
                int progress = (int) f;
                progressBar.setProgress(progress);
                Log.i("POIU", "onProgressChange: " + progress);
            }

            @Override
            public void onRendered(final File file) {

                MediaScannerConnection.scanFile(VideoMaker_Activity.this, new String[]{file.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

                Log.i("POIU", "onRendered: video generated " + file.getAbsolutePath());

                Toast.makeText(context, "Save complete", Toast.LENGTH_SHORT).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mainLayout.setBackgroundColor(getColor(R.color.bg4));
                }
                dialog.dismiss();

                // TODO: 18-05-2020 open video player activity here


                filePath = file.getAbsolutePath();

                if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                    return;
                }

                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("path", file.getAbsolutePath());
                intent.putExtra("fromVideoMaker", true);
                startActivity(intent);
                finishAffinity();
            }
        });

        dialog.show();

//        File filepath = Environment.getExternalStorageDirectory();
//            File dir = new File(filepath.getAbsolutePath() + "/" + "Introspect - Intro,Outro Video Maker");
        File dir = new File(getExternalMediaDirs()[0] + "/" + "Introspect - Intro,Outro Video Maker");
        dir.mkdirs();

        String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String FileName = getResources().getString(R.string.app_name) + "_" + ts + ".mp4";
        File file = new File(dir, FileName);
        file.renameTo(file);

        renderer.init(file);
//        }

    }

    int textColor = 0;
    int backgroundColor = 0;

    public void changeTextColor(View view) {
        AmbilWarnaDialog backgroundColorDialog = new AmbilWarnaDialog(
                context, textColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                textColor = color;
                textColorImageView.setBackgroundColor(textColor);

                tileAnimationView.addValueCallback(
                        new KeyPath("**"),
                        LottieProperty.COLOR,
                        new SimpleLottieValueCallback<Integer>() {
                            @Override
                            public Integer getValue(LottieFrameInfo<Integer> frameInfo) {
                                return textColor;
                            }
                        }
                );
            }
        }
        );
        backgroundColorDialog.show();
    }

    public void changeFrameColor(View view) {

        AmbilWarnaDialog textColorDialog = new AmbilWarnaDialog(context, backgroundColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {

                        backgroundColor = color;

                        backgroundColorImageView.setBackgroundColor(backgroundColor);

                        tileAnimationView.addValueCallback(
                                new KeyPath("**"),

                                LottieProperty.COLOR_FILTER,
                                new SimpleLottieValueCallback<ColorFilter>() {
                                    @Override
                                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                                        return new PorterDuffColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP);
                                    }
                                }
                        );
                    }
                });
        textColorDialog.show();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void changeBackgroundAsImage(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_bg_chooser);
        dialog.setCancelable(true);

        AppCompatButton btnAnimatedVideo = dialog.findViewById(R.id.btnAnimatedVideo);
        AppCompatButton btnImage = dialog.findViewById(R.id.btnImage);

        btnAnimatedVideo.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(VideoMaker_Activity.this, BackgroundListActivity.class).putExtra("fromVideoMaker", true);
            backgroundActivityResultLauncher.launch(intent);
        });

        btnImage.setOnClickListener(v -> {
            dialog.dismiss();
            startActivityForResult(new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/*"), CODE_IMAGE_GALLERY);
        });

        dialog.show();
        dialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.95), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                startCroping(imageUri);
            }

        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri imageUriResultCrop = UCrop.getOutput(data);

            if (imageUriResultCrop != null) {
                backgroundImageView.setImageURI(imageUriResultCrop);
            }

        }
    }

    private void startCroping(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMG_NAME;
        destinationFileName += ".jpg";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));

        uCrop.withAspectRatio(16, 9);

        uCrop.withOptions(getCropOptions());

        uCrop.start(VideoMaker_Activity.this);
    }

    private UCrop.Options getCropOptions() {
        UCrop.Options options = new UCrop.Options();

        options.setCompressionQuality(90);

//        UI
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);

//        Colors
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        options.setToolbarColor(getResources().getColor(R.color.bg4));

        options.setToolbarTitle("CROP IMAGE");

        return options;
    }

    ActivityResultLauncher<Intent> backgroundActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            backgroundAnimationPath = intent.getStringExtra("bgPath");
                            backgroundAnimationView.setAnimation(backgroundAnimationPath);
//                            backgroundAnimationView.startAnimation();
                        }
                        Log.i(TAG, "onActivityResult: " + backgroundAnimationPath);
                        // TODO: 13-08-2021 do some operation
                    }
                }
            }
    );

    @Override
    protected void onResume() {
        super.onResume();
    }
}
