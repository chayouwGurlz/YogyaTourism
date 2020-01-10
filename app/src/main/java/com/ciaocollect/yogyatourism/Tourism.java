package com.ciaocollect.yogyatourism;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Tourism implements Parcelable {
    private String tourism_name;
    private String tourism_place;
    private String tourism_detail;
    private String tourism_picture;

    public String getTourism_name() {
        return tourism_name;
    }

    public String getTourism_place() {
        return tourism_place;
    }

    public String getTourism_detail() {
        return tourism_detail;
    }

    public String getTourism_picture() { return tourism_picture; }

    public Tourism() { }

    public Tourism(JSONObject object) {
        try{
            String tourism_name = object.getString("nama_pariwisata");
            String tourism_place = object.getString("alamat_pariwisata");
            String tourism_detail = object.getString("detail_pariwisata");
            String tourism_picture = object.getString("gambar_pariwisata");
            this.tourism_name = tourism_name;
            this.tourism_place = tourism_place;
            this.tourism_detail = tourism_detail;
            this.tourism_picture = tourism_picture;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tourism_name);
        dest.writeString(this.tourism_place);
        dest.writeString(this.tourism_detail);
        dest.writeString(this.tourism_picture);
    }

    private Tourism(Parcel in) {
        this.tourism_name = in.readString();
        this.tourism_place = in.readString();
        this.tourism_detail = in.readString();
        this.tourism_picture = in.readString();
    }

    public static final Parcelable.Creator<Tourism> CREATOR = new Parcelable.Creator<Tourism>() {
        @Override
        public Tourism createFromParcel(Parcel source) {
            return new Tourism(source);
        }

        @Override
        public Tourism[] newArray(int size) {
            return new Tourism[size];
        }
    };
}
