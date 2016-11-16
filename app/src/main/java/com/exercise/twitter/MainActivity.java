package com.exercise.twitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.exercise.twitter.api.MockService;
import com.exercise.twitter.api.TwitterService;
import com.exercise.twitter.timeline.Contract;
import com.exercise.twitter.timeline.TimelinePresenter;
import com.exercise.twitter.timeline.TimelineRVAdapter;

public class MainActivity extends AppCompatActivity {
    private final String tag = MainActivity.class.getSimpleName();

    private TwitterService service = new MockService();
    private HandleDialog dialog;

    private Contract.Presenter presenter;

    // it maybe better to place it as a shared preference value
    private String activeHandle = "handle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TimelineRVAdapter adapter  = new TimelineRVAdapter();

        RecyclerView viewTimeline = (RecyclerView) findViewById(R.id.rv_timeline_list);
        viewTimeline.setAdapter(adapter);
        viewTimeline.setLayoutManager(new LinearLayoutManager(this));

        presenter = new TimelinePresenter(service, adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (TextUtils.isEmpty(activeHandle)) {
            return;
        }

        presenter.getTimeline();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.action_handle);
        if (item != null) {
            item.setTitle(activeHandle);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_handle:
                dialog = new HandleDialog();
                dialog.setOnHandleChangedCallback(new HandleDialog.OnHandleChanged() {
                    @Override
                    public void change(String result) {
                        activeHandle = result;
                        invalidateOptionsMenu();

                        dialog = null;
                    }
                });
                dialog.show(getSupportFragmentManager(), "handle");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
