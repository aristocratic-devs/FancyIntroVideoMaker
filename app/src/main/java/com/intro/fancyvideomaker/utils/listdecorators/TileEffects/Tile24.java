package com.intro.fancyvideomaker.utils.listdecorators.TileEffects;

import android.content.Context;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.intro.fancyvideomaker.utils.listdecorators.TileEffects.utils.Tile;

import androidx.annotation.Keep;
@Keep
public class Tile24 extends Tile {
    private LottieAnimationView animationView;
    private Context context;
    private LinearLayout linearLayout;

    public Tile24(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
        init();
    }

    public void init() {
        init("tile1.json");
        addTextDelegateField("MOTION", Boolean.valueOf(true), new String[0]);
        String str = "Pre-comp";
        String str2 = "**";
        addTextDelegateField("ALPHA", str, "title7_text2", str2);
        addTextDelegateField("PRESENTS", str, "title7_text3", str2);
        changePathColor(-1, str2);
    }
}
