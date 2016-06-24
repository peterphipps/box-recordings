/* The MIT License (MIT)
 * Copyright (c) 2016 YouView Ltd
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.youview.tinydnssd.demo;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youview.tinydnssd.MDNSDiscover;

/**
 * Base class for an adapter of {@link com.youview.tinydnssd.MDNSDiscover.Result} objects.
 * Subclasses to implement {@link #getItem(int)} and {@link #getCount()}.
 */
abstract class ResultAdapter extends BaseAdapter {
    private static class Holder {
        TextView mTextModel, mTextSerial;
    }

    @Override
    public abstract MDNSDiscover.Result getItem(int position);

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        Holder holder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list_item, parent, false);
            holder = new Holder();
            view.setTag(holder);
            holder.mTextModel = (TextView) view.findViewById(R.id.text_model);
            holder.mTextSerial = (TextView) view.findViewById(R.id.text_serial);
        } else {
            view = convertView;
            holder = (Holder) view.getTag();
        }
        final MDNSDiscover.Result item = getItem(position);
        String ipAndPort = null, dnsName = null, txtRecord = null;
        String txtModel = null, txtSerial = null, txtUid = null;
        if (item.a != null) {
            dnsName = item.a.fqdn;
            if (item.srv != null) {
                ipAndPort = item.a.ipaddr + ":" + item.srv.port;
            }
        }
        SetTopBox setTopBox;
        if (item.txt != null) {
            setTopBox = new SetTopBox(item);
        }
        else {
            setTopBox = new SetTopBox(0);
        }
        holder.mTextModel.setText(setTopBox.getVendor() + " " + setTopBox.getModel());
        holder.mTextSerial.setText("Serial ending in: " + setTopBox.getSerial());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), PairBoxActivity.class);
                SetTopBox setTopBox = new SetTopBox(item);
                intent.putExtra("setTopBox", setTopBox);
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
