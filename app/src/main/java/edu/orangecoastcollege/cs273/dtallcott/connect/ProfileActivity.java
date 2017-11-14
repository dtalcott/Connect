package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ImageView avatarImageView;
    TextView fullNameTextView;
    LinearLayout coursesTakenLinearLayout;
    LinearLayout coursesLinearLayout; //only used if student takes at least one course
    View underline1;

    Student selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        avatarImageView = (ImageView) findViewById(R.id.avatarImageView);
        fullNameTextView = (TextView) findViewById(R.id.fullNameTextView);
        coursesTakenLinearLayout = (LinearLayout) findViewById(R.id.coursesTakenLinearLayout);
        coursesLinearLayout = (LinearLayout) findViewById(R.id.coursesLinearLayout);

        underline1 = findViewById(R.id.underline1);

        selectedStudent = getIntent().getParcelableExtra("SelectedStudent");

        populateLayoutWithInformation();
        triggerAnimations();

        //TODO: implement FAB
    }

    private void populateLayoutWithInformation() {
        AssetManager am = getAssets();
        try {
            InputStream is = am.open(selectedStudent.getImageName());
            Drawable drawable = Drawable.createFromStream(is, selectedStudent.getImageName());
            avatarImageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fullNameTextView.setText(selectedStudent.getFirstName() + " " + selectedStudent.getLastName());

        if (selectedStudent.getCourses().isEmpty()) {
            TextView courseTextView = new TextView(this);
            courseTextView.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            courseTextView.setText(R.string.student_with_no_courses);

            coursesTakenLinearLayout.addView(courseTextView);
        } else {
            List<Course> courses = selectedStudent.getCourses();

            for(Course c : courses)
            {
                TextView courseTextView = new TextView(this);
                courseTextView.setLayoutParams(
                        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                courseTextView.setText(c.getCourseNumber() + " - " + c.getName());

                coursesLinearLayout.addView(courseTextView);
            }
            coursesLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void triggerAnimations()
    {
        Animation slideAnimation = AnimationUtils.loadAnimation(this,R.anim.slide_right);
        underline1.startAnimation(slideAnimation);
    }
}
