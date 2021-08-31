package com.intro.fancyvideomaker.utils.listdecorators.TileEffects;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.intro.fancyvideomaker.utils.listdecorators.TileEffects.utils.Tile;

import androidx.annotation.Keep;
@Keep
public class Tile9 extends Tile {
    private LottieAnimationView animationView;
    private Context context;
    private LinearLayout linearLayout;

    public Tile9(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
        init();
    }

    public void init() {
        init("tile9.json");
        this.animationView.setScaleType(ScaleType.CENTER_CROP);
        addTextDelegateField("YOUR TITLE");
        addTextDelegateField("YOUR SUBTITLE");
        String str = "#551A8B";
        String str2 = "**";
        changePathColor(Color.parseColor(str), "Shape Layer 1", str2);
        changePathColor(Color.parseColor(str), "Shape Layer 2", str2);
    }
}
