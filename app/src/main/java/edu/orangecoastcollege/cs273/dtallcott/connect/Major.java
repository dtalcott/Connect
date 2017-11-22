package edu.orangecoastcollege.cs273.dtallcott.connect;

/**
 * Created by phuynh101 on 11/21/2017.
 */

public class Major {
    private String majorId;
    private String majorName;

    public Major(String majorId, String majorName) {
        this.majorId = majorId;
        this.majorName = majorName;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
}
