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

class DBHelper extends SQLiteOpenHelper {

    private Context mContext;
    static final String DATABASE_NAME = "Connect";
    private static final int DATABASE_VERSION = 12;

    private static final String STUDENT_DATABASE_TABLE = "Students";
    private static final String COURSE_DATABASE_TABLE = "Courses";
    private static final String MAJOR_DATABASE_TABLE = "Majors";
    private static final String USER_DATABASE_TABLE =  "Users";
    private static final String STUDY_GROUP_DATABASE_TABLE = "Study_Groups";

    //Students
    private static final String FIELD_STUDENT_NUMBER = "student_number";
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME = "last_name";
    private static final String FIELD_COURSES = "courses";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_CONTACTS = "contacts";
    private static final String FIELD_PRIVACY = "privacy";

    //Courses
    private static final String KEY_FIELD_COURSE_ID = "_id";
    private static final String FIELD_COURSE_NUMBER = "course_number";
    private static final String FIELD_COURSE_NAME = "course_name";
    private static final String FIELD_MAJOR = "major";

    //Majors
    private static final String FIELD_MAJOR_ID = "major_id";
    private static final String FIELD_MAJOR_NAME = "major_name";

    //Users
    private static final String FIELD_USER_STUDENT_NUMBER = "user_student_number";
    private static final String FIELD_USER_USERNAME = "username";
    private static final String FIELD_USER_PASSWORD = "password";

    //StudyGroup
    private static final String FIELD_STUDY_NAME = "study_group_name";
    private static final String FIELD_STUDY_COURSE = "study_group_course";
    private static final String FIELD_STUDY_DATE = "study_group_date";
    private static final String FIELD_STUDY_TIME = "study_group_time";
    private static final String FIELD_STUDY_DESCRIPTION = "study_group_description";
    private static final String FIELD_STUDY_LOCATION = "study_group_location";
    private static final String FIELD_STUDY_STUDENTS = "study_group_students";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStudentDatabase = "CREATE TABLE " + STUDENT_DATABASE_TABLE + "("
                + FIELD_STUDENT_NUMBER + " TEXT PRIMARY KEY , "
                + FIELD_FIRST_NAME + " TEXT, "
                + FIELD_LAST_NAME + " TEXT, "
                + FIELD_COURSES + " TEXT, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_CONTACTS + " TEXT, "
                + FIELD_PRIVACY + " INTEGER"
                + ")";
        db.execSQL(createStudentDatabase);

        String createCourseDatabase = "CREATE TABLE " + COURSE_DATABASE_TABLE + "("
                + KEY_FIELD_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_COURSE_NUMBER + " TEXT, "
                + FIELD_COURSE_NAME + " TEXT, "
                + FIELD_MAJOR + " TEXT"
                + ")";
        db.execSQL(createCourseDatabase);

        String createMajorDatabase = "CREATE TABLE " + MAJOR_DATABASE_TABLE + "("
                + FIELD_MAJOR_ID + " TEXT PRIMARY KEY, "
                + FIELD_MAJOR_NAME + " TEXT"
                + ")";
        db.execSQL(createMajorDatabase);

        String createUserDatabase = "CREATE TABLE " + USER_DATABASE_TABLE + "("
                + FIELD_USER_STUDENT_NUMBER + " TEXT PRIMARY KEY, "
                + FIELD_USER_USERNAME + " TEXT, "
                + FIELD_USER_PASSWORD + " TEXT"
                + ")";
        db.execSQL(createUserDatabase);

        String createStudyGroupDatabase = "CREATE TABLE " + STUDY_GROUP_DATABASE_TABLE + "("
                + FIELD_STUDY_NAME + " TEXT PRIMARY KEY, "
                + FIELD_STUDY_COURSE + " TEXT, "
                + FIELD_STUDY_TIME + " TEXT, "
                + FIELD_STUDY_DATE + " TEXT, "
                + FIELD_STUDY_DESCRIPTION + " TEXT, "
                + FIELD_STUDY_LOCATION + " TEXT, "
                + FIELD_STUDY_STUDENTS + " TEXT)";
        db.execSQL(createStudyGroupDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COURSE_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MAJOR_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STUDY_GROUP_DATABASE_TABLE);

        onCreate(db);
    }

    //----------------------------- STUDENTS-DATABASE--------------------------------------------

    private String coursesListToString(List<Course> courses) {
        if(courses.isEmpty())
            return "";

        String coursesString = "";
        for (Course c : courses)
            coursesString += c.getCourseNumber() + "|";
        return coursesString.substring(0, coursesString.length() - 1);
    }

    private List<Course> coursesStringToList(String coursesString) {
        List<Course> courses = new ArrayList<>();
        if (!coursesString.isEmpty()) {
            String[] coursesArray = coursesString.split("\\|");

            for (int i = 0; i < coursesArray.length; i++) {
                courses.add(getCourse(coursesArray[i]));
            }
        }
        return courses;
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_STUDENT_NUMBER, student.getStudentNumber());
        values.put(FIELD_FIRST_NAME, student.getFirstName());
        values.put(FIELD_LAST_NAME, student.getLastName());
        values.put(FIELD_COURSES, coursesListToString(student.getCourses()));
        values.put(FIELD_DESCRIPTION, student.getDescription());
        values.put(FIELD_CONTACTS, student.getContacts());
        values.put(FIELD_PRIVACY, student.getPrivacy());
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
                        FIELD_LAST_NAME, FIELD_COURSES, FIELD_DESCRIPTION, FIELD_CONTACTS, FIELD_PRIVACY}, whereStatement
                , null, null, null, FIELD_LAST_NAME + " ASC");

        if (cursor.moveToFirst()) {
            do {
                Student newStudent = new Student(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), coursesStringToList(cursor.getString(3)),
                        cursor.getString(4), cursor.getString(5), cursor.getInt(6) == 1);
                students.add(newStudent);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return students;
    }

    public Student getStudent(User user)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(STUDENT_DATABASE_TABLE, new String[]{FIELD_STUDENT_NUMBER, FIELD_FIRST_NAME,
                FIELD_LAST_NAME, FIELD_COURSES, FIELD_DESCRIPTION, FIELD_CONTACTS, FIELD_PRIVACY},
                FIELD_STUDENT_NUMBER + " = ?", new String[]{user.getStudentID()},
                null,null,null);

        Student student = null;
        if(cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            student = new Student(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), coursesStringToList(cursor.getString(3)),
                    cursor.getString(4), cursor.getString(5), cursor.getInt(6) == 1);
        }
        return student;
    }

    public Student getStudentByStudentNumber(String studentNumber)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(STUDENT_DATABASE_TABLE, new String[]{FIELD_STUDENT_NUMBER, FIELD_FIRST_NAME,
                        FIELD_LAST_NAME, FIELD_COURSES, FIELD_DESCRIPTION, FIELD_CONTACTS, FIELD_PRIVACY},
                FIELD_STUDENT_NUMBER + " = ?", new String[]{studentNumber},
                null,null,null);

        Student student = null;
        if(cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            student = new Student(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), coursesStringToList(cursor.getString(3)),
                    cursor.getString(4), cursor.getString(5), cursor.getInt(6) == 1);
        }
        return student;
    }

    public void deleteAllStudents() {
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
                if (fields.length != 7) {
                    Log.d("OCC Connect", "Skipping Bad CSV Row: " + Arrays.toString(fields));
                    continue;
                }
                String studentNumber = fields[0].trim();
                String firstName = fields[1].trim();
                String lastName = fields[2].trim();
                String courses = fields[3].trim();
                String description = fields[4].trim();
                String contacts = fields[5].trim();
                boolean privacy = (Integer.parseInt(fields[6].trim()) == 1);
                addStudent(new Student(studentNumber, firstName, lastName,
                        coursesStringToList(courses), description, contacts, privacy));
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
                null, null, null, null, null);

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
                FIELD_COURSE_NUMBER + " = ?", new String[]{courseNumber}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Course newCourse = new Course(cursor.getString(0), cursor.getString(1),
                cursor.getString(2));

        db.close();
        cursor.close();

        return newCourse;
    }

    public List<Course> getCourseByMajor(String major) {
        SQLiteDatabase db = getReadableDatabase();

        List<Course> courses = new ArrayList<>();
        Cursor cursor = db.query(COURSE_DATABASE_TABLE, new String[]{FIELD_COURSE_NUMBER, FIELD_COURSE_NAME, FIELD_MAJOR},
                FIELD_MAJOR + " = ?", new String[]{major}, null, null, null);

        if(cursor.moveToFirst())
        {
            do{
                Course newCourse = new Course(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2));
                courses.add(newCourse);
            }while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return courses;
    }

    public void deleteAllCourses() {
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

    //------------------------------COURSES-DATABASE-ENDS-HERE-------------------------------------

    //-----------------------------MAJORS-DATABASE-STARTS-HERE-------------------------------------
    public void addMajor(Major major) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_MAJOR_ID, major.getMajorId());
        values.put(FIELD_MAJOR_NAME, major.getMajorName());

        db.insert(MAJOR_DATABASE_TABLE, null, values);
        db.close();
    }

    public List<Major> getAllMajors() {
        SQLiteDatabase db = getReadableDatabase();

        List<Major> majors = new ArrayList<>();

        Cursor cursor = db.query(MAJOR_DATABASE_TABLE, new String[]{FIELD_MAJOR_ID, FIELD_MAJOR_NAME},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Major major = new Major(cursor.getString(0), cursor.getString(1));
                majors.add(major);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return majors;
    }

    public void deleteAllMajors() {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(MAJOR_DATABASE_TABLE, null, null);
        db.close();
    }

    public boolean importMajorsFromCSV(String csvFileName) {
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
                if (fields.length != 2) {
                    Log.d("OCC Connect", "Skipping Bad CSV Row: " + Arrays.toString(fields));
                    continue;
                }
                String majorId = fields[0].trim();
                String majorName = fields[1].trim();
                addMajor(new Major(majorId,majorName));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //-----------------------------MAJORS-DATABASE-ENDS-HERE-------------------------------------

    //-----------------------------USERS-DATABASE-STARTS-HERE------------------------------------
    public void addUser(String studentNumber, String userUsername, String userPassword) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_USER_STUDENT_NUMBER, studentNumber);
        values.put(FIELD_USER_USERNAME, userUsername);
        values.put(FIELD_USER_PASSWORD, userPassword);
        db.insert(USER_DATABASE_TABLE, null, values);

        db.close();
    }

    public User getUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(USER_DATABASE_TABLE, new String[]{FIELD_USER_STUDENT_NUMBER, FIELD_USER_USERNAME, FIELD_USER_PASSWORD},
                FIELD_USER_USERNAME + " = ? AND " + FIELD_USER_PASSWORD + " =?", new String[]{username, password}, null, null, null);

        User user = null;

        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            user = new User(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2));
        }
        db.close();
        cursor.close();

        return user;
    }

    public void deleteAllUsers() {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(USER_DATABASE_TABLE, null, null);
        db.close();
    }

    public List<User> getAllUsers() {
        SQLiteDatabase db = getReadableDatabase();

        List<User> users = new ArrayList<>();

        /*Cursor cursor = db.query(USER_DATABASE_TABLE, new String[]{FIELD_USER_STUDENT_NUMBER, FIELD_USER_USERNAME, FIELD_USER_PASSWORD},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                users.add(user);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();*/
        users.add(new User("C00000", "phuynh", "123"));
        users.add(new User("C00001", "dtallcott", "1234"));

        return users;
    }

    public boolean importUsersFromCSV(String csvFileName) {
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
                String userStudentNumber = fields[0].trim();
                String userUserName = fields[1].trim();
                String userPassword = fields[2].trim();
                addUser(userStudentNumber, userUserName, userPassword);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //-----------------------------USERS-DATABASE-ENDS-HERE--------------------------------------
    //--------------------------STUDY-GROUP-DATABASE-STARTS-HERE---------------------------------
    public void addStudyGroup(String title, String course, String time, String date,
                              String description, String location, List<Student> students)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_STUDY_NAME, title);
        values.put(FIELD_STUDY_COURSE, course);
        values.put(FIELD_STUDY_TIME, time);
        values.put(FIELD_STUDY_DATE, date);
        values.put(FIELD_STUDY_DESCRIPTION, description);
        values.put(FIELD_STUDY_LOCATION, location);
        values.put(FIELD_STUDY_STUDENTS, studentListToString(students));

        db.insert(STUDY_GROUP_DATABASE_TABLE, null, values);

        db.close();
    }

    private List<Student> studentStringToStudentList(String studentsString)
    {
        List<String> studentsList = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        if (!studentsString.isEmpty()) {
            String[] studentsArray = studentsString.split("\\|");

            for (int i = 0; i < studentsArray.length; i++)
            {
                studentsList.add(studentsArray[i]);
                students.add(getStudentByStudentNumber(studentsArray[i]));
            }
        }
        return students;
    }

    private String studentListToString(List<Student> students)
    {
        String studentList = "";
        for (Student s : students)
        {
            studentList += s.getStudentNumber() + "|";
        }
        return studentList;
    }

    public List<StudyGroup> getStudyGroups() {
        SQLiteDatabase db = getReadableDatabase();

        List<StudyGroup> studyGroups = new ArrayList<>();

        Cursor cursor = db.query(STUDY_GROUP_DATABASE_TABLE, new String[]{FIELD_STUDY_NAME, FIELD_STUDY_COURSE,
                        FIELD_STUDY_TIME, FIELD_STUDY_DATE, FIELD_STUDY_DESCRIPTION, FIELD_STUDY_LOCATION, FIELD_STUDY_STUDENTS},
                        null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                StudyGroup newStudyGroup = new StudyGroup(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        studentStringToStudentList(cursor.getString(6)));
                studyGroups.add(newStudyGroup);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return studyGroups;
    }

    public void deleteAllStudyGroups() {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(STUDY_GROUP_DATABASE_TABLE, null, null);
        db.close();
    }

    public boolean importStudyGroupsFromCSV(String csvFileName) {
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
                if (fields.length != 8) {
                    Log.d("OCC Connect", "Skipping Bad CSV Row: " + Arrays.toString(fields));
                    continue;
                }
                String studyTitle = fields[0].trim();
                String studyCourse = fields[1].trim();
                String studyTime = fields[2].trim();
                String studyDate = fields[3].trim();
                String studyDescription = fields[4].trim();
                String studyLocation = fields[5].trim();
                String hostStudent = fields[6].trim();
                String studyStudents = fields[7].trim();

                addStudyGroup(studyTitle, studyCourse, studyTime, studyDate, studyDescription,
                    studyLocation, studentStringToStudentList(studyStudents));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //--------------------------STUDY-GROUP-DATABASE-ENDS-HERE-----------------------------------
}
