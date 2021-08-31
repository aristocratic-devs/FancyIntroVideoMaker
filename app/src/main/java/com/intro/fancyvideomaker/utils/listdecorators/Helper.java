package com.intro.fancyvideomaker.utils.listdecorators;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Helper {

    private static String TAG = "HELPER";

    public static boolean check_permission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED;
    }

    public static boolean request_permission(Context context, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, requestCode);
        return true;
    }

    public static ArrayList<String> getAssetData(Context context, String path) {
        ArrayList<String> assetPaths = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        try {
            String[] files = assetManager.list(path);

            if (files != null) {
                assetPaths.addAll(Arrays.asList(files));
            }
        } catch (IOException e1) {
            Log.e(TAG, "getAssetData: ", e1);
        }
        return assetPaths;
    }

    public static int height, width;

    public static void getheightandwidth(Context context) {
        getHeight(context);
        getwidth(context);
    }

    public static int getwidth(Context context) {
        width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    public static int getHeight(Context context) {
        height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    public static int setHeight(int h) {

        return (height * h) / 1920;

    }

    public static int setWidth(int w) {
        return (width * w) / 1080;

    }

    public static void setSize(View view, int width, int height) {

        view.getLayoutParams().height = setHeight(height);
        view.getLayoutParams().width = setWidth(width);

    }

    public static void setSize(View view, int width, int height, boolean sameheightandwidth) {

        if (sameheightandwidth) {
            view.getLayoutParams().height = setWidth(height);
            view.getLayoutParams().width = setWidth(width);
        } else {
            view.getLayoutParams().height = setHeight(height);
            view.getLayoutParams().width = setHeight(width);
        }

    }

    public static void setMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.setMargins(setWidth(left), setWidth(top), setWidth(right), setWidth(bottom));
    }
}
