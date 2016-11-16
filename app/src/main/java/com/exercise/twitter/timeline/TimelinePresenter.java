package com.exercise.twitter.timeline;

import android.util.Log;

import com.exercise.twitter.api.TwitterService;

import java.util.List;

import rx.Subscriber;

/**
 * Timeline presenter implementation
 */
public class TimelinePresenter implements Contract.Presenter {
    private static final String TAG = TimelinePresenter.class.getSimpleName();
    TwitterService service;
    Contract.View view;

    public TimelinePresenter(TwitterService service, Contract.View view) {
        this.service = service;
    }

    @Override
    public void getTimeline() {
        service.getTimeline().subscribe(new Subscriber<List<Timeline>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onNext(final List<Timeline> timelines) {
                if (view == null) {
                    return;
                }

                view.updateTimelineList(timelines);
            }
        });
    }
}
