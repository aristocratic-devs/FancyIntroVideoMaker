package com.intro.fancyvideomaker;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;


public class MyAcpplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AudienceNetworkAds.initialize(this);
    }
}
