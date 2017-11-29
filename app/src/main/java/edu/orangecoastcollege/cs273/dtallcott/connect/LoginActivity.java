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
    private String mUserName;
    private String mPassword;
    private EditText mUserNameEditText;
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserNameEditText = (EditText) findViewById(R.id.userNameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEitText);

    }

    public void gotoMainActivity(View view)
    {

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}
