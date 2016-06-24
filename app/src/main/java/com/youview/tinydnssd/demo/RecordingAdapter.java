package com.youview.tinydnssd.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youview.core.recordings.model.MediaItem;
import com.youview.core.recordings.model.MediaRecord;
import com.youview.tinydnssd.MDNSDiscover;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by pphipps on 23/06/16.
 */
public class RecordingAdapter extends BaseAdapter {
    private static class Holder {
        TextView mTextTitle, mTextDuration;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        Holder holder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recording_list_item, parent, false);
            holder = new Holder();
            view.setTag(holder);
            holder.mTextTitle = (TextView) view.findViewById(R.id.text_title);
            holder.mTextDuration = (TextView) view.findViewById(R.id.text_duration);
        } else {
            view = convertView;
            holder = (Holder) view.getTag();
        }
        //Get recording information
        final MediaItem item = (MediaItem) getItem(position);
        final MediaRecord recording = item.getMediaRecord();

        recording.getDuration();

        //Get recording information
        holder.mTextTitle.setText(recording.getTitle());
        int duration = recording.getDuration();
        holder.mTextDuration.setText(recording.getAcquisitionStatus());
//        holder
// xtDateTime.setText(item.dateTime);
        return view;
    }
}
