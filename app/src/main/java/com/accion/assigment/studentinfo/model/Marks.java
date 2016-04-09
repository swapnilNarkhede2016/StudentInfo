package com.accion.assigment.studentinfo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by swapnil on 9/4/16.
 */
public class Marks implements Parcelable {
    @SerializedName("subjectName")
    private String subjectName;

    @SerializedName("marks")
    private int marks;

    public String getSubjectName() {
        return subjectName;
    }

    public int getMarks() {
        return marks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(subjectName);
        parcel.writeInt(marks);
    }

    private Marks(Parcel in) {
        subjectName = in.readString();
        marks = in.readInt();
    }

    public static final Parcelable.Creator<Marks> CREATOR
            = new Parcelable.Creator<Marks>() {
        public Marks createFromParcel(Parcel in) {
            return new Marks(in);
        }

        public Marks[] newArray(int size) {
            return new Marks [size];
        }
    };
}
