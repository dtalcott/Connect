package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by devontallcott on 11/9/17.
 */

public class LoginActivity extends AppCompatActivity
{
    private DBHelper mDBHelper;
    private EditText userNameEditText;
    private EditText passwordEitText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEitText = (EditText) findViewById(R.id.passwordEitText);
        
        mDBHelper = new DBHelper(this);
        
        populateUserDatabase();
    }

    public void populateUserDatabase()
    {
//        mDBHelper.deleteAllUsers();
//        mDBHelper.importUsersFromCSV("users.csv");
    }
    
    public void gotoMainActivity(View view)
    {
//        String username = userNameEditText.getText().toString();
//        String password = passwordEitText.getText().toString();
//        if(username.isEmpty() || password.isEmpty())
//            Toast.makeText(this, R.string.empty_username_password, Toast.LENGTH_SHORT).show();
//        else{
//            Student student = mDBHelper.getStudentFromUser(username,password);
//            if(student == null)
//                Toast.makeText(this, R.string.wrong_username_or_password, Toast.LENGTH_SHORT).show();
//            else {
//                Intent mainIntent = new Intent(this, MainActivity.class);
//                mainIntent.putExtra("Student",student);
//                startActivity(mainIntent);
//            }
//        }
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}
