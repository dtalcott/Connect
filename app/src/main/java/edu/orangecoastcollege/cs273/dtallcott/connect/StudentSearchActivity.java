package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class StudentSearchActivity extends AppCompatActivity {

    private StudentDBHelper mStudentDBHelper;
    private List<Student> mStudentsList;
    private StudentListAdapter mStudentListAdapter;

    private ListView studentsListView;
    private TextView emptyListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search);

        studentsListView = (ListView) findViewById(R.id.studentsListView);
        emptyListTextView = (TextView) findViewById(R.id.emptyListTextView);

        mStudentDBHelper = new StudentDBHelper(this);

        String whereStatement = getIntent().getStringExtra("WhereStatement");
        mStudentsList = mStudentDBHelper.getStudents(whereStatement);

        if(mStudentsList.isEmpty())
        {
            studentsListView.setVisibility(View.GONE);
            emptyListTextView.setVisibility(View.VISIBLE);
        }
        else {
            mStudentListAdapter = new StudentListAdapter(this, R.layout.student_search_row_layout, mStudentsList);
            studentsListView.setAdapter(mStudentListAdapter);
        }
    }

    public void sendtoProfile(View view)
    {
        if(view instanceof LinearLayout)
        {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    view.findViewById(R.id.avatarImageView),getString(R.string.transition_name_avatar));

            Student selectedStudent = (Student) view.getTag();
            Intent profileIntent= new Intent(this, ProfileActivity.class);
            profileIntent.putExtra("SelectedStudent", selectedStudent);
            startActivity(profileIntent, options.toBundle());
        }

    }
}
