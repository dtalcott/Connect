package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by devontallcott on 11/9/17.
 */

public class LoginActivity extends AppCompatActivity
{
    private DBHelper mDBHelper;
    private EditText userNameEditText;
    private EditText passwordEitText;
    private SharedPreferences mSharePreferences;
    private SharedPreferences.Editor editor;
    private RelativeLayout loginRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEitText = (EditText) findViewById(R.id.passwordEitText);
        loginRelativeLayout = (RelativeLayout) findViewById(R.id.loginRelativeLayout);

        mDBHelper = new DBHelper(this);

        mSharePreferences = getSharedPreferences("edu.orangecoastcollege.cs273.dtallcott.connect", MODE_PRIVATE);
        editor = mSharePreferences.edit();

        userNameEditText.setText(mSharePreferences.getString("username",""));
        passwordEitText.setText(mSharePreferences.getString("password",""));
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void gotoMainActivity(View view)
    {
        String username = userNameEditText.getText().toString();
        String password = passwordEitText.getText().toString();
        Animation shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.error_layout_animation);
        if(username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.empty_username_password, Toast.LENGTH_SHORT).show();
            loginRelativeLayout.startAnimation(shakeAnimation);
        }
        else{
            User user = mDBHelper.getUser(username,password);
            if(user == null) {
                Toast.makeText(this, R.string.wrong_username_or_password, Toast.LENGTH_SHORT).show();
                loginRelativeLayout.startAnimation(shakeAnimation);
            }
            else {

                editor.putString("username",username);
                editor.putString("password",password);
                editor.commit();

                Student student = mDBHelper.getStudent(user);
                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.putExtra("Student",student);
                startActivity(mainIntent);
            }
        }
    }
}
