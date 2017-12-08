package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dtallcott on 11/16/2017.
 */

public class StudyGroupListAdapter extends ArrayAdapter<StudyGroup>
{
    private Context mContext;
    private int mResource;
    private List<StudyGroup> mAllStudyGroups;


    public StudyGroupListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<StudyGroup> allStudyGroups)
    {
        super(context, resource, allStudyGroups);
        mContext = context;
        mResource = resource;
        mAllStudyGroups = allStudyGroups;
    }



    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View listItemView = inflater.inflate(mResource, null);

        TextView listItemTitleTextView = (TextView) listItemView.findViewById(R.id.studyGroupTitleTextView);
        TextView listItemDateTextView = (TextView) listItemView.findViewById(R.id.studyGroupDateTextView);
        TextView listItemCourseTextView = (TextView) listItemView.findViewById(R.id.studyGroupCourseTextView);

        StudyGroup selectedStudyGroup = mAllStudyGroups.get(position);
        listItemTitleTextView.setText(selectedStudyGroup.getmTitle());
        listItemDateTextView.setText(selectedStudyGroup.getDate());
        listItemCourseTextView.setText(selectedStudyGroup.getmCourse());

        return listItemView;
    }
}
