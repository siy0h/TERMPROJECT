package com.example.gymapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

        //UserAccount admin;
        Button btn2, btn3, back;


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin);

            btn2 = findViewById(R.id.searchClass);
            btn3 = findViewById(R.id.addClass);
            back = findViewById(R.id.backBTN);

            btn2.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), EditClassAdmin.class);
                    startActivity(intent);

                }

            });

            btn3.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), CreateClassAdmin.class);
                    startActivity(intent);

                }

            });

            back.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            });

        }
    }
