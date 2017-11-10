package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by devontallcott on 11/9/17.
 */

public class SplashActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        //Load for 3 seconds
        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }
}
