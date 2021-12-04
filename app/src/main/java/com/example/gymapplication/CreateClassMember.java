package com.example.gymapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class CreateClassMember extends AppCompatActivity  {

    Button viewClasses, enroll, unenroll;
    Button btn, btn1, btn2, back;
    DBClass db3;
    Spinner typeDropDown, difficultyDropDown, dayOfWeekDropDown, timeDropDown;
    EditText getClassId, getClassTitle, getClassDescription;
    TextInputEditText capacity;
    static String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class_member);

        //drop down list to select type of class
        typeDropDown = (Spinner) findViewById(R.id.type_id);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.classType));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeDropDown.setAdapter(typeAdapter);

        //drop down list for difficulty
        difficultyDropDown = (Spinner) findViewById(R.id.difficulty_id);
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.difficultyType));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyDropDown.setAdapter(difficultyAdapter);

        //drop down list for dayofweek
        dayOfWeekDropDown = (Spinner) findViewById(R.id.dow_id);
        ArrayAdapter<String> dayOfWeekAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dayOfWeek));
        dayOfWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOfWeekDropDown.setAdapter(dayOfWeekAdapter);

        //drop down list for time
        timeDropDown = (Spinner) findViewById(R.id.time_id);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.time));
        dayOfWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeDropDown.setAdapter(timeAdapter);

        capacity = findViewById(R.id.cap_id);
        viewClasses = findViewById(R.id.classView);
        back = findViewById(R.id.backEdit);
        getClassId = findViewById(R.id.class_id);
        getClassTitle = findViewById(R.id.title_id);
        btn = findViewById(R.id.chooseDate);
        btn1 = findViewById(R.id.classAdd);
        btn2 = findViewById(R.id.classDelete);

        db3 = new DBClass(this);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MemberActivity.class);
                startActivity(intent);
            }
        });

//        btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                showPickerDialog();
//            }
//            private void showPickerDialog(){
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        CreateClassMember.this,
//                        CreateClassMember.this,
//                        Calendar.getInstance().get(Calendar.YEAR),
//                        Calendar.getInstance().get(Calendar.MONTH),
//                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.show();
//            }
//        });

        viewClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor res = db3.getAllData();
                if (res.getCount() == 0) {

                    showMessage("Error", "Nothing Found");
                    return;

                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("ID : " + res.getString(0) + "\n");
                    buffer.append("Title : " + res.getString(1) + "\n");
                    buffer.append("Type : " + res.getString(2) + "\n");
                    buffer.append("Difficulty : " + res.getString(4) + "\n");
                    buffer.append("Capacity : " + res.getString(5) + "\n");
                    buffer.append("Date : " + res.getString(6) + "\n");
                    buffer.append("Time : " + res.getString(7) + "\n");
                    buffer.append("Instructor : " + res.getString(8) + "\n");
                    buffer.append("Day of week : " + res.getString(9) + "\n");

                    String stringOfMembers = res.getString(10);
                    String[] arrayOfMembers = stringOfMembers.split(",");
                    List<String> listMembers = new ArrayList<String>();
                    listMembers = Arrays.asList(arrayOfMembers);
                    int numMembers = -1;
                    if (listMembers.toString().equals("[]")){
                        numMembers = 0;
                    } else {
                        numMembers = listMembers.size();
                    }
                    buffer.append("Members Registered : " + listMembers.toString() + "\n");
                    buffer.append("\n");

                }

                showMessage("Scheduled Class Info", buffer.toString());
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try{
                    String classID = getClassId.getText().toString();
                    String cap = capacity.getText().toString();
                    String classTitle = getClassTitle.getText().toString();



                    if (classTitle.isEmpty() && cap.isEmpty()){
                        Toast.makeText(CreateClassMember.this,"Both title and capacity cannot be empty", Toast.LENGTH_LONG).show();
                    }
                    else if(classID.isEmpty()){
                        Toast.makeText(CreateClassMember.this,"Class ID Cannot be Empty", Toast.LENGTH_LONG).show();
                    }
                    else if (!isInteger(cap) && cap.length() != 0){
                        Toast.makeText(CreateClassMember.this,"Please enter an integer for 'capacity'", Toast.LENGTH_LONG).show();
                    }
                    else if (!isInteger(cap) && cap.length() != 0){
                        Toast.makeText(CreateClassMember.this,"Please enter using an Integer (#) for Capacity", Toast.LENGTH_LONG).show();
                    }

                    SharedPreferences currentUserSession = getApplicationContext().getSharedPreferences("currentUserSession", Context.MODE_PRIVATE);
                    String memberEmail = currentUserSession.getString("email", "");
                    int enrollStatus = db3.enrollMember(classID, memberEmail);
                    if (enrollStatus == 1){
                        Toast.makeText(CreateClassMember.this,"Successfully enrolled!", Toast.LENGTH_LONG).show();
                    }
                    if (enrollStatus == -1){
                        Toast.makeText(CreateClassMember.this,"You're already enrolled!", Toast.LENGTH_LONG).show();
                    }
                    if (enrollStatus == -2){
                        Toast.makeText(CreateClassMember.this,"Class is at full capacity!", Toast.LENGTH_LONG).show();
                    }

                }
                catch(Exception e){
                    Toast.makeText(CreateClassMember.this,"ERROR: " + e, Toast.LENGTH_LONG).show();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                try{
                    String classID = getClassId.getText().toString();
                    String cap = capacity.getText().toString();
                    String classTitle = getClassTitle.getText().toString();

                    if (classTitle.isEmpty() && cap.isEmpty()){
                        Toast.makeText(CreateClassMember.this,"Both title and capacity cannot be empty", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if(classID.isEmpty()){
                        Toast.makeText(CreateClassMember.this,"Class ID Cannot be Empty", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if (!isInteger(cap) && cap.length() != 0){
                        Toast.makeText(CreateClassMember.this,"Please enter an integer for 'capacity'", Toast.LENGTH_LONG).show();
                    }
                    else if (!isInteger(cap) && cap.length() != 0){
                        Toast.makeText(CreateClassMember.this,"Please enter using an Integer (#) for Capacity", Toast.LENGTH_LONG).show();
                    }

                    SharedPreferences currentUserSession = getApplicationContext().getSharedPreferences("currentUserSession", Context.MODE_PRIVATE);
                    String memberEmail = currentUserSession.getString("email", "");
                    int enrollStatus = db3.unenrollMember(classID, memberEmail);
                    if (enrollStatus == 1){
                        Toast.makeText(CreateClassMember.this,"Successfully unenrolled!", Toast.LENGTH_LONG).show();
                    }
                    if (enrollStatus == 0){
                        Toast.makeText(CreateClassMember.this,"Cannot unenroll!", Toast.LENGTH_LONG).show();
                    }
                    if (enrollStatus == -1){
                        Toast.makeText(CreateClassMember.this,"Cannot Unenroll Without being Enroll First!", Toast.LENGTH_LONG).show();
                    }

                }
                catch(Exception e){
                    Toast.makeText(CreateClassMember.this,"Error: " + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showMessage (String title, String Message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        }   catch (NumberFormatException e) {
            return false;
        }   catch (NullPointerException e) {
            return false;
        }
        return true;
    }

}
