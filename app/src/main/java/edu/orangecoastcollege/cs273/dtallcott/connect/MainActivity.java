package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHelper mDBHelper;
    Student currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DBHelper(this);

        populateMajorsDatabase();

        currentStudent = getIntent().getParcelableExtra("Student");
        Toast.makeText(this, getResources().getString(R.string.welcome_back, currentStudent.getFullName())
                , Toast.LENGTH_SHORT).show();
    }



    public void populateMajorsDatabase()
    {
        mDBHelper.deleteAllMajors();
        mDBHelper.importMajorsFromCSV("majors.csv");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Resources res = getResources();
        switch(item.getItemId())
        {
            case R.id.search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.info:
                Intent infoIntent = new Intent(this, InformationActivity.class);
                startActivity(infoIntent);
                break;
            case R.id.add:
                Intent addIntent = new Intent(this, StudyActivity.class);
                startActivity(addIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
