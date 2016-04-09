package com.accion.assigment.studentinfo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by swapnil on 9/4/16.
 */
public class Student implements Parcelable {
    @SerializedName("fName")
    private String firstName;

    @SerializedName("lName")
    private String lastName;

    @SerializedName("rollNo")
    private String rollNo;

    @SerializedName("profilePic")
    private String profilePic;

    @SerializedName("marks")
    private ArrayList<Marks> markList;

    @Override
    public int describeContents() {
        return 0;
    }

    private Student(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        rollNo = in.readString();
        profilePic = in.readString();
        if (markList == null) {
            markList = new ArrayList<>();
        }
        in.readList(markList, Marks.class.getClassLoader());
        setMarkList(markList);
    }

    public static final Parcelable.Creator<Student> CREATOR
            = new Parcelable.Creator<Student>() {
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(rollNo);
        parcel.writeString(profilePic);
        parcel.writeList(markList);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public ArrayList<Marks> getMarkList() {
        return markList;
    }

    public void setMarkList(ArrayList<Marks> markList) {
        this.markList = markList;
    }
}

