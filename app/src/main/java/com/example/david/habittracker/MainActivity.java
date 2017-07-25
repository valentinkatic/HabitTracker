package com.example.david.habittracker;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.habittracker.data.DatabaseHelper;
import com.example.david.habittracker.data.HabitContract;
import com.example.david.habittracker.data.HabitContract.HabitEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText nameEdit;
    private EditText descriptionEdit;
    private EditText dateEdit;
    private EditText durationEdit;
    private Button insertButton;
    private Button refreshButton;
    private TextView habitsList;

    private Calendar date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit = (EditText) findViewById(R.id.nameEdit);
        descriptionEdit = (EditText) findViewById(R.id.descriptionEdit);
        dateEdit = (EditText) findViewById(R.id.dateEdit);
        durationEdit = (EditText) findViewById(R.id.durationEdit);
        insertButton = (Button) findViewById(R.id.insertButton);
        refreshButton = (Button) findViewById(R.id.refreshButton);
        habitsList = (TextView) findViewById(R.id.habitsList);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, dateSetListener, year, month, day).show();
            }
        });

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewHabit();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshHabits();
            }
        });

    }

    private void refreshHabits(){
        List<Habit> habits = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(this);
        try {
            habits = db.readAllHabits();
        } catch (Exception e){}
        finally {
            db.close();
        }

        if (habits != null && habits.size()>0) {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format(Locale.getDefault(), "%4s|%-10s|%-15s|%-11s|%-8s\n", HabitEntry._ID, HabitEntry.COLUMN_NAME, HabitEntry.COLUMN_DESCRIPTION, HabitEntry.COLUMN_DATE, HabitEntry.COLUMN_DURATION));

            for (Habit h : habits){
                String myFormat = "dd.MM.yyyy.";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                builder.append(String.format(Locale.getDefault(), "%03d|%-10s|%-15s|%-11s|%-4dmin\n", h.getId(), h.getName(), h.getDescription(), sdf.format(h.getDate()), h.getDuration()));
            }

            habitsList.setText(builder.toString());
        }
    }

    private void insertNewHabit(){
        Habit habit = new Habit(
                nameEdit.getText().toString(),
                descriptionEdit.getText().toString(),
                date.getTimeInMillis(),
                Long.parseLong(durationEdit.getText().toString())
        );
        DatabaseHelper db = new DatabaseHelper(MainActivity.this);
        try {
            if (db.insertHabit(habit)){
                Toast.makeText(this, R.string.insert_successful, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.insert_failed, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            Toast.makeText(this, R.string.insert_failed, Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
            nameEdit.setText("");
            descriptionEdit.setText("");
            dateEdit.setText("");
            durationEdit.setText("");
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            String myFormat = "dd.MM.yyyy.";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            dateEdit.setText(sdf.format(date.getTimeInMillis()));
        }
    };

}
