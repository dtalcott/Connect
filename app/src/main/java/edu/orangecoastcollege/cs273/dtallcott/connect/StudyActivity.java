package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
