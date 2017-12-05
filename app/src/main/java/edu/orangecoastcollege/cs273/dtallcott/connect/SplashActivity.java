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

public class SplashActivity extends AppCompatActivity implements IDatabaseLoaderTaskResponse
{
    private ImageView logoImageView;
    private SharedPreferences mSharedPreferences;
    private DBHelper mDBHelper;
    private Intent loginIntent;

    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        logoImageView = (ImageView) findViewById(R.id.logoImageView);

        Animation logoAnimation = AnimationUtils.loadAnimation(this,R.anim.logo_animation);
        logoImageView.startAnimation(logoAnimation);

        mSharedPreferences = getSharedPreferences("edu.orangecoastcollege.cs273.dtallcott.connect", MODE_PRIVATE);
        username = mSharedPreferences.getString("username","");
        password = mSharedPreferences.getString("password", "");

        mDBHelper = new DBHelper(this);
        //load 4 databases in the background using AsyncTasks
        DatabaseLoaderAsyncTask databaseLoaderTask = new DatabaseLoaderAsyncTask(this);
        databaseLoaderTask.delegate = this;
        databaseLoaderTask.execute(new String[]{"Courses", "Majors", "Users", "Students"});
    }

    @Override
    public void OnAllDatabasesLoaded() {
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
}
