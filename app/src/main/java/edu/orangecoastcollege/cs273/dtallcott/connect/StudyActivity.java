package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity {

    private List<StudyGroup> mAllStudyGroups;
    private List<Student> mStudentList;
    private List<Course> mCourses;
    private List<Major> mMajors;
    private Student currentStudent;
    private List<StudyGroup> mUsersStudyGroups;
    private DBHelper mDBHelper;
    private String sender;
    private StudyGroup studyGroup;

    private ListView studyGroupListView;

    private StudyGroupListAdapter studyGroupListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        mDBHelper = new DBHelper(this);

        mUsersStudyGroups = new ArrayList<>();
        Intent intent = getIntent();
        currentStudent = intent.getParcelableExtra("CurrentStudent");
        sender = intent.getStringExtra("Sender");
        mAllStudyGroups = mDBHelper.getStudyGroups();
        if (sender.equals("CreateStudyGroup"))
        {
            studyGroup = intent.getParcelableExtra("AddedStudyGroup");
            mAllStudyGroups.add(studyGroup);
        }
        mCourses = mDBHelper.getAllCourses();
        mMajors = mDBHelper.getAllMajors();

        if (!mAllStudyGroups.isEmpty())
        {
            for (StudyGroup sg: mAllStudyGroups)
            {
                for (Student s : sg.getmStudents())
                {
                    if (s.equals(currentStudent))
                        mUsersStudyGroups.add(sg);
                }
            }
        }
        studyGroupListView = (ListView) findViewById(R.id.activityStudyGroupListView);
        studyGroupListAdapter =
                new StudyGroupListAdapter(this, R.layout.study_group_list_item, mUsersStudyGroups);
        studyGroupListView.setAdapter(studyGroupListAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    public void gotoFindStudyGroup(View view)
    {
        Intent mainIntent = new Intent(this, FindStudyGroup.class);
        mainIntent.putExtra("CurrentStudent", currentStudent);
        startActivity(mainIntent);
    }

    public void gotoCreateStudyGroup(View view)
    {
        Intent mainIntent = new Intent(this, CreateStudyGroup.class);
        mainIntent.putExtra("CurrentStudent", currentStudent);
        startActivity(mainIntent);
    }

    protected void onStudyGroupClicked (ListView l, View v, int position, long id)
    {
        Intent detailsIntent = new Intent(this, StudyGroupDetails.class);
        detailsIntent.putExtra("CurrentStudent", currentStudent);
        StudyGroup selectedStudyGroup = mAllStudyGroups.get(position);
        detailsIntent.putExtra("SelectedStudyGroup", selectedStudyGroup);
        startActivity(detailsIntent);
    }
}
