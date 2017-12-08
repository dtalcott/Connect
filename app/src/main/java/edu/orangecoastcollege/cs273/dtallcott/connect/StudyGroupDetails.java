package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;

public class StudyGroupDetails extends AppCompatActivity
{
    private Student currentStudent;
    private List<Student> allStudents;
    private StudyGroup selectedStudyGroup;

    private TextView studyGroupNameTextView;
    private TextView studyGroupCourseTextView;
    private TextView studyGroupTimeTextView;
    private TextView studyGroupDateTextView;
    private TextView studyGroupDescriptionTextView;
    private TextView studyGroupLocationTextView;
    private ListView studentsListView;

    private GoogleMap mMap;
    private StudyGroupLocation studyGroupLocation;
    private StudentListAdapter studentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_group_details);

        Intent incomingIntent = getIntent();
        currentStudent = incomingIntent.getParcelableExtra("CurrentStudent");
        selectedStudyGroup = incomingIntent.getParcelableExtra("SelectedStudyGroup");

        studyGroupNameTextView = (TextView) findViewById(R.id.detailsTitleTextView);
        studyGroupCourseTextView = (TextView) findViewById(R.id.detailsCourseTextView);
        studyGroupTimeTextView = (TextView) findViewById(R.id.detailsTimeextView);
        studyGroupDateTextView =(TextView) findViewById(R.id.detailsDateTextView);
        studyGroupDescriptionTextView = (TextView) findViewById(R.id.detailsDescriptionTextView);
        studyGroupLocationTextView = (TextView) findViewById(R.id.detailsLocationTextView);


        studyGroupNameTextView.setText(selectedStudyGroup.getmTitle());
        studyGroupCourseTextView.setText(selectedStudyGroup.getmCourse());
        studyGroupTimeTextView.setText(selectedStudyGroup.getmTime());
        studyGroupDateTextView.setText(selectedStudyGroup.getDate());
        studyGroupDescriptionTextView.setText(selectedStudyGroup.getmDescription());
        studyGroupLocationTextView.setText(selectedStudyGroup.getmLocation());

        allStudents = selectedStudyGroup.getmStudents();

        studentsListView = (ListView) findViewById(R.id.detailsStudentsListView);
        studentListAdapter =
                new StudentListAdapter(this, R.layout.student_search_row_layout, allStudents);
        studentsListView.setAdapter(studentListAdapter);

    }
}
