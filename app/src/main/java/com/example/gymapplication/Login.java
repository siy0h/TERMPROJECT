package com.example.gymapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    EditText username, password;
    Button signup, login;
    DBHelper DB;
    Boolean checkUserPass;

    CheckBox memberCheck;
    CheckBox instructorCheck;
    CheckBox adminCheck;
    String table = "";

    String user = "";
    String pass = "";
    Intent intent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.signUpButton);
        login = (Button) findViewById(R.id.loginButton);
        memberCheck = (CheckBox)findViewById(R.id.memberBox);
        adminCheck = (CheckBox)findViewById(R.id.adminBox);
        instructorCheck = (CheckBox)findViewById(R.id.instructorBox);
        DB = new DBHelper(this);
    }

    public void loginEvent(View v){
        user = username.getText().toString();
        pass = password.getText().toString();

        if (user.length() < 3 || pass.length() < 3)
            Toast.makeText(Login.this, "Enter user and pass of length more than 3.", Toast.LENGTH_SHORT).show();
        else if (!memberCheck.isChecked() && !instructorCheck.isChecked() && !adminCheck.isChecked()){
            Toast.makeText(Login.this, "Select an account type", Toast.LENGTH_SHORT).show();
        }
        else {
            if (memberCheck.isChecked())
                table = "member";
            else if (instructorCheck.isChecked())
                table = "instructor";
            else
                table = "admin";

            checkUserPass = DB.checkUsernamePassword(user, pass, table);
            if(checkUserPass) {
                if (memberCheck.isChecked())
                    intent = new Intent(v.getContext(), MemberActivity.class);
                else if (instructorCheck.isChecked())
                    intent = new Intent(v.getContext(), InstructorActivity.class);
                else
                    intent = new Intent(v.getContext(), AdminActivity.class);
                Toast.makeText(Login.this, "Welcome " + user + "! you are logged in as " + table, Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }else
                Toast.makeText(Login.this, "Incorrect login in formation. Please Try again", Toast.LENGTH_SHORT).show();
        }

    }
    public void signUpEvent(View v){
        intent = new Intent(v.getContext(), MainActivity.class);
        startActivity(intent);
    }

    public void memberClicked(View v) {
        //code to check if this checkbox is checked!
        adminCheck.setChecked(false);
        instructorCheck.setChecked(false);
    }

    public void instructorClicked(View v) {
        //code to check if this checkbox is checked!
        adminCheck.setChecked(false);
        memberCheck.setChecked(false);

    }

    public void adminClicked(View v) {
        //code to check if this checkbox is checked!
        instructorCheck.setChecked(false);
        memberCheck.setChecked(false);
    }
}