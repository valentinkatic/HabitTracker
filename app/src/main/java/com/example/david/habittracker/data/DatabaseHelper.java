package com.example.david.habittracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.david.habittracker.Habit;
import com.example.david.habittracker.data.HabitContract.HabitEntry;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "habittracker.db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_DESCRIPTION + " TEXT, "
                + HabitEntry.COLUMN_DATE + " LONG NOT NULL, "
                + HabitEntry.COLUMN_DURATION + " LONG);";

        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertHabit(Habit habit){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HabitEntry.COLUMN_NAME, habit.getName());
        contentValues.put(HabitEntry.COLUMN_DESCRIPTION, habit.getDescription());
        contentValues.put(HabitEntry.COLUMN_DATE, habit.getDate());
        contentValues.put(HabitEntry.COLUMN_DURATION, habit.getDuration());

        return db.insert(HabitEntry.TABLE_NAME, null, contentValues) != -1;
    }

    public List<Habit> readAllHabits(){
        List<Habit> habits = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_NAME,
                HabitEntry.COLUMN_DESCRIPTION,
                HabitEntry.COLUMN_DATE,
                HabitEntry.COLUMN_DURATION };

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_NAME);
        int descriptionColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DESCRIPTION);
        int dateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DATE);
        int durationColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DURATION);

        while (cursor.moveToNext()) {

            Habit habit = new Habit(
                    cursor.getInt(idColumnIndex),
                    cursor.getString(nameColumnIndex),
                    cursor.getString(descriptionColumnIndex),
                    cursor.getLong(dateColumnIndex),
                    cursor.getLong(durationColumnIndex)
            );

            habits.add(habit);
        }

        return habits;
    }

}
