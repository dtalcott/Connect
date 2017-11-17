package edu.orangecoastcollege.cs273.dtallcott.connect;

import java.util.List;

/**
 * Created by dtallcott on 11/16/2017.
 */

public class StudyGroup
{
    private String mTitle;
    private String mCourse;
    private String mTime;
    private String mDescription;
    private String mLocation;
    private Student mHostStudent;
    private List<Student> mStudents;

    public StudyGroup(String title, String course, String time, String description, String location, Student student)
    {
        mTitle = title;
        mCourse = course;
        mTime = time;
        mDescription = description;
        mLocation = location;
        mHostStudent = student;
        mStudents.add(student);
    }

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


}
