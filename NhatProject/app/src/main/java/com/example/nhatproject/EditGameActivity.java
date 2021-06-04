package com.example.nhatproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Random;

public class EditGameActivity extends AppCompatActivity {

    private EditText homeTeam, awayTeam, date, time;
    private Button update, delete, cancel;
    private DatabaseReference reference;
    private int mDay,mMonth,mYear,mHour,mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        homeTeam = findViewById(R.id.hometeam);
        awayTeam = findViewById(R.id.awayteam);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditGameActivity.this, HomeViewActivity.class);
                startActivity(intent);
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        date.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                dialog.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mHour = calendar.get(Calendar.HOUR);
                mMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (minute < 10) {
                            time.setText(hourOfDay + ":0" + minute);
                        } else {
                            time.setText(hourOfDay + ":" + minute);
                        }
                    }
                }, mHour, mMinute, false);
                dialog.show();
            }
        });
        final Intent intent=getIntent();
        if(intent.getSerializableExtra("game") != null){
            FootballGame footballGame = (FootballGame)intent.getSerializableExtra("game");
            homeTeam.setText(footballGame.getHomeTeam());
            awayTeam.setText(footballGame.getAwayTeam());
            date.setText(footballGame.getDate());
            time.setText(footballGame.getTime());
            reference = FirebaseDatabase.getInstance().getReference().child("football").
                    child("game" + footballGame.getKey());
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FootballGame fg = new FootballGame();
                    fg.setHomeTeam(homeTeam.getText().toString());
                    fg.setAwayTeam(awayTeam.getText().toString());
                    fg.setDate(date.getText().toString());
                    fg.setTime(time.getText().toString());
                    fg.setKey(footballGame.getKey());
//                    fg.setKey(fg.getKey());
                    reference.setValue(fg).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),
                                    "Cập nhật thành công.",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "Cập nhật không thành công.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),
                                    "Xoá thành công.",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "Xoá không thành công.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}