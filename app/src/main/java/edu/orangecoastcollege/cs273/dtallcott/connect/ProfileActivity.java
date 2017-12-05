package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView avatarImageView;
    private TextView fullNameTextView;
    private TextView descriptionTextView;
    private LinearLayout coursesTakenLinearLayout;
    private LinearLayout coursesLinearLayout; //only used if student takes at least one course

    private View underline1;

    private CardView contactsCardView;
    private LinearLayout contactsLinearLayout; //only used if student takes at least one course
    private View underline2;

    private FloatingActionButton fab;
    private BottomSheetDialog mBottomSheetDialog;
    private LinearLayout requestLocationButtonLayout;
    private LinearLayout inviteButtonLayout;

    Student selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        avatarImageView = (ImageView) findViewById(R.id.avatarImageView);

        fullNameTextView = (TextView) findViewById(R.id.fullNameTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        coursesTakenLinearLayout = (LinearLayout) findViewById(R.id.coursesTakenLinearLayout);
        coursesLinearLayout = (LinearLayout) findViewById(R.id.coursesLinearLayout);
        underline1 = findViewById(R.id.underline1);

        contactsCardView = (CardView)findViewById(R.id.contactsCardView);
        contactsLinearLayout = (LinearLayout)findViewById(R.id.contactsLinearLayout);
        underline2 = findViewById(R.id.underline2);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setCanceledOnTouchOutside(true);
        //onClick attribute in xml will cause errors on devices with android 6.0
        //=>programmatically handle onClick events
        View mBottomSheetView = getLayoutInflater().inflate(R.layout.bottomsheet_dialog_layout, null);
        mBottomSheetDialog.setContentView(mBottomSheetView);
        requestLocationButtonLayout = (LinearLayout) mBottomSheetView.findViewById(R.id.requestLocationButtonLayout);
        inviteButtonLayout = (LinearLayout) mBottomSheetView.findViewById(R.id.inviteButtonLayout);
        requestLocationButtonLayout.setOnClickListener(this);
        inviteButtonLayout.setOnClickListener(this);

        selectedStudent = getIntent().getParcelableExtra("SelectedStudent");
        String sender = getIntent().getStringExtra("Sender");
        if(sender != null && sender.equals("MainActivity"))
            fab.setVisibility(View.GONE);
        populateLayoutWithInformation();
        triggerAnimations();
    }

    public void populateProfileOptions(View v)
    {
        mBottomSheetDialog.show();
    }

    public void requestLocation(View v)
    {
        mBottomSheetDialog.dismiss();
        Intent mapIntent = new Intent(this, MapActivity.class);
        mapIntent.putExtra("SelectedStudent",selectedStudent);
        startActivity(mapIntent);
    }

    public void invitetoStudyGroup(View v)
    {
        Toast.makeText(this, "invite to a study group", Toast.LENGTH_SHORT).show();
        mBottomSheetDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
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

        fullNameTextView.setText(selectedStudent.getFullName());
        descriptionTextView.setText("\"" + selectedStudent.getDescription() + "\"");

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

        if(!selectedStudent.getPrivacy())
        {
            String contacts[] = selectedStudent.getContacts().split("\\|");
            for(int  i = 0; i < contacts.length; i++)
            {
                TextView contactTextView = new TextView(this);
                contactTextView.setLayoutParams(
                        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                contactTextView.setText(contacts[i]);

                contactsLinearLayout.addView(contactTextView);
            }
            contactsCardView.setVisibility(View.VISIBLE);
        }
    }

    private void triggerAnimations()
    {
        Animation slideAnimation = AnimationUtils.loadAnimation(this,R.anim.slide_right);
        underline1.startAnimation(slideAnimation);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation slideAnimation2 = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.slide_right);
                underline2.startAnimation(slideAnimation2);
            }
        }, 300);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.requestLocationButtonLayout:
                requestLocation(view);
                break;
            case R.id.inviteButtonLayout:
                invitetoStudyGroup(view);
                break;
        }
    }
}
