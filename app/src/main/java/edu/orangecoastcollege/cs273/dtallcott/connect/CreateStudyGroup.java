package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class CreateStudyGroup extends AppCompatActivity
{
    private DBHelper db;
    private List<StudyGroup> mAllStudyGroups;
    private List<Major> allMajorsList;
    private List<Course> allCoursesList;
    private Student currentStudent;
    private List<Course> selectedCoursesList;
    private String selectedMajor;
    private String selectedCourse;

    private Spinner majorSpinner;
    private Spinner courseSpinner;
    private EditText nameEditText;
    private EditText timeEditText;
    private EditText dateEditText;
    private EditText descriptionEditText;
    private EditText locationEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_group);
        Intent intent = getIntent();
        currentStudent = intent.getParcelableExtra("SelectedStudent");
        db = new DBHelper(this);
        db.importMajorsFromCSV("majors.csv");
        db.importCoursesFromCSV("courses.csv");

        allMajorsList = db.getAllMajors();
        allCoursesList = db.getAllCourses();

        majorSpinner = (Spinner) findViewById(R.id.studyGroupMajorSpinner);
        courseSpinner = (Spinner) findViewById(R.id.studyGroupCourseSpinner);

        ArrayAdapter<String> majorSpinnerAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, getMajorNames());

        majorSpinner.setAdapter(majorSpinnerAdapter);
        majorSpinner.setOnItemSelectedListener(majorSpinnerListener);

        ArrayAdapter<String> courseSpinnerAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, getCourseNames());

        courseSpinner.setAdapter(courseSpinnerAdapter);
        courseSpinner.setOnItemSelectedListener(courseSpinnerListener);
    }

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

    private String[] getCourseNames()
    {
        String[] courseNames = new String [allCoursesList.size() + 1];
        courseNames[0] = "[Select Course]";
        for (int i = 1; i < courseNames.length; i++)
        {
            courseNames[i] = allCoursesList.get(i-1).getName();
        }
        return courseNames;
    }

    public void reset(View v)
    {
        majorSpinner.setSelection(0);
        courseSpinner.setSelection(0);
    }

    public AdapterView.OnItemSelectedListener majorSpinnerListener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            String majorName = String.valueOf(adapterView.getSelectedItem());
            selectedCoursesList.clear();
            if (majorName.equals("[Select Major]"))
                selectedCoursesList = allCoursesList;
            else
            {
                for (Course c : allCoursesList)
                {
                    if (c.getMajor().toLowerCase().equals(majorName))
                    {
                        selectedCoursesList.add(c);
                    }
                }
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {

        }

    };

    public AdapterView.OnItemSelectedListener courseSpinnerListener = new AdapterView.OnItemSelectedListener()
    {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            String courseName = String.valueOf(adapterView.getSelectedItem());

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {

        }
    };

    public boolean textEmpty()
    {
        if (nameEditText.toString().isEmpty())
            return true;
        if (selectedMajor.equals("[Select Major]"))
            return true;
        if (selectedCourse.equals("[Select Course]"))
            return true;
        if (dateEditText.toString().isEmpty())
            return true;
        if (timeEditText.toString().isEmpty())
            return true;
        if (descriptionEditText.toString().isEmpty())
            return true;
        return false;

    }

  public void goToStudyActivity(View v)
  {
      selectedMajor = majorSpinner.getSelectedItem().toString();
      selectedCourse = courseSpinner.getSelectedItem().toString();

      if (!textEmpty())
      {
          StudyGroup newStudyGroup = new StudyGroup(nameEditText.getText().toString(), selectedCourse,
                  timeEditText.getText().toString(), dateEditText.getText().toString(),
                  descriptionEditText.getText().toString(), locationEditText.getText().toString(), currentStudent);
          mAllStudyGroups.add(newStudyGroup);
          //TODO: add study group to database
          Intent mainIntent = new Intent(this, StudyActivity.class);
          mainIntent.putExtra("CurrentStudent", currentStudent);
          startActivity(mainIntent);
      }
      else
          Toast.makeText(CreateStudyGroup.this, getString(R.string.empty_toast), Toast.LENGTH_SHORT).show();
  }
}
