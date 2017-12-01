package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by devontallcott on 11/9/17.
 */

public class SplashActivity extends AppCompatActivity
{
    private ImageView logoImageView;
    private SharedPreferences mSharedPreferences;
    private DBHelper mDBHelper;
    private Intent loginIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        logoImageView = (ImageView) findViewById(R.id.logoImageView);

        Animation logoAnimation = AnimationUtils.loadAnimation(this,R.anim.logo_animation);
        logoImageView.startAnimation(logoAnimation);

        //put these in the background using AsyncTasks
        mDBHelper = new DBHelper(this);
        populateCoursesDatabase();
        populateStudentsDatabase();
        populateUserDatabase();

        mSharedPreferences = getSharedPreferences("edu.orangecoastcollege.cs273.dtallcott.connect", MODE_PRIVATE);
        String username = mSharedPreferences.getString("username","");
        String password = mSharedPreferences.getString("password", "");

        if(username.isEmpty() || password.isEmpty())
            loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
        else
        {
            User user = mDBHelper.getUser(username,password);
            loginIntent = new Intent(this, MainActivity.class);
            loginIntent.putExtra("Student", mDBHelper.getStudent(user));
        }
        //Load for 3 seconds
        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                startActivity(loginIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }

    public void populateStudentsDatabase()
    {
        mDBHelper.deleteAllStudents();
        mDBHelper.importStudentsFromCSV("students.csv");
    }

    public void populateUserDatabase()
    {
        mDBHelper.deleteAllUsers();
        mDBHelper.importUsersFromCSV("users.csv");
    }

    public void populateCoursesDatabase()
    {
        mDBHelper.deleteAllCourses();
        mDBHelper.importCoursesFromCSV("courses.csv");
    }
}
