package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DBHelper mDBHelper;
    private Student currentStudent;
    private boolean backPressedOnce;

    private CardView searchCardView;
    private CardView studyGroupCardView;
    private CardView profileCardView;
    private CardView infoCardView;

    private CircleImageView profileImageView;

    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private ShakeDetector mShakeDetector;

    private boolean changeLogShown = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        searchCardView = (CardView) findViewById(R.id.searchCardView);
        studyGroupCardView = (CardView) findViewById(R.id.studyGroupCardView);
        profileCardView = (CardView) findViewById(R.id.profileCardView);
        infoCardView = (CardView) findViewById(R.id.infoCardView);
        profileImageView = (CircleImageView) findViewById(R.id.profileImageView);

        mDBHelper = new DBHelper(this);

        populateMajorsDatabase();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                if(!changeLogShown) {
                    changeLogShown = true;
                    Toast.makeText(MainActivity.this, "You found me!!!!!!", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                    mBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            changeLogShown = false;
                        }
                    });
                    mBuilder.setCancelable(false)
                            .setTitle("Change log")
                            .setMessage(R.string.changelog)
                            .create().show();
                }
            }
        });


        currentStudent = getIntent().getParcelableExtra("Student");
        Toast.makeText(this, getResources().getString(R.string.welcome_back, currentStudent.getFullName())
                , Toast.LENGTH_SHORT).show();

        AssetManager am = getAssets();
        try {
            InputStream is = am.open(currentStudent.getImageName());
            profileImageView.setImageDrawable(Drawable.createFromStream(is, currentStudent.getFullName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        searchCardView.setOnClickListener(this);
        studyGroupCardView.setOnClickListener(this);
        profileCardView.setOnClickListener(this);
        infoCardView.setOnClickListener(this);

        triggerAnimations();
    }

    public void populateMajorsDatabase() {
        mDBHelper.deleteAllMajors();
        mDBHelper.importMajorsFromCSV("majors.csv");
    }

    @Override
    public void onBackPressed() {
        if (!backPressedOnce) {
            backPressedOnce = true;
            Toast.makeText(this, R.string.back_again_to_exit, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressedOnce = false;
                }
            }, 500);
        } else {
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
        switch (view.getId()) {
            case R.id.searchCardView:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                searchIntent.putExtra("CurrentStudent", currentStudent);
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
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this, profileImageView, getString(R.string.transition_name_avatar));
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                profileIntent.putExtra("SelectedStudent", currentStudent);
                profileIntent.putExtra("Sender", "MainActivity");
                startActivity(profileIntent, options.toBundle());
        }
    }

    private void triggerAnimations() {
        Animation cardviewAnimation = AnimationUtils.loadAnimation(this, R.anim.cardview_go_up);
        searchCardView.setVisibility(View.VISIBLE);
        searchCardView.startAnimation(cardviewAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation cardviewAnimation2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cardview_go_up);
                studyGroupCardView.setVisibility(View.VISIBLE);
                studyGroupCardView.startAnimation(cardviewAnimation2);
            }
        }, 200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation cardviewAnimation3 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cardview_go_up);
                profileCardView.setVisibility(View.VISIBLE);
                profileCardView.startAnimation(cardviewAnimation3);
            }
        }, 400);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation cardviewAnimation4 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cardview_go_up);
                infoCardView.setVisibility(View.VISIBLE);
                infoCardView.startAnimation(cardviewAnimation4);
            }
        }, 600);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector,accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(mShakeDetector, accelerometer);
    }
}
