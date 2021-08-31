package com.intro.fancyvideomaker.utils.listdecorators.TileEffects;

import android.content.Context;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.intro.fancyvideomaker.utils.listdecorators.TileEffects.utils.Tile;

import androidx.annotation.Keep;
@Keep
public class Tile2 extends Tile {
    private LottieAnimationView animationView;
    private Context context;
    private LinearLayout linearLayout;

    public Tile2(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
        init();
    }

    public void init() {
        init("tile2.json");
        addTextDelegateField("SMALL TEXT");
        String str = "**";
        addTextDelegateField("BIG TEXT", "BIG TEXT ", str);
        changePathColor(-16711936, "Shape Layer 1", str);
    }
}
