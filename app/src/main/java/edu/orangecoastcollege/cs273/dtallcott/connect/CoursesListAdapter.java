package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dtallcott on 11/30/2017.
 */

public class CoursesListAdapter extends ArrayAdapter<Course>
{
    private Context mContext;
    private List<Course> mCoursesList = new ArrayList<>();
    private int mResourceId;

    public CoursesListAdapter(Context c, int rId, List<Course> courses)
    {
        super(c, rId, courses);
        mContext = c;
        mResourceId = rId;
        mCoursesList = courses;
    }
}
