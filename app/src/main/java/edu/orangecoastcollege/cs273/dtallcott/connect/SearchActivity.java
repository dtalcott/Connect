package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        populateMutualCourses();
        populateOneOtherCourse(false);
        triggerAnimations();
    }

    private void populateMutualCourses()
    {
        //Use database. For now, manually build all the mutual courses
        mAllMutualCourses = new ArrayList<>();
        mAllMutualCourses.add(new Course("Mobile Application Development", "CS A273", "Computer Science" ));
        mAllMutualCourses.add(new Course("Data Structures", "CS A200", "Computer Science" ));
        mAllMutualCourses.add(new Course("Linear Algebra and Differential Equations", "MATH A285", "Math" ));

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
        underline2.startAnimation(slideAnimation);

    }
    private void addCourse()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_a_course_search_activity);

        mAllCoursesList = new ArrayList<>();
        mAllCoursesList.add(new Course("C++ Programming 1", "CS A150", "Computer Science" ));
        mAllCoursesList.add(new Course("C++ Programming 2", "CS A250", "Computer Science" ));
        mAllCoursesList.add(new Course("Calculus 1", "MATH A180", "Math" ));
        mAllCoursesList.add(new Course("History of U.S. to 1876", "HIST A170", "History" ));

        final String[] allCoursesArray = new String[mAllCoursesList.size()];
        for(int  i =0; i < allCoursesArray.length; i++)
            allCoursesArray[i] = mAllCoursesList.get(i).getCourseNumber() + " - " + mAllCoursesList.get(i).getName();

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
        //This will return a record in database
        //Since database is not established, we manually build the course
        Course course;
        switch (courseNumber)
        {
            case "CS A150":
                course = new Course("C++ Programming 1", "CS A150", "Computer Science" );
                break;
            case "CS A250":
                course = new Course("C++ Programming 2", "CS A250", "Computer Science" );
                break;
            case "MATH A180":
                course = new Course("Calculus 1", "MATH A180", "Math" );
                break;
            case "HIST A170":
                course = new Course("History of U.S. to 1876", "HIST A170", "History" );
                break;
            default:
                course = new Course("","","");
                break;
        }
        return course;
    }

    public void searchByInformation(View view)
    {
        Student dummy = new Student(firstNameEditText.getText().toString(),
                lastNameEditText.getText().toString(), mAllSelectedCoursesList);
        Intent listActivityIntent = new Intent(this, ListActivity.class);
        listActivityIntent.putExtra("SelectedStudent", dummy);
        startActivity(listActivityIntent);
    }
}
