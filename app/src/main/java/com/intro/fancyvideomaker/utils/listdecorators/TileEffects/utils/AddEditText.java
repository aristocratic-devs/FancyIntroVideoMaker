package com.intro.fancyvideomaker.utils.listdecorators.TileEffects.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputFilter.AllCaps;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.model.KeyPath;
import com.intro.fancyvideomaker.R;

import androidx.annotation.Keep;
@Keep
public class AddEditText implements OnClickListener, OnCheckedChangeListener {
    private static final String TAG = "AddEditText";
    public LottieAnimationView animationView;
    private Context context;

    private EditText f362et;
    public KeyPath keyPath;
    private LinearLayout linearLayout;
    public String placeholder;
    private int rotationAngle = 180;

    public AddEditText(Context context2, LinearLayout linearLayout2, String str, LottieAnimationView lottieAnimationView) {
        this.context = context2;
        this.linearLayout = linearLayout2;
        this.placeholder = str;
        this.animationView = lottieAnimationView;
    }

    public void setKeyPath(String[] strArr) {
        this.keyPath = new KeyPath(strArr);
    }

    public EditText addText() {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AppCompatActivity appCompatActivity = (AppCompatActivity) this.context;
        if (layoutInflater != null) {
            View inflate = layoutInflater.inflate(R.layout.card_text_controler, null);
            this.f362et =  inflate.findViewById(R.id.card_text_control_text);      init();
            this.f362et.setSingleLine();
            this.f362et.setHint(this.placeholder);
            this.f362et.setFilters(new InputFilter[]{new AllCaps()});
            this.f362et.setImeOptions(6);
            this.linearLayout.addView(inflate);
            return this.f362et;
        } else return null;
    }

    private void init() {
        toggleExpandIcon();
    }

    private void toggleExpandIcon() {
        this.rotationAngle = this.rotationAngle == 0 ? 180 : 0;

    }

    public void onClick(View view) {

    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {

    }
}
