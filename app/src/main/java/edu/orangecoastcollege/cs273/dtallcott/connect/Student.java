package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuynhHuu on 12-Nov-17.
 */

public class Student implements Parcelable {
    private String mFirstName;
    private String mLastName;
    private List<Course> mCourses;

    public Student(String firstName, String lastName, List<Course> courses) {
        mFirstName = firstName;
        mLastName = lastName;
        mCourses = courses;
    }

    public Student(String firstName, String lastName, String coursesString) {
        mFirstName = firstName;
        mLastName = lastName;

        mCourses = new ArrayList<>();
        String[] coursesArray = coursesString.split("\\|");

        //TODO: use database
        for (int i = 0; i < coursesArray.length; i++) {
            switch (coursesArray[i]) {
                case "CS A150":
                    mCourses.add(new Course("C++ Programming 1", "CS A150", "Computer Science"));
                    break;
                case "CS A250":
                    mCourses.add(new Course("C++ Programming 2", "CS A250", "Computer Science"));
                    break;
                case "MATH A180":
                    mCourses.add(new Course("Calculus 1", "MATH A180", "Math"));
                    break;
                case "HIST A170":
                    mCourses.add(new Course("History of U.S. to 1876", "HIST A170", "History"));
                    break;
                case "CS A273":
                    mCourses.add(new Course("Mobile Application Development", "CS A273", "Computer Science"));
                    break;
                case "CS A200":
                    mCourses.add(new Course("Data Structures", "CS A200", "Computer Science"));
                    break;
                case "MATH A285":
                    mCourses.add(new Course("Linear Algebra and Differential Equations", "MATH A285", "Math"));
                    break;
                default:
                    mCourses.add(new Course("", "", ""));
                    break;
            }

        }
    }

    private Student(Parcel parcel) {
        mFirstName = parcel.readString();
        mLastName = parcel.readString();

        mCourses = new ArrayList<Course>();
        parcel.readList(mCourses, null);
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
