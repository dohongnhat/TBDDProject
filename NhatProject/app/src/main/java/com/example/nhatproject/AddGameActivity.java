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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddGameActivity extends AppCompatActivity {
    private EditText homeTeam, awayTeam, date, time;
    private Button add, cancel;
    private DatabaseReference reference;
    private Integer footballid = new Random().nextInt();
    private String keygame = Integer.toString(footballid);
    private int mDay,mMonth,mYear,mHour,mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        homeTeam = findViewById(R.id.hometeam);
        awayTeam = findViewById(R.id.awayteam);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        add = findViewById(R.id.add);
        cancel = findViewById(R.id.cancel);
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
                    date.setText(day+"/"+(month+1)+"/"+year);
                }
            },mYear,mMonth,mDay);
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
                    if(minute<10){
                        time.setText(hourOfDay+":0"+minute);
                    }else{
                        time.setText(hourOfDay+":"+minute);
                    }
                }
            },mHour,mMinute,false);
            dialog.show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().
                        child("football").
                        child("game" + footballid);
//                Map<String, Object> data= new HashMap<>();
//                data.put("home", homeTeam.getText().toString());
//                data.put("away", awayTeam.getText().toString());
//                data.put("date", date.getText().toString());
//                data.put("time", time.getText().toString());
//                data.put("key", keygame);
                FootballGame footballGame = new FootballGame();
                footballGame.setKey(keygame);
                footballGame.setHomeTeam(homeTeam.getText().toString());
                footballGame.setAwayTeam(awayTeam.getText().toString());
                footballGame.setDate(date.getText().toString());
                footballGame.setTime(time.getText().toString());
                reference.setValue(footballGame).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),
                                "Thêm thành công.",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "Thêm thất bại.",Toast.LENGTH_SHORT).show();
                    }
                });
//                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGameActivity.this, HomeViewActivity.class);
                startActivity(intent);
            }
        });
    }
}