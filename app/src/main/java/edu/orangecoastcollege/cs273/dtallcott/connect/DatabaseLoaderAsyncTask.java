package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by HuynhHuu on 01-Dec-17.
 */

public class DatabaseLoaderAsyncTask extends AsyncTask<String, Void, Void> {

    private Context mContext;
    private DBHelper mDBHelper;
    public IDatabaseLoaderTaskResponse delegate;

    public DatabaseLoaderAsyncTask(Context context)
    {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
       // super.onPreExecute();
        mDBHelper = new DBHelper(mContext);

    }

    @Override
    protected Void doInBackground(String... strings) {
        for(String s : strings)
            switch (s)
            {
                case "Students":
                    populateStudentsDatabase();
                    break;
                case "Courses":
                    populateCoursesDatabase();
                    break;
                case "Majors":
                    populateMajorsDatabase();
                    break;
                case "Users":
                    populateUserDatabase();
                    break;
            }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      //  super.onPostExecute(aVoid);
        delegate.OnAllDatabasesLoaded();
    }

    public void populateStudentsDatabase()
    {
        mDBHelper.deleteAllStudents();
        mDBHelper.importStudentsFromCSV("students.csv");
    }

    public void populateUserDatabase()
    {
        mDBHelper.deleteAllUsers();
        mDBHelper.importUsersFromCSV("users.csv");
    }

    public void populateCoursesDatabase()
    {
        mDBHelper.deleteAllCourses();
        mDBHelper.importCoursesFromCSV("courses.csv");
    }

    public void populateMajorsDatabase()
    {
        mDBHelper.deleteAllMajors();
        mDBHelper.importMajorsFromCSV("majors.csv");
    }
}
