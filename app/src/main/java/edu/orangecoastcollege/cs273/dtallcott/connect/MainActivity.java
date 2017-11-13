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

        populateStudentsDatabase();
    }

    public void populateStudentsDatabase()
    {
        mStudentDBHelper.deleteAllStudents();

        mStudentDBHelper.addStudent(new Student("Brian", "Wegener", "CS A273|MATH A180"));
        mStudentDBHelper.addStudent(new Student("Derek", "Tran", "CS A273"));
        mStudentDBHelper.addStudent(new Student("Peter", "Tang", "CS A273"));
        mStudentDBHelper.addStudent(new Student("Brendan", "Albert", "CS A273"));
        mStudentDBHelper.addStudent(new Student("Casey", "Thastanaphonh", "CS A273"));
        mStudentDBHelper.addStudent(new Student("Daniel", "Reyna", "CS A273"));
        mStudentDBHelper.addStudent(new Student("Lelouch", "Ong", "CS A273"));
        mStudentDBHelper.addStudent(new Student("Ryan", "Millett", "CS A273"));
        mStudentDBHelper.addStudent(new Student("Phuc", "Huynh", "CS A273"));
        mStudentDBHelper.addStudent(new Student("Devon", "Tallcott", "CS A273"));
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
