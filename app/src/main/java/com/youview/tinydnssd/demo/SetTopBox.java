package com.youview.tinydnssd.demo;

import android.os.Parcel;
import android.os.Parcelable;

import com.youview.tinydnssd.MDNSDiscover;

import java.util.Set;

/**
 * Created by pphipps on 21/06/16.
 */
public class SetTopBox implements Parcelable {
    String model = null, serial = null, uid = null;

    public SetTopBox(MDNSDiscover.Result item) {
        model = item.txt.dict.get("model");
        serial = item.txt.dict.get("serial4");
        uid = item.txt.dict.get("uid");
    }

    public SetTopBox(int init) {
        switch (init) {
            case 0:
                model = "error";
                serial = "error";
                uid = "error";
                break;
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private SetTopBox(Parcel in) {
        String[] data = new String[3];

        in.readStringArray(data);
        this.model = data[0];
        this.serial = data[1];
        this.uid = data[2];
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.model,
                this.serial,
                this.uid});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SetTopBox createFromParcel(Parcel in) {
            return new SetTopBox(in);
        }

        public SetTopBox[] newArray(int size) {
            return new SetTopBox[size];
        }
    };



}
