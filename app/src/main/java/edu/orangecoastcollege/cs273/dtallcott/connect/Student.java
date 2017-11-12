package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by HuynhHuu on 12-Nov-17.
 */

public class Student implements Parcelable{
    private String mFirstName;
    private String mLastName;
    private List<Course> mCourses;

    public Student(String firstName, String lastName, List<Course> courses) {
        mFirstName = firstName;
        mLastName = lastName;
        mCourses = courses;
    }

    private Student(Parcel parcel) {
        mFirstName = parcel.readString();
        mLastName = parcel.readString();
        parcel.readList(mCourses,null);
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel parcel) {
            return new Student(parcel);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public List<Course> getCourses() {
        return mCourses;
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mFirstName);
        parcel.writeString(mLastName);
        parcel.writeList(mCourses);
    }

}
