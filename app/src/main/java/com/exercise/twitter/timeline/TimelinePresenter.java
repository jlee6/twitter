package com.exercise.twitter.timeline;

import android.app.Activity;
import android.util.Log;

import com.exercise.twitter.api.TwitterService;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Timeline presenter implementation
 */
public class TimelinePresenter implements Contract.Presenter {
    private static final String TAG = TimelinePresenter.class.getSimpleName();

    private TwitterService service;
    private Contract.View view;

    public TimelinePresenter(TwitterService service, Contract.View view) {
        this.service = service;
        this.view = view;
    }

    @Override
    public void initialize(Activity activity) {
        service.initialize(activity);
    }

    @Override
    public void getTimeline() {
        service.getTimeline()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeline -> {
                    if (view == null) {
                        return;
                    }
                    view.updateTimelineList(timeline);
                }, throwable -> Log.e(TAG, throwable.toString()));
    }
}
