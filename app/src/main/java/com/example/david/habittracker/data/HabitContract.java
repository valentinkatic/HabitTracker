package com.example.david.habittracker.data;

import android.provider.BaseColumns;

public class HabitContract {

    public HabitContract(){}

    public static abstract class HabitEntry implements BaseColumns {

        public final static String TABLE_NAME = "habits";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DURATION = "duration";

    }

}
