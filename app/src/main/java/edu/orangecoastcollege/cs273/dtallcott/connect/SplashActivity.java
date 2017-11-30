package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        logoImageView = (ImageView) findViewById(R.id.logoImageView);

        Animation logoAnimation = AnimationUtils.loadAnimation(this,R.anim.logo_animation);
        logoImageView.startAnimation(logoAnimation);

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
