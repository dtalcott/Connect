package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HuynhHuu on 12-Nov-17.
 */

class StudentDBHelper extends SQLiteOpenHelper {

    private Context mContext;
    static final String DATABASE_NAME = "Connect";
    private static final int DATABASE_VERSION = 1;

    private static final String STUDENT_DATABASE_TABLE = "Students";
    private static final String COURSE_DATABASE_TABLE = "Courses";

    //Students
    private static final String FIELD_STUDENT_NUMBER = "student_number";
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME = "last_name";
    private static final String FIELD_COURSES = "courses";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_CONTACTS = "contacts";


    //Courses
    private static final String KEY_FIELD_COURSE_ID = "_id";
    private static final String FIELD_COURSE_NUMBER = "course_number";
    private static final String FIELD_COURSE_NAME = "course_name";
    private static final String FIELD_MAJOR = "major";

    public StudentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStudentDatabase = "CREATE TABLE " + STUDENT_DATABASE_TABLE  + "("
                + FIELD_STUDENT_NUMBER +" TEXT PRIMARY KEY , "
                + FIELD_FIRST_NAME + " TEXT, "
                + FIELD_LAST_NAME + " TEXT, "
                + FIELD_COURSES + " TEXT, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_CONTACTS + " TEXT"
                + ")";
        db.execSQL(createStudentDatabase);

        String createCourseDatabase = "CREATE TABLE " + COURSE_DATABASE_TABLE + "("
                + KEY_FIELD_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_COURSE_NUMBER + " TEXT, "
                + FIELD_COURSE_NAME + " TEXT, "
                + FIELD_MAJOR + " TEXT"
                + ")";
        Log.e("ConnectTest", createCourseDatabase);
        db.execSQL(createCourseDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_DATABASE_TABLE);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + COURSE_DATABASE_TABLE);
        onCreate(db);
    }

    //----------------------------- STUDENTS-DATABASE--------------------------------------------

    private String coursesListToString(List<Course> courses) {
        String coursesString = "";
        for (Course c : courses)
            coursesString += c.getCourseNumber() + "|";
        return coursesString.substring(0, coursesString.length() - 1);
    }

    private List<Course> coursesStringToList(String coursesString) {
        List<Course> courses = new ArrayList<>();
        String[] coursesArray = coursesString.split("\\|");

        //TODO: use database
        for (int i = 0; i < coursesArray.length; i++) {
            courses.add(getCourse(coursesArray[i]));
        }
        return courses;
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_FIRST_NAME, student.getFirstName());
        values.put(FIELD_LAST_NAME, student.getLastName());
        values.put(FIELD_COURSES, coursesListToString(student.getCourses()));
        db.insert(STUDENT_DATABASE_TABLE, null, values);
        db.close();
    }

    public String createWhereStatement(Student dummy) {
        String where = "";
        if (!dummy.getFirstName().isEmpty())
            where += FIELD_FIRST_NAME + " = '" + dummy.getFirstName() + "' AND ";
        if (!dummy.getLastName().isEmpty())
            where += FIELD_LAST_NAME + " = '" + dummy.getLastName() + "' AND ";

        String coursesString = coursesListToString(dummy.getCourses());
        if (!coursesString.isEmpty()) {
            String[] coursesArray = coursesString.split("\\|");
            for (int i = 0; i < coursesArray.length; i++)
                where += FIELD_COURSES + " LIKE '%" + coursesArray[i] + "%' AND ";
        }
        return where.substring(0, where.length() - 5);
    }

    public List<Student> getStudents(String whereStatement) {
        //dummy student might not have enough information
        //we will construct students with detailed information
        SQLiteDatabase db = getReadableDatabase();

        List<Student> students = new ArrayList<>();

        Cursor cursor = db.query(STUDENT_DATABASE_TABLE, new String[]{FIELD_STUDENT_NUMBER, FIELD_FIRST_NAME,
                        FIELD_LAST_NAME, FIELD_COURSES, FIELD_DESCRIPTION, FIELD_CONTACTS}, whereStatement
                , null, null, null, FIELD_LAST_NAME + " ASC");

        if (cursor.moveToFirst()) {
            do {
                Student newStudent = new Student(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2),coursesStringToList(cursor.getString(3)),
                        cursor.getString(4), cursor.getString(5));
                students.add(newStudent);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return students;
    }

    public void deleteAllStudents()
    {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(STUDENT_DATABASE_TABLE, null, null);
        db.close();
    }

    public boolean importStudentsFromCSV(String csvFileName) {
        AssetManager manager = mContext.getAssets();
        InputStream inStream;
        try {
            inStream = manager.open(csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line;
        try {
            while ((line = buffer.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 3) {
                    Log.d("OCC Connect", "Skipping Bad CSV Row: " + Arrays.toString(fields));
                    continue;
                }
                String studentNumber = fields[0].trim();
                String firstName = fields[1].trim();
                String lastName = fields[2].trim();
                String courses = fields[3].trim();
                String description = fields[4].trim();
                String contacts = fields[5].trim();

                addStudent(new Student(studentNumber, firstName, lastName,
                        coursesStringToList(courses), description,contacts));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //-----------------------------STUDENTS-DATABASE----ENDS---HERE---------------------------------------
    //-----------------------------------------------------------------------------------------


    //---------------------------COURSES-DATABASE-----STARTS-------HERE------------------------------
    public void addCourse(Course course) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_COURSE_NUMBER, course.getCourseNumber());
        values.put(FIELD_COURSE_NAME, course.getName());
        values.put(FIELD_MAJOR, course.getMajor());

        db.insert(COURSE_DATABASE_TABLE, null, values);
        db.close();
    }

    public List<Course> getAllCourses() {
        SQLiteDatabase db = getReadableDatabase();

        List<Course> courses = new ArrayList<>();

        Cursor cursor = db.query(COURSE_DATABASE_TABLE, new String[]{FIELD_COURSE_NUMBER, FIELD_COURSE_NAME, FIELD_MAJOR},
                null,null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Course newCourse = new Course(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2));
                courses.add(newCourse);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return courses;
    }

    public Course getCourse(String courseNumber) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(COURSE_DATABASE_TABLE, new String[]{FIELD_COURSE_NUMBER, FIELD_COURSE_NAME, FIELD_MAJOR},
                FIELD_COURSE_NUMBER +" = ?", new String[]{courseNumber}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Course newCourse = new Course(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2));

        db.close();
        cursor.close();

        return newCourse;
    }

    public void deleteAllCourses()
    {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(COURSE_DATABASE_TABLE, null, null);
        db.close();
    }

    public boolean importCoursesFromCSV(String csvFileName) {
        AssetManager manager = mContext.getAssets();
        InputStream inStream;
        try {
            inStream = manager.open(csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line;
        try {
            while ((line = buffer.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 3) {
                    Log.d("OCC Connect", "Skipping Bad CSV Row: " + Arrays.toString(fields));
                    continue;
                }
                String courseNumber = fields[0].trim();
                String courseName = fields[1].trim();
                String major = fields[2].trim();
                addCourse(new Course(courseNumber, courseName, major));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
