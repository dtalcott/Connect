package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class FindStudyGroup extends AppCompatActivity
{
    private List<StudyGroup> allStudyGroups;
    private Student currentStudent;
    private StudyGroup selectedStudyGroup;

    private DBHelper mDBHelper;

    private ListView studyGroupListView;
    private StudyGroupListAdapter studyGroupListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_study_group);
        Intent incomingIntent = getIntent();
        currentStudent = incomingIntent.getParcelableExtra("CurrentStudent");
        allStudyGroups = mDBHelper.getStudyGroups();

        studyGroupListView = (ListView) findViewById(R.id.findStudyGroupListView);
        studyGroupListAdapter =
                new StudyGroupListAdapter(this, R.layout.study_group_list_item, allStudyGroups);
        studyGroupListView.setAdapter(studyGroupListAdapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        Intent detailsIntent = new Intent(this, StudyGroupDetails.class);
        selectedStudyGroup = allStudyGroups.get(position);
        detailsIntent.putExtra("SelectedStudyGroup", selectedStudyGroup);
        detailsIntent.putExtra("CurrentStudent", currentStudent);
        startActivity(detailsIntent);
    }


}
