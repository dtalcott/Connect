package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    StudentDBHelper mStudentDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStudentDBHelper = new StudentDBHelper(this);

        populateCoursesDatabase();
        populateStudentsDatabase();
        populateMajorsDatabase();
    }

    public void populateStudentsDatabase()
    {
        mStudentDBHelper.deleteAllStudents();
        mStudentDBHelper.importStudentsFromCSV("students.csv");
    }

    public void populateCoursesDatabase()
    {
        mStudentDBHelper.deleteAllCourses();
        mStudentDBHelper.importCoursesFromCSV("courses.csv");
    }

    public void populateMajorsDatabase()
    {
        mStudentDBHelper.deleteAllMajors();
        mStudentDBHelper.importMajorsFromCSV("majors.csv");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Resources res = getResources();
        switch(item.getItemId())
        {
            case R.id.search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.info:
                Intent infoIntent = new Intent(this, InformationActivity.class);
                startActivity(infoIntent);
                break;
            case R.id.add:
                Intent addIntent = new Intent(this, StudyActivity.class);
                startActivity(addIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
