package com.intro.fancyvideomaker.utils.listdecorators.TileEffects;

import android.content.Context;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.intro.fancyvideomaker.utils.listdecorators.TileEffects.utils.Tile;

import androidx.annotation.Keep;
@Keep
public class Tile1 extends Tile {
    private static final String TAG = "Tile1";
    private LottieAnimationView animationView;
    private Context context;
    private LinearLayout linearLayout;

    public Tile1(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
        init();

    }

    public void init() {
        init("tile1.json");
        String str = "ANIMATED";
        String str2 = "**";
        addTextDelegateField(str, str, str2);
        String str3 = "TITLES";
        addTextDelegateField(str3, str3, str2);
        changePathColor(-16711936, str2);
    }
}
