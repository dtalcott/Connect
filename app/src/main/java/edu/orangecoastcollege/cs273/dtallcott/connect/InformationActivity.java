package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
