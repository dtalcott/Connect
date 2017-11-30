package edu.orangecoastcollege.cs273.dtallcott.connect;

import static android.os.Build.ID;

/**
 * Created by dtallcott on 11/28/2017.
 */

public class User
{
    private String mStudentID;
    private String mUserName;
    private String mPassword;

    public User(String studentID, String userName, String password)
    {
        mStudentID = studentID;
        mUserName = userName;
        mPassword = password;
    }

    public String getStudentID()
    {
        return mStudentID;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
