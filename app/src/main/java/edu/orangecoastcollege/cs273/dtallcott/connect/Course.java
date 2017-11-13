package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HuynhHuu on 12-Nov-17.
 */

public class Course implements Parcelable{
    private String mName;
    private String mCourseNumber;
    private String mMajor;
    private String mInstructor;

    public Course(String name, String courseNumber, String major) {
        mName = name;
        mCourseNumber = courseNumber;
        mMajor = major;
        mInstructor = "";
    }

    public Course(String name, String courseNumber, String major, String instructor) {
        this.mName = name;
        this.mCourseNumber = courseNumber;
        this.mMajor = major;
        this.mInstructor = instructor;
    }

    protected Course(Parcel in) {
        mName = in.readString();
        mCourseNumber = in.readString();
        mMajor = in.readString();
        mInstructor = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getCourseNumber() {
        return mCourseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.mCourseNumber = courseNumber;
    }

    public String getMajor() {
        return mMajor;
    }

    public void setMajor(String major) {
        this.mMajor = major;
    }

    public String getInstructor() {
        return mInstructor;
    }

    public void setInstructor(String instructor) {
        mInstructor = instructor;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;
        return mCourseNumber.equals(course.getCourseNumber());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mCourseNumber);
        parcel.writeString(mMajor);
        parcel.writeString(mInstructor);
    }
}
