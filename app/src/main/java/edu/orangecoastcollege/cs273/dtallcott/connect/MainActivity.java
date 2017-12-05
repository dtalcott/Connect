package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper mDBHelper;
    Student currentStudent;
    boolean backPressedOnce;

    CardView searchCardView;
    CardView studyGroupCardView;
    CardView profileCardView;
    CardView infoCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        searchCardView = (CardView)findViewById(R.id.searchCardView);
        studyGroupCardView = (CardView) findViewById(R.id.studyGroupCardView);
        profileCardView = (CardView)findViewById(R.id.profileCardView);
        infoCardView = (CardView)findViewById(R.id.infoCardView);

        mDBHelper = new DBHelper(this);

        populateMajorsDatabase();

        currentStudent = getIntent().getParcelableExtra("Student");
        Toast.makeText(this, getResources().getString(R.string.welcome_back, currentStudent.getFullName())
                , Toast.LENGTH_SHORT).show();

        searchCardView.setOnClickListener(this);
        studyGroupCardView.setOnClickListener(this);
        profileCardView.setOnClickListener(this);
        infoCardView.setOnClickListener(this);
    }

    public void populateMajorsDatabase()
    {
        mDBHelper.deleteAllMajors();
        mDBHelper.importMajorsFromCSV("majors.csv");
    }

    @Override
    public void onBackPressed() {
        if(!backPressedOnce) {
            backPressedOnce = true;
            Toast.makeText(this, R.string.back_again_to_exit, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                  backPressedOnce = false;
                }
            }, 500);
        }else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent logoutIntent = new Intent(this, LoginActivity.class);
        startActivity(logoutIntent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.searchCardView:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                searchIntent.putExtra("CurrentStudent",currentStudent);
                startActivity(searchIntent);
                break;
            case R.id.infoCardView:
                Intent infoIntent = new Intent(this, InformationActivity.class);
                startActivity(infoIntent);
                break;
            case R.id.studyGroupCardView:
                Intent addIntent = new Intent(this, StudyActivity.class);
                startActivity(addIntent);
                break;
            case R.id.profileCardView:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                profileIntent.putExtra("SelectedStudent", currentStudent);
                startActivity(profileIntent);
        }
    }
}
