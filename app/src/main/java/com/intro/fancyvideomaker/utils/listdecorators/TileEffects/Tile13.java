package com.intro.fancyvideomaker.utils.listdecorators.TileEffects;

import android.content.Context;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import androidx.core.internal.view.SupportMenu;

import com.airbnb.lottie.LottieAnimationView;
import com.intro.fancyvideomaker.utils.listdecorators.TileEffects.utils.Tile;

import androidx.annotation.Keep;
@Keep
public class Tile13 extends Tile {
    private LottieAnimationView animationView;
    private Context context;
    private LinearLayout linearLayout;

    public Tile13(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        super(context2, lottieAnimationView, linearLayout2);
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
        init();
    }

    public void init() {
        init("tile13.json");
        this.animationView.setScaleType(ScaleType.CENTER_CROP);
        addTextDelegateField("TRENDY");
        addTextDelegateField("TYPOGRAPHY");
        addTextDelegateField("PACK");
        String str = "**";
        changePathColor(SupportMenu.CATEGORY_MASK, "Shape Layer 1", str);
        changePathColor(SupportMenu.CATEGORY_MASK, "Shape Layer 2", str);
    }
}
