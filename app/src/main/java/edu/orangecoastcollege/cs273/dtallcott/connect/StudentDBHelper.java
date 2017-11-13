package edu.orangecoastcollege.cs273.dtallcott.connect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuynhHuu on 12-Nov-17.
 */

class StudentDBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "Connect";
    private static final String DATABASE_TABLE = "Students";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "_id";
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME = "last_name";
    private static final String FIELD_COURSES = "courses";
    private static final String FIELD_IMAGE_NAME = "image_name";

    public StudentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDatabase = "CREATE TABLE " + DATABASE_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_FIRST_NAME + " TEXT, "
                + FIELD_LAST_NAME + " TEXT, "
                + FIELD_COURSES + " TEXT, "
                + FIELD_IMAGE_NAME + " TEXT"
                + ")";
        db.execSQL(createDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

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
            Course course = new Course("", "", "");
            switch (coursesArray[i]) {
                case "CS A150":
                    course = new Course("C++ Programming 1", "CS A150", "Computer Science");
                    break;
                case "CS A250":
                    course = new Course("C++ Programming 2", "CS A250", "Computer Science");
                    break;
                case "MATH A180":
                    course = new Course("Calculus 1", "MATH A180", "Math");
                    break;
                case "HIST A170":
                    course = new Course("History of U.S. to 1876", "HIST A170", "History");
                    break;
                case "CS A273":
                    new Course("Mobile Application Development", "CS A273", "Computer Science");
                    break;
                case "CS A200":
                    new Course("Data Structures", "CS A200", "Computer Science");
                    break;
                case "MATH A285":
                    new Course("Linear Algebra and Differential Equations", "MATH A285", "Math");
                    break;
                default:
                    course = new Course("", "", "");
                    break;
            }
            courses.add(course);
        }
        return courses;
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_FIRST_NAME, student.getFirstName());
        values.put(FIELD_LAST_NAME, student.getLastName());
        values.put(FIELD_COURSES, coursesListToString(student.getCourses()));
        db.insert(DATABASE_TABLE, null, values);
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

    public List<Student> getStudent(String whereStatement) {
        //dummy student might not have enough information
        //we will construct students with detailed information
        SQLiteDatabase db = getReadableDatabase();

        List<Student> students = new ArrayList<>();

        Cursor cursor = db.query(DATABASE_TABLE, new String[]{FIELD_FIRST_NAME, FIELD_LAST_NAME, FIELD_COURSES, FIELD_IMAGE_NAME}, whereStatement
                , null, null, null, FIELD_LAST_NAME + " ASC");

        if (cursor.moveToFirst()) {
            do {
                Student newStudent = new Student(cursor.getString(0), cursor.getString(1),
                        coursesStringToList(cursor.getString(2)));
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

        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }
}
