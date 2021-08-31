package com.intro.fancyvideomaker.utils.listdecorators.TileEffects.utils;

import android.content.Context;
import android.util.Log;
import androidx.annotation.Keep;
@Keep
public class TileUtils {
    private static final String TAG = "TileUtils";

    public void _saveEditText(Context context, String str) {
//        Template template = ((MainActivity) context).global.getTemplate();
//        MetaEditText metaEditText = new MetaEditText();
//        metaEditText.setDelegateText(str);
//        List metaEditTexts = template.getMetaEditTexts();
//        metaEditTexts.add(metaEditText);
        StringBuilder sb = new StringBuilder();
        sb.append("_saveIntense:  ");
        sb.append(str);
        sb.append(" size: ");
//        sb.append(metaEditTexts.size());
        Log.i(TAG, sb.toString());
    }

    public void _saveEffectElementColor(Context context, String... strArr) {
//        ((MainActivity) context).global.getTemplate();
//        new EffectElementColor().setPathNames(strArr);
    }
}
