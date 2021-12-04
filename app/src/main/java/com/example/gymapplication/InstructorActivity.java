package com.example.gymapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InstructorActivity extends AppCompatActivity{

    Button btn2, btn3, btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        btn = findViewById(R.id.editInstructor);
        btn2 = findViewById(R.id.instructorAdd);
        btn3 = findViewById((R.id.searchInstructor));


        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), EditClassInstructor.class);
                startActivity(intent);
            }

        });

        btn3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SearchInstructor.class);
                startActivity(intent);
            }

        });

        btn2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CreateClassInstructor.class);
                startActivity(intent);

            }

        });
    }
}
