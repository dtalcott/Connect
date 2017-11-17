package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

    public void gotoFindStudyGroup(View view)
    {
        Intent mainIntent = new Intent(this, FindStudyGroup.class);
        startActivity(mainIntent);
    }

    public void gotoCreateStudyGroup(View view)
    {
        Intent mainIntent = new Intent(this, CreateStudyGroup.class);
        startActivity(mainIntent);
    }
}
