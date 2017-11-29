package edu.orangecoastcollege.cs273.dtallcott.connect;

import static android.os.Build.ID;

/**
 * Created by dtallcott on 11/28/2017.
 */

public class User
{
    private String mID;
    private String mUserName;
    private String mPassword;

    public User(String iD, String userName, String password)
    {
        mID = ID;
        mUserName = userName;
        mPassword = password;
    }

    public String getmID()
    {
        return mID;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
