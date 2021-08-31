package com.intro.fancyvideomaker.utils.listdecorators.renderer;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.io.IOException;
import androidx.annotation.Keep;
@Keep
public class Renderer extends RenderEngine {

    private static final String TAG = "Renderer";

    public LottieAnimationView animationView, backgroundAnimationView;
    public Context context;
    public ConstraintLayout constraintLayout;
    String str = TAG;
    int videoFrames = 900;

    public Renderer(Context context, ConstraintLayout constraintLayout, LottieAnimationView lottieAnimationView, LottieAnimationView backgroundAnimationView, int videoFrames) {

        this.context = context;
        this.constraintLayout = constraintLayout;
        this.animationView = lottieAnimationView;
        this.videoFrames = videoFrames;
        this.backgroundAnimationView = backgroundAnimationView;

    }

//    public Renderer(Context context, ConstraintLayout constraintLayout, LottieAnimationView lottieAnimationView, int videoFrames) {
//
//        this.context = context;
//        this.constraintLayout = constraintLayout;
//        this.animationView = lottieAnimationView;
//        this.videoFrames=videoFrames;
//
//    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void init(final File file) {

        Log.i(str, "Generating movie...");

        setResolution(constraintLayout.getHeight(), constraintLayout.getWidth());
        Log.d(TAG, "line56 height = " + constraintLayout.getHeight());
        Log.d(TAG, "line56 width = " + constraintLayout.getWidth());

        try {
            generateMovie(file);

            Log.i(str, "Movie generation complete");

        } catch (Exception e) {


            float height = constraintLayout.getWidth() % 2;
            int heightnew = constraintLayout.getHeight();
            int widthnew = constraintLayout.getWidth();
            if (height != 0) {

                heightnew = heightnew - 1;


            } else {

                widthnew = widthnew - 1;
            }
            try {
                setFrameResolution(heightnew, widthnew);
                generateMovie(file);
            } catch (Exception e1) {
                showAlertDialog("Rendering error", constraintLayout.getHeight() + "= height " + constraintLayout.getWidth() + "= width");
            }
            Log.e(str, "Movie generation FAILED", e);

        }
    }

    public void showAlertDialog(String str, String str2) {
        Builder builder = new Builder(this.context);
        builder.setTitle(str);
        builder.setMessage(str2);
        builder.setPositiveButton("OK", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void generateMovie(final File file) {

//        setFrameResolution();
        setLayout(constraintLayout);

        try {
            prepareEncoder(file);

            final Handler handler = new Handler();
            handler.post(new Runnable() {

                int count = 0;

                public void run() {
                    drainEncoder(false);

                    animationView.setFrame((int) (this.count % animationView.getMaxFrame()));
                    if (backgroundAnimationView != null) {
                        backgroundAnimationView.setFrame((int) (this.count % animationView.getMaxFrame()));
                    }

                    generateFrame(renderer(constraintLayout));
                    int i = this.count;
                    this.count = i + 1;

                    //todo change @param videoFrames if you want to change length of generated
                    // video
                    if (((float) i) < videoFrames) {
                        handler.postDelayed(this, 0);
                        float calcFramePercentage = calcFramePercentage(this.count, videoFrames);
                        if (interfaceRenderEngine != null) {
                            interfaceRenderEngine.onProgressChange(calcFramePercentage);
                            return;
                        }
                        return;
                    }
                    drainEncoder(true);
                    releaseEncoder();
                    if (interfaceRenderEngine != null) {
                        interfaceRenderEngine.onRendered(file);
                    }
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "generateMovie: ", e);
        }
    }


    public void setFrameResolution(int heightnew, int widthnew) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) constraintLayout.getLayoutParams();
        layoutParams.width = widthnew;
        layoutParams.height = heightnew;
        constraintLayout.setLayoutParams(layoutParams);

    }
}
