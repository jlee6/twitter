package com.exercise.twitter.timeline;

import java.util.List;

/**
 * MVP Contract for view and presenter
 */
public interface Contract {
    interface Presenter {
        void getTimeline();
    }

    interface View {
        void updateTimelineList(List<Timeline> timeline);
    }
}
