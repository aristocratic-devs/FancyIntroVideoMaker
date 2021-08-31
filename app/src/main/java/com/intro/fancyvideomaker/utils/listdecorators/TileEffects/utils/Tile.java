package com.intro.fancyvideomaker.utils.listdecorators.TileEffects.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.FontAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.TextDelegate;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.ScaleXY;
import com.intro.fancyvideomaker.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Keep
public class Tile extends TileUtils implements OnSeekBarChangeListener, OnClickListener {

    public String TAG = "from_created";
    private float animHeight = 2500.0f;
    private float animWidth = 1282.5f;
    public LottieAnimationView animationView;
    private LinearLayout bottomSheetContainer;
    private LinearLayout bottomSheetTypeface;
    //    public ColorPickerView colorPickerView;
//    private List<AccentColor> colors;
    public Context context;
    private List<EditText> editTexts = new ArrayList();
    private FrameLayout frameLayout;
    public Boolean isCalled = Boolean.valueOf(false);
    private LinearLayout linearLayout;
    private int raw;
    private RecyclerView rvTypeface;
    private SeekBar scaleSeekBar;
    //    private TapTargetSequence tapTargetSequence;
//    private Template template;
    public TextDelegate textDelegate;
    //    public TextEffectInterface textEffectInterface;
    private LinearLayout typefaceLayout;
    private List<String> typefaces;
    private Float xFrame;
    private SeekBar xPositionSeekBar;
    private Float yFrame;
    private SeekBar yPositionSeekBar;

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public void selectSpecificLayer(String... strArr) {
    }

    public Tile(Context context2, LottieAnimationView lottieAnimationView, LinearLayout linearLayout2) {
        this.context = context2;
        this.animationView = lottieAnimationView;
        this.linearLayout = linearLayout2;
    }

//    public void setTextEffectInterface(TextEffectInterface textEffectInterface2) {
//        this.isCalled = Boolean.valueOf(true);
//        this.textEffectInterface = textEffectInterface2;
//    }

//    public void init(int i, String str) {
//        changeFont(str);
//        init(i);
//    }

    public void init(String path) {
//        this.raw = i;
        this.animationView.setAnimation("tile/" + path);
        this.scaleSeekBar = ((AppCompatActivity) this.context).findViewById(R.id.scaleSeekBar);
        this.xPositionSeekBar = ((AppCompatActivity) this.context).findViewById(R.id.positionXSeekBar);
        this.yPositionSeekBar = ((AppCompatActivity) this.context).findViewById(R.id.positionYSeekBar);
        getAnimationSize(path);
        logAllKeyPaths();
        this.animationView.playAnimation();
        this.textDelegate = new TextDelegate(this.animationView);

        this.scaleSeekBar.setOnSeekBarChangeListener(this);
        this.xPositionSeekBar.setOnSeekBarChangeListener(this);
        this.yPositionSeekBar.setOnSeekBarChangeListener(this);
    }

    public KeyPath findKeyPath(String... strArr) {
        return new KeyPath(strArr);
    }

    public void addTextDelegateField(String str, Boolean bool, String... strArr) {
        AddEditText addEditText = new AddEditText(this.context, this.linearLayout, str, this.animationView);
        addEditText.setKeyPath(strArr);
        setEditText(addEditText, str);
    }

    public void addTextDelegateField(String str, String... strArr) {
        AddEditText addEditText = new AddEditText(this.context, this.linearLayout, str, this.animationView);
        addEditText.setKeyPath(strArr);
        setEditText(addEditText, str);
    }

    public void addTextDelegateField(String str) {
        setEditText(new AddEditText(this.context, this.linearLayout, str, this.animationView), str);
    }

    private void setEditText(AddEditText addEditText, final String str) {
        _saveEditText(this.context, str);
        final EditText addText = addEditText.addText();
        this.animationView.setTextDelegate(this.textDelegate);
        addText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                Tile.this.textDelegate.setText(str, String.valueOf(addText.getText()));
                Tile.this.animationView.setTextDelegate(Tile.this.textDelegate);
            }
        });
        this.editTexts.add(addText);
    }

    private void getWarnings(LottieComposition lottieComposition) {
        ArrayList<String> warnings = lottieComposition.getWarnings();
        Log.i(this.TAG, "getWarnings: loaded");
        for (String str : warnings) {
            String str2 = this.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("getWarnings: ");
            sb.append(str);
            Log.i(str2, sb.toString());
        }
    }

    public void changePathColor(int i, String... strArr) {
//        _saveEffectElementColor(this.context, strArr);
//        this.animationView.addValueCallback(new KeyPath(strArr), LottieProperty.COLOR_FILTER, new LottieValueCallback<>(new SimpleColorFilter(i)));
//        this.colors.add(new AccentColor(i, strArr));
//        setAccentColors(this.colors);
    }

    public void changePathColorLocal(int i, String... strArr) {
//        this.animationView.addValueCallback(new KeyPath(strArr), LottieProperty.COLOR_FILTER, new LottieValueCallback<>(new SimpleColorFilter(i)));
//        setAccentColors(this.colors);
    }

    public void logAllKeyPaths() {
        this.animationView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() {
            public void onCompositionLoaded(LottieComposition lottieComposition) {
//                for (KeyPath keyPath : Tile.this.animationView.resolveKeyPath(new KeyPath("**"))) {
//                    String access$500 = Tile.this.TAG;
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("logAllKeyPaths: ");
//                    sb.append(keyPath);
//                    Log.i(access$500, sb.toString());
//                }
            }
        });
    }

//    public void setAccentColors(List<AccentColor> list) {
////        RecyclerView recyclerView = ((AppCompatActivity) this.context).findViewById(R.id.rvAccentColors);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this.context, 0, false));
//////        AdapterColors adapterColors = new AdapterColors(list);
////        recyclerView.setAdapter(adapterColors);
////        adapterColors.setColorAccentInterface(new ColorAccentInterface() {
////            public void colorPicked(final AccentColor accentColor, final MaterialCardView materialCardView) {
////                Tile.this.bottomSheetBehavior.setState(3);
////                Tile.this.colorPickerView.setColor(accentColor.getColor());
////                Tile.this.colorPickerView.setOnColorChangedListener(new OnColorChangedListener() {
////                    public void onColorChanged(int i) {
////                        accentColor.setColor(i);
////                        Tile.this.changePathColorLocal(i, accentColor.getPathname());
////                        ViewCompat.setBackgroundTintList(materialCardView, ColorStateList.valueOf(i));
////                    }
////                });
////            }
////        });
//    }

    private void setTypeface(int i) {
//        this.typefaces = new ArrayList();
//        try {
//            this.typefaces.addAll(Arrays.asList(this.context.getAssets().list("fonts")));
//            AdapterTypefaces adapterTypefaces = new AdapterTypefaces(this.typefaces);
//            this.rvTypeface.setLayoutManager(new LinearLayoutManager(this.context));
//            this.rvTypeface.setAdapter(adapterTypefaces);
//            this.bsbTypeface.setState(3);
//            adapterTypefaces.setAdapterTypefacesInter(new AdapterTypefacesInter() {
//                public void onClicked(String str) {
//                    String access$500 = Tile.this.TAG;
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("fetchFont: ");
//                    sb.append(str);
//                    Log.i(access$500, sb.toString());
//                    Tile.this.animationView.setAnimation((int) R.raw.tile24);
//                    Tile.this.animationView.setAnimation((int) R.raw.tile1);
//                    Tile.this.changeFont(str);
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.i(this.TAG, "getFonts: error");
//        }
    }

    public boolean deleteDir(File file) {
        if (file != null && file.isDirectory()) {
            String[] list = file.list();
            for (String file2 : list) {
                if (!deleteDir(new File(file, file2))) {
                    return false;
                }
            }
            return file.delete();
        } else if (file == null || !file.isFile()) {
            return false;
        } else {
            return file.delete();
        }
    }

    private void setScale(float f) {
        try {
            this.animationView.addValueCallback(this.animationView.resolveKeyPath(new KeyPath("**")).get(0), LottieProperty.TRANSFORM_SCALE, new LottieValueCallback<>(new ScaleXY(f, f)));
        } catch (IndexOutOfBoundsException unused) {
            Toast.makeText(this.context, "Something went wrong while setting scale", Toast.LENGTH_LONG).show();
        }
    }

    private void setPositionX(float f) {
        List resolveKeyPath = this.animationView.resolveKeyPath(new KeyPath("**"));
        this.xFrame = Float.valueOf(f);
        Float f2 = this.yFrame;
        try {
            this.animationView.addValueCallback((KeyPath) resolveKeyPath.get(0), LottieProperty.TRANSFORM_POSITION, new LottieValueCallback<>(new PointF(f, f2 != null ? f2.floatValue() : this.animHeight)));
        } catch (IndexOutOfBoundsException unused) {
            Toast.makeText(this.context, "Something went wrong while setting Y axis", Toast.LENGTH_LONG).show();
        }
    }

    private void setPositionY(float f) {
        List resolveKeyPath = this.animationView.resolveKeyPath(new KeyPath("**"));
        this.yFrame = f;
        Float f2 = this.xFrame;
        try {
            this.animationView.addValueCallback((KeyPath) resolveKeyPath.get(0), LottieProperty.TRANSFORM_POSITION, new LottieValueCallback<>(new PointF(f2 != null ? f2.floatValue() : this.animWidth, f)));
        } catch (IndexOutOfBoundsException unused) {
            Toast.makeText(this.context, "Something went wrong while setting X axis", Toast.LENGTH_LONG).show();
        }
    }

    public void setEditTexts(List<EditText> list) {
        this.editTexts = list;
    }

    public List<EditText> getEditTexts() {
        return this.editTexts;
    }

    public LottieComposition getComposition() {
        return this.animationView.getComposition();
    }

    private void getAnimationSize(String assetPath) {
        StringWriter stringWriter = new StringWriter();
        try {
            InputStream openRawResource = this.context.getAssets().open("tile/" + assetPath);
            char[] cArr = new char[1024];
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openRawResource));
            while (true) {
                int read = bufferedReader.read(cArr);
                if (read != -1) {
                    stringWriter.write(cArr, 0, read);
                } else {

                    break;
                }
            }
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        try {
            JSONObject jSONObject = new JSONObject(stringWriter.toString());
            String string = jSONObject.getString("w");
            this.animHeight = (float) Integer.parseInt(jSONObject.getString("h"));
            this.animWidth = (float) Integer.parseInt(string);
        } catch (JSONException e5) {
            e5.printStackTrace();
        }
    }

    public void changeFont(final String str) {
        this.animationView.setFontAssetDelegate(new FontAssetDelegate() {
            public Typeface fetchFont(String str) {
                Log.i(Tile.this.TAG, "fetchFont: inn");
                AssetManager assets = Tile.this.context.getAssets();

                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("fonts/");
                    sb.append(str);
                    return Typeface.createFromAsset(assets, sb.toString());
                } catch (Exception e) {

                    StringBuilder sb = new StringBuilder();
                    sb.append("fonts/Montserrat.ttf");
                    return Typeface.createFromAsset(assets, sb.toString());
                }
            }

            public String getFontPath(String str) {
                Toast.makeText(Tile.this.context, str, Toast.LENGTH_LONG).show();
                StringBuilder sb = new StringBuilder();
                sb.append("fonts/");
                sb.append(str);
                return super.getFontPath(sb.toString());
            }
        });
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        switch (seekBar.getId()) {
            case R.id.positionXSeekBar /*2131362112*/:
                setPositionX((float) i);
                return;
            case R.id.positionYSeekBar /*2131362113*/:
                setPositionY((float) i);
                return;
            case R.id.scaleSeekBar /*2131362145*/:
                setScale(((float) i) / 100.0f);
                return;
            default:
                return;
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.positionXSeekBar /*2131362112*/:
                seekBar.setMax(((int) this.animWidth) * 2);
                return;
            case R.id.positionYSeekBar /*2131362113*/:
                seekBar.setMax(((int) this.animHeight) * 2);
                return;
            case R.id.scaleSeekBar /*2131362145*/:
                seekBar.setMax(200);
                seekBar.setProgress(100);
                return;
            default:
                return;
        }
    }

    public void onClick(View view) {
//        if (view.getId() == R.id.m_typeface) {
//            setTypeface(this.raw);
//        }
    }

    public void removeAllEditTexts() {
        linearLayout.removeAllViews();
        linearLayout.invalidate();
    }
}
