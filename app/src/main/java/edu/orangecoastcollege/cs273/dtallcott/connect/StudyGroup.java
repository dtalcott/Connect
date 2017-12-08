package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dtallcott on 11/16/2017.
 */

public class StudyGroup implements Parcelable
{
    private String mTitle;
    private String mCourse;
    private String mTime;
    private String mDate;
    private String mDescription;
    private String mLocation;
    private Student mHostStudent;
    private List<Student> mStudents;

    public StudyGroup(String title, String course, String time, String date, String description, String location, Student student)
    {
        mTitle = title;
        mCourse = course;
        mTime = time;
        mDate = date;
        mDescription = description;
        mLocation = location;
        mHostStudent = student;
        mStudents = new ArrayList<>();
        mStudents.add(student);
    }

    public StudyGroup(String title, String course, String time, String date, String description, String location, List<Student> students)
    {
        mTitle = title;
        mCourse = course;
        mTime = time;
        mDate = date;
        mDescription = description;
        mLocation = location;
        mHostStudent = students.get(0);
        mStudents = students;
    }

    private StudyGroup(Parcel parcel) {
        mTitle = parcel.readString();
        mCourse = parcel.readString();
        mTime = parcel.readString();
        mDate = parcel.readString();
        mDescription = parcel.readString();
        mLocation = parcel.readString();
        mStudents = new ArrayList<>();
        parcel.readList(mStudents, getClass().getClassLoader());
        mHostStudent = mStudents.get(0);
    }

    public static final Creator<StudyGroup> CREATOR = new Creator<StudyGroup>() {
        @Override
        public StudyGroup createFromParcel(Parcel in) {
            return new StudyGroup(in);
        }

        @Override
        public StudyGroup[] newArray(int size) {
            return new StudyGroup[size];
        }
    };

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmCourse() {
        return mCourse;
    }

    public void setmCourse(String mCourse) {
        this.mCourse = mCourse;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getDate() { return mDate; }

    public void setDate(String date) { mDate = date; }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public Student getmHostStudent() {
        return mHostStudent;
    }

    public void setmHostStudent(Student mHostStudent) {
        this.mHostStudent = mHostStudent;
    }

    public List<Student> getmStudents() {
        return mStudents;
    }

    public void setmStudents(List<Student> mStudents) {
        this.mStudents = mStudents;
    }

    public void addStudent(Student student)
    {
        mStudents.add(student);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(mTitle);
        parcel.writeString(mCourse);
        parcel.writeString(mTime);
        parcel.writeString(mDate);
        parcel.writeString(mDescription);
        parcel.writeString(mLocation);
        parcel.writeList(mStudents);
    }

}
