package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class StudentSearchActivity extends AppCompatActivity {

    private StudentDBHelper mStudentDBHelper;
    private List<Student> mStudentsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search);
        mStudentDBHelper = new StudentDBHelper(this);

        Student dummy = getIntent().getParcelableExtra("SelectedStudent");
        mStudentsList = mStudentDBHelper.getStudent(dummy);
    }
}
