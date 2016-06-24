package com.youview.tinydnssd.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import com.youview.core.recordings.GetRecordingsRequest;
import com.youview.core.recordings.model.MediaItem;
import com.youview.core.requestmanager.Callback;
import com.youview.core.requestmanager.Result;

import java.util.ArrayList;
import java.util.List;

public class RecordingListActivity extends AppCompatActivity {
    List<MediaItem> mRecordingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_list);
        //Populate listview
        final MyAdapter recordingAdapter = new MyAdapter();
        ((ListView) findViewById(R.id.recording_list)).setAdapter(recordingAdapter);

        new GetRecordingsRequest(new Callback<List<MediaItem>>() {
            @Override
            public void call(Result<List<MediaItem>> result) {
                mRecordingList = (List<MediaItem>) result.getValue();
                recordingAdapter.notifyDataSetChanged();
            }
        }).start();


    }

    private class MyAdapter extends RecordingAdapter {

        @Override
        public int getCount() {
            return mRecordingList.size();
        }
        @Override
        public MediaItem getItem(int position) {
            return mRecordingList.get(position);
        }
    }
}
