package com.exercise.twitter.api;

import android.app.Activity;

import com.exercise.twitter.R;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.fuckboilerplate.rx_social_connect.RxSocialConnect;

import rx.Observable;

public class TwitterAuthentication {
    private OAuth1AccessToken accessToken;
    private OAuth10aService twitterService;

    public TwitterAuthentication() {
    }

    public OAuth1AccessToken getToken() {
        return accessToken;
    }

    public OAuth10aService getService() {
        return twitterService;
    }

    Observable<OAuth1AccessToken> authenticate(Activity activity) {
        twitterService = new ServiceBuilder()
                .apiKey(activity.getString(R.string.consumer_key))
                .apiSecret(activity.getString(R.string.consumer_secret))
                .callback("")
                .build(TwitterApi.instance());

        return RxSocialConnect.with(activity, twitterService)
                .map(response -> accessToken = response.token());
    }
}
