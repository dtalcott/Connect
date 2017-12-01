package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

public class CreateStudyGroup extends AppCompatActivity
{
    private DBHelper db;
    private List<Major> allMajorsList;
    private List<Course> allCoursesList;

    private Spinner majorSpinner;
    private ListView coursesListView;

    private CoursesListAdapter coursesListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_group);

       /* db = new DBHelper(this);
        db.importMajorsFromCSV("majors.csv");
        db.importCoursesFromCSV("courses.csv");

        allMajorsList = db.getAllMajors();
        allCoursesList = db.getAllCourses();

        majorSpinner = (Spinner) findViewById(R.id.studyGroupMajorSpinner);
        coursesListView = (ListView) findViewById(R.id.studyGroupCourseListView);

        ArrayAdapter<String> instructorSpinnerAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, getMajorNames());

        majorSpinner.setAdapter(majorSpinnerAdapter);
        majorSpinner.setOnItemSelectedListener(majorSpinnerListener);*/
    }
/*
    private String[] getMajorNames()
    {
        String[] majorNames = new String [allMajorsList.size() + 1];
        majorNames[0] = "[Select Major]";
        for (int i = 1; i < majorNames.length; i++)
        {
            majorNames[i] = allMajorsList.get(i-1).getMajorName();
        }
        return majorNames;
    }

    public void reset(View v)
    {
        majorSpinner.setSelection(0);
        coursesListAdapter.clear();
        coursesListAdapter.addAll(allCoursesList);
    }

    public OnItemSelectedListener majorSpinnerListener = new OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            String majorName = String.valueOf(adapterView.getSelectedItem());
            coursesListAdapter.clear();
            if (majorName.equals("[Select Major]"))
                coursesListAdapter.addAll(allCourseList);
            else
            {
                for (Course c : allCoursesList)
                {
                    if (c.getMajor().toLowerCase().equals(majorName))
                    {
                        coursesListAdapter.add(c);
                    }
                }
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {

        }

    };
*/
}
