package com.example.gymapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class EditClassAdmin extends AppCompatActivity {

    Button viewClasses, deleteClass, editClass, back;
    DBClass db3;
    Spinner typeDropDown, difficultyDropDown;
    EditText getClassId, getClassTitle, getClassDescription, getTime;
    TextInputEditText capacity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class_admin);

        //drop down list to select type of class
        typeDropDown = (Spinner) findViewById(R.id.classType_id3);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.classType));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeDropDown.setAdapter(typeAdapter);

        //drop down list for difficulty
        difficultyDropDown = (Spinner) findViewById(R.id.classDifficulty_id3);
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.difficultyType));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyDropDown.setAdapter(difficultyAdapter);

        capacity = findViewById(R.id.memEnterCap);
        viewClasses = findViewById(R.id.classView);
        back = findViewById(R.id.backEdit);
        deleteClass = findViewById(R.id.classDelete);
        editClass = findViewById(R.id.classAdd);
        getClassId = findViewById(R.id.getClassID);
        getClassTitle = findViewById(R.id.getClassTitle);
        getClassDescription = findViewById(R.id.getClassDescr);
        getTime = findViewById(R.id.editTime2);

        db3 = new DBClass(this);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            }

        });

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
                    buffer.append("Description : " + res.getString(3) + "\n");
                    buffer.append("Difficulty : " + res.getString(4) + "\n");
                    buffer.append("Capacity : " + res.getString(5) + "\n");
                    buffer.append("Date : " + res.getString(6) + "\n");
                    buffer.append("Time : " + res.getString(7) + "\n");
                    buffer.append("Instructor : " + res.getString(8) + "\n");
                    buffer.append("Day of week : " + res.getString(9) + "\n\n");

                }

                showMessage("Here are Your Entries", buffer.toString());

            }
        });

        deleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean deletedRows = db3.deleteData(getClassId.getText().toString(), "admin");

                if (deletedRows == true) {

                    Toast.makeText(EditClassAdmin.this, "Data Updated.", Toast.LENGTH_LONG).show();

                }   else {

                    Toast.makeText(EditClassAdmin.this,"Data not Updated", Toast.LENGTH_LONG).show();

                }

            }
        });

        editClass.setOnClickListener(new View.OnClickListener() {
            @Override
            //getClassId, getClassTitle
            public void onClick(View view) {
                try{
                    String classID = getClassId.getText().toString();
                    String type = typeDropDown.getSelectedItem().toString();
                    String diff = difficultyDropDown.getSelectedItem().toString();
                    String cap = capacity.getText().toString();
                    String time = getTime.getText().toString();
                    String classDescription = getClassDescription.getText().toString();
                    String classTitle = getClassTitle.getText().toString();

                    if (classTitle.isEmpty() && classDescription.isEmpty() && time.isEmpty()){
                        Toast.makeText(EditClassAdmin.this,"Both title and description fields cannot be empty", Toast.LENGTH_LONG).show();
                    }
                    else if(classID.isEmpty()){
                        Toast.makeText(EditClassAdmin.this,"Must input a class ID to update", Toast.LENGTH_LONG).show();
                    }
                    else if (!isInteger(cap) && cap.length() != 0){
                        Toast.makeText(EditClassAdmin.this,"Please enter an integer for 'capacity'", Toast.LENGTH_LONG).show();
                    }
                    //if capacity is an integer but is smaller than 1
                    else if (isInteger(cap) && Integer.parseInt(cap) < 1){
                        Toast.makeText(EditClassAdmin.this, "Class's capacity must be > 1", Toast.LENGTH_SHORT).show();
                    }
                    else if(!time.isEmpty()&&(Integer.parseInt(time) < 0 || Integer.parseInt(time) > 24)){
                        Toast.makeText(EditClassAdmin.this, "Invalid Time", Toast.LENGTH_SHORT).show();
                    }
                    //if description isn't empty and the class title isn't empty
                    if (!cap.isEmpty() && Integer.parseInt(cap) > 1){
                        db3.updateCapacity(classID, Integer.parseInt(cap));
                    }
                    if (!diff.isEmpty()) {
                        db3.updateDifficulty(classID, diff);
                    }
                    if(!type.isEmpty()) {
                        db3.updateType(classID, type);
                    }
                    if(!time.isEmpty() && (Integer.parseInt(time) > 0 && Integer.parseInt(time) < 24)){
                        db3.updateTime(classID, time);
                    }
                    if(!classTitle.isEmpty()){
                        db3.updateName(classID, classTitle);
                    }
                    if(!classDescription.isEmpty()){
                        db3.updateDescription(classID, classDescription);
                    }

                }
                catch(Exception e){
                    Toast.makeText(EditClassAdmin.this,"Exception occurred: " + e, Toast.LENGTH_LONG).show();
                }

                Toast.makeText(EditClassAdmin.this,"Updated Class!", Toast.LENGTH_LONG).show();

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
