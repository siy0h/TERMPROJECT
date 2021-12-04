package com.example.gymapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button signup, login;
    DBHelper DB;
    Boolean checkUser;
    Boolean insert;

    CheckBox memberCheck;
    CheckBox instructorCheck;
    String table = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.signUpButton);
        login = (Button) findViewById(R.id.loginButton);
        instructorCheck = (CheckBox)findViewById(R.id.instructorBox);
        memberCheck = (CheckBox)findViewById(R.id.memberBox);
        DB = new DBHelper(this);

    }

    public void signUpEvent(View v) {
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (user.length() < 3 || pass.length() < 3) {
            Toast.makeText(MainActivity.this, "Enter user and pass of length more than 3.", Toast.LENGTH_SHORT).show();

        }
        else if (!memberCheck.isChecked() && !instructorCheck.isChecked()){
            Toast.makeText(MainActivity.this, "Select an account type", Toast.LENGTH_SHORT).show();
        }
        else {
            if (instructorCheck.isChecked())
                table = "instructor";
            else
                table = "member";
            checkUser = DB.checkUsername(user, table);
            if (!checkUser) {
                insert = DB.insertData(user, pass, table);
                Toast.makeText(MainActivity.this, "Account successfully created. You may now login", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(MainActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();

        }
    }

    public void memberClicked(View v) {
        //code to check if this checkbox is checked!
        instructorCheck.setChecked(false);
    }
    public void instructorClicked(View v) {
        //code to check if this checkbox is checked!
        memberCheck.setChecked(false);
    }
    public void loginEvent(View v){

        Intent loginIntent = new Intent(v.getContext(), Login.class);
        startActivity(loginIntent);
    }
}