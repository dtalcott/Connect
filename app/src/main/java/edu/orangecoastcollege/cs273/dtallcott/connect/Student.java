package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuynhHuu on 12-Nov-17.
 */

public class Student implements Parcelable {

    private String mStudentNumber;
    private String mFirstName;
    private String mLastName;
    private String mFullName;
    private List<Course> mCourses;
    private String mImageName;
    private String mDescription;
    private String mContacts;

    public Student(String studentNumber, String firstName, String lastName, List<Course> courses,
                   String description, String contacts) {
        mStudentNumber = studentNumber;
        mFirstName = firstName;
        mLastName = lastName;
        mFullName = firstName + " " + lastName;
        mCourses = courses;
        mImageName = mFirstName + mLastName + ".jpg";
        mDescription = description;
        mContacts = contacts;
    }

    public Student(String firstName, String lastName, List<Course> courses) {
        mFirstName = firstName;
        mLastName = lastName;
        mCourses = courses;
//        mCourses = new ArrayList<>();
//        String[] coursesArray = coursesString.split("\\|");
//
//        StudentDBHelper db = new StudentDBHelper(context);
//
//        for (int i = 0; i < coursesArray.length; i++)
//            mCourses.add(db.getCourse(coursesArray[i]));
    }

    private Student(Parcel parcel) {
        mStudentNumber = parcel.readString();
        mFirstName = parcel.readString();
        mLastName = parcel.readString();
        mFullName = mFirstName + " " + mLastName;
        mCourses = new ArrayList<Course>();
        parcel.readList(mCourses, getClass().getClassLoader());
        mImageName = mFirstName + mLastName + ".jpg";
        mDescription = parcel.readString();
        mContacts = parcel.readString();
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

    public String getImageName() {
        return mImageName;
    }

    public void setImageName(String imageName) {
        mImageName = imageName;
    }

    public String getStudentNumber() {
        return mStudentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        mStudentNumber = studentNumber;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getContacts() {
        return mContacts;
    }

    public void setContacts(String contacts) {
        mContacts = contacts;
    }

    public String getFullName() {
        return mFullName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mStudentNumber);
        parcel.writeString(mFirstName);
        parcel.writeString(mLastName);
        parcel.writeList(mCourses);
        parcel.writeString(mDescription);
        parcel.writeString(mContacts);
    }

}
