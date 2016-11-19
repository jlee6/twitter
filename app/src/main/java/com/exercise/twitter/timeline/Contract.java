package com.exercise.twitter.timeline;

import android.app.Activity;

import java.util.List;

/**
 * MVP Contract for view and presenter
 */
public interface Contract {
    interface Presenter {
        void initialize(Activity activity);
        void getTimeline();
    }

    interface View {
        void updateTimelineList(List<Timeline> timeline);
    }
}
