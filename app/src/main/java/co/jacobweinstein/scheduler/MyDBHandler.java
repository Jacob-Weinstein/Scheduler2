package co.jacobweinstein.scheduler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final String TAG = "MyDBHandler";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "assignments2.db";
    public static final String TABLE_ASSIGNMENTS = "assignments";

    public static final String ASSIGNMENT_ID = "_id";
    public static final String COLUMN_TITLE = "assignmentTitle";
    public static final String COLUMN_DAY_OF_MONTH = "dayOfMonth";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_CLASSNAME = "className";
    public static final String COLUMN_DESC = "description";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNMENTS);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ASSIGNMENTS + "(" +
                ASSIGNMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DAY_OF_MONTH + " INTEGER, " +
                COLUMN_MONTH + " INTEGER, " +
                COLUMN_YEAR + " INTEGER, " +
                COLUMN_CLASSNAME + " TEXT, " +
                COLUMN_DESC + " TEXT " +
                ");";
        db.execSQL(query);
    }

    public void addAssignment(Assignment assignment){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, assignment.getTitle());
        values.put(COLUMN_MONTH, assignment.getMonth());
        values.put(COLUMN_DAY_OF_MONTH, assignment.getDayOfMonth());
        values.put(COLUMN_YEAR, assignment.getYear());
        values.put(COLUMN_CLASSNAME, assignment.getClassName());
        values.put(COLUMN_DESC, assignment.getDesc());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ASSIGNMENTS, null, values);
        db.close();
    }
    public void deleteAssignment(String title, String className){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ASSIGNMENTS + " WHERE " + COLUMN_TITLE +" =\"" + title + "\" AND " + COLUMN_CLASSNAME + "=\"" + className+"\";");
        db.close();
    }
    public Assignment getAssignment(String title, String className){
        ArrayList<Assignment> assignments = databaseToAssignments();
        for (int i = 0;i<assignments.size();i++){
            Assignment s = assignments.get(i);
            if (s.getTitle().equals(title) && s.getClassName().equals(className)){
                return s;
            }
        }
        return null;
    }
    public void changeAssignment(String currentTitle, String currentClass, String title, String className, int dayOfMonth, int month, int year, String desc){
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_ASSIGNMENTS +
                " SET " + COLUMN_TITLE + "=\"" + title + "\", " +
                COLUMN_CLASSNAME + "=\"" + className + "\", " +
                COLUMN_DAY_OF_MONTH + "=" + dayOfMonth + ", " +
                COLUMN_MONTH + "=" + month + ", " +
                COLUMN_YEAR + "=" + year + ", " +
                COLUMN_DESC + "=\"" + desc + "\" WHERE " +
                COLUMN_TITLE + "=\"" + currentTitle + "\" AND " + COLUMN_CLASSNAME + "=\"" + currentClass + "\";"
                ;
        db.execSQL(query);
        db.close();
    }
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ASSIGNMENTS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);//cursor points to table
        c.moveToFirst();//move to first row of results
        String[] colN = c.getColumnNames();
        for (int i = 0;i<colN.length;i++){
            Log.i(TAG, colN[i]);
        }
        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex(COLUMN_TITLE)) != null){
                dbString += c.getString(c.getColumnIndex(COLUMN_TITLE)) + c.getInt(c.getColumnIndex(COLUMN_MONTH))+ "\n";
                Log.i(TAG, "found");
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }
    public ArrayList<Assignment> databaseToAssignments(){
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ASSIGNMENTS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);//cursor points to table
        c.moveToFirst();//move to first row of results
        String[] colN = c.getColumnNames();
        for (int i = 0;i<colN.length;i++){
            Log.i(TAG, colN[i]);
        }
        while (!c.isAfterLast()){
            Assignment s = new Assignment(
                    c.getString(c.getColumnIndex(COLUMN_TITLE)),
                    c.getInt(c.getColumnIndex(COLUMN_MONTH)) + "/" + c.getInt(c.getColumnIndex(COLUMN_DAY_OF_MONTH)) + "/" + c.getInt(c.getColumnIndex(COLUMN_YEAR)),
                    c.getString(c.getColumnIndex(COLUMN_CLASSNAME)),
                    c.getString(c.getColumnIndex(COLUMN_DESC))
            );
            assignments.add(s);
            c.moveToNext();
        }
        db.close();
        return assignments;
    }
    public String[] databaseToStringByLine(){
        ArrayList<Assignment> oldList = databaseToAssignments();
        String[] newList = new String[oldList.size()];
        for (int i = 0;i<oldList.size();i++){
            newList[i] = oldList.get(i).toString();
        }
        return newList;
    }

}
