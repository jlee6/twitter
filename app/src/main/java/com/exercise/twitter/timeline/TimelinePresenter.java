package com.exercise.twitter.timeline;

import android.util.Log;

import com.exercise.twitter.api.TwitterService;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Timeline presenter implementation
 */
public class TimelinePresenter implements Contract.Presenter {
    private static final String TAG = TimelinePresenter.class.getSimpleName();
    TwitterService service;
    Contract.View view;

    public TimelinePresenter(TwitterService service, Contract.View view) {
        this.service = service;
        this.view = view;
    }

    @Override
    public void getTimeline() {
        service.getTimeline()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Timeline>>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.wtf(TAG, "Exception: " + e.toString());
                               }

                               @Override
                               public void onNext(List<Timeline> timeline) {
                                   Log.d(TAG, "received timeline");
                                   if (view == null) {
                                       return;
                                   }
                                   view.updateTimelineList(timeline);
                               }
                           });
    }
}
