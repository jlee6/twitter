package com.exercise.twitter.api;

import android.util.Log;

import com.exercise.twitter.timeline.Timeline;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.SocketFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.schedulers.Schedulers;

public class TwitterService {
    private static final String BASE_URL = "http://api.twitter.com/1.1/";

    private OkHttpClient client;
    private String handle = "findmyhandle";

    public TwitterService() {
        client = new OkHttpClient().newBuilder()
                .socketFactory(SocketFactory.getDefault())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Log.d(TwitterService.class.getSimpleName(),
                                chain.request().toString());
                        return chain.proceed(chain.request());
                    }
                }).build();
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Observable<List<Timeline>> getTimeline() {
        Map<String, String> options = new HashMap<>();
        options.put("screen_name", handle);
        options.put("count", "10");

        return getApi().getTimeline(options).subscribeOn(Schedulers.io());
    }

    private TwitterApi getApi() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client).baseUrl(BASE_URL).build().create(TwitterApi.class);
    }

    interface TwitterApi {
        @GET("statuses/user_timeline.json")
        Observable<List<Timeline>> getTimeline(@QueryMap Map<String, String> options);
    }
}
