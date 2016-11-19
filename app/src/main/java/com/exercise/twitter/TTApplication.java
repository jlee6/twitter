package com.exercise.twitter;

import android.app.Application;

import org.fuckboilerplate.rx_social_connect.RxSocialConnect;

import io.victoralbertos.jolyglot.GsonSpeaker;

public class TTApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RxSocialConnect.register(this, "TTApplication").using(new GsonSpeaker());
    }
}
