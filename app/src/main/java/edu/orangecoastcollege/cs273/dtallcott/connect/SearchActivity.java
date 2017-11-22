package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText lastNameEditText;
    private EditText firstNameEditText;

    private LinearLayout mutualCoursesLinearLayout;
    private LinearLayout otherCoursesLinearLayout;

    private View underline1;
    private View underline2;

    //all selected course that the user selected
    private List<Course> mAllSelectedCoursesList;
    //all available courses for the user to add
    private List<Course> mAllCoursesList;
    //all linearLayout that are created by user
    private List<LinearLayout> mLinearLayoutsList;

    private List<Course> mAllMutualCourses;

    private List<Major> mAllMajorsList;

    private StudentDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        mutualCoursesLinearLayout = (LinearLayout) findViewById(R.id.mutualCoursesLinearLayout);
        otherCoursesLinearLayout = (LinearLayout) findViewById(R.id.otherCoursesLinearLayout);

        underline1 = findViewById(R.id.underline1);
        underline2 = findViewById(R.id.underline2);

        mAllSelectedCoursesList = new ArrayList<>();
        mLinearLayoutsList = new ArrayList<>();
        mAllMutualCourses = new ArrayList<>();
        mAllMajorsList = new ArrayList<>();

        db = new StudentDBHelper(this);
        mAllMajorsList = db.getAllMajors();

        populateMutualCourses();
        populateOneOtherCourse(false);
        triggerAnimations();
    }

    private void populateMutualCourses()
    {
        mAllMutualCourses = new ArrayList<>();
        //TODO: only load courses from the user. For now, manually build the list
        mAllMutualCourses.add(new Course("CS A273", "Mobile Application Development", "Computer Science"));
        mAllMutualCourses.add(new Course("CS A200", "C++ Programming 2", "Computer Science"));
        mAllMutualCourses.add(new Course("MATH A285", "Linear Algebra and Differential Equations", "Math"));

        for(final Course c : mAllMutualCourses)
        {
            CheckBox courseCheckBox = new CheckBox(this);
            courseCheckBox.setText(c.getCourseNumber() +" - " + c.getName());
            courseCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if(isChecked)
                        mAllSelectedCoursesList.add(c);
                    else
                        mAllSelectedCoursesList.remove(c);
                }
            });
            mutualCoursesLinearLayout.addView(courseCheckBox);
        }
    }

    private void populateOneOtherCourse(boolean allowDeletion)
    {
        LayoutParams linearLayoutLayoutParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final LinearLayout anotherCourseLayout = new LinearLayout(this);
        anotherCourseLayout.setOrientation(LinearLayout.HORIZONTAL);
        anotherCourseLayout.setLayoutParams(linearLayoutLayoutParams);

        LayoutParams editTextLayoutParams =
                new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);

        EditText otherCourse = new EditText(this);
        otherCourse.setLayoutParams(editTextLayoutParams);
        otherCourse.setEnabled(false);
        otherCourse.setHint(R.string.add_another_course_search_activity);


        LayoutParams buttonLayoutParams =
                new LayoutParams(80, LayoutParams.WRAP_CONTENT);

        Button button = new Button(this);
        button.setLayoutParams(buttonLayoutParams);
        if(!allowDeletion) {
            button.setText(R.string.add_button_search_activity);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addCourse();
                }
            });
        }
        else {
            button.setText(R.string.delete_button_search_activity);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCourse(anotherCourseLayout);
                }
            });
        }
        anotherCourseLayout.addView(otherCourse);
        anotherCourseLayout.addView(button);

        otherCoursesLinearLayout.addView(anotherCourseLayout);
        mLinearLayoutsList.add(anotherCourseLayout);
    }

    private void triggerAnimations()
    {
        Animation slideAnimation = AnimationUtils.loadAnimation(this,R.anim.slide_right);
        underline1.startAnimation(slideAnimation);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation slideAnimation2 = AnimationUtils.loadAnimation(SearchActivity.this, R.anim.slide_right);
                underline2.startAnimation(slideAnimation2);
            }
        }, 300);
    }

    private void addCourse()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_a_course_search_activity);

        mAllCoursesList = new ArrayList<>();
        mAllCoursesList = db.getAllCourses();


        final String[] allCoursesArray = new String[mAllCoursesList.size()];
        for(int  i =0; i < allCoursesArray.length; i++)
            allCoursesArray[i] = mAllCoursesList.get(i).getCourseNumber() + " - " + mAllCoursesList.get(i).getName();

//        builder.setSingleChoiceItems(allCoursesArray, 0, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Course selectedCourse = mAllCoursesList.get(i);
//                if(mAllSelectedCoursesList.contains(selectedCourse))
//                    Toast.makeText(SearchActivity.this, R.string.duplicates_warning, Toast.LENGTH_SHORT).show();
//                else {
//                    mAllSelectedCoursesList.add(selectedCourse);
//                    populateOneOtherCourse(true);
//                    LinearLayout layout = mLinearLayoutsList.get(mLinearLayoutsList.size() - 1);
//                    ((EditText)layout.getChildAt(0)).setText(allCoursesArray[i]);
//                    dialogInterface.dismiss();
//                }
//            }
//        });

        builder.setSingleChoiceItems(allCoursesArray, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Course selectedCourse = mAllCoursesList.get(i);
                if(mAllSelectedCoursesList.contains(selectedCourse))
                    Toast.makeText(SearchActivity.this, R.string.duplicates_warning, Toast.LENGTH_SHORT).show();
                else {
                    mAllSelectedCoursesList.add(selectedCourse);
                    populateOneOtherCourse(true);
                    LinearLayout layout = mLinearLayoutsList.get(mLinearLayoutsList.size() - 1);
                    ((EditText)layout.getChildAt(0)).setText(allCoursesArray[i]);
                    dialogInterface.dismiss();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    private void deleteCourse(LinearLayout anotherCourseLayout)
    {

        String fullCourseName = ((EditText)anotherCourseLayout.getChildAt(0)).getText().toString();
        String courseNumber = fullCourseName.substring(0, fullCourseName.indexOf("-") -1);
        Course courseToDelete = searchCourseFromDatabase(courseNumber);

        mAllSelectedCoursesList.remove(courseToDelete);

        mLinearLayoutsList.remove(anotherCourseLayout);
        otherCoursesLinearLayout.removeView(anotherCourseLayout);
    }

    private Course searchCourseFromDatabase(String courseNumber)
    {
        return db.getCourse(courseNumber);
    }

    public void searchByInformation(View view)
    {
        if(mAllSelectedCoursesList.isEmpty())
            Toast.makeText(this, R.string.no_courses_selected_search_activity, Toast.LENGTH_SHORT).show();
        else {
            Student dummy = new Student(firstNameEditText.getText().toString(),
                    lastNameEditText.getText().toString(), mAllSelectedCoursesList);

            Intent listActivityIntent = new Intent(this, StudentSearchActivity.class);
            listActivityIntent.putExtra("WhereStatement", db.createWhereStatement(dummy));
            startActivity(listActivityIntent);
        }
    }
}
