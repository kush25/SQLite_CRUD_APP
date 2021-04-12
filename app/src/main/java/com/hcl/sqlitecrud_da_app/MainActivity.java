package com.hcl.sqlitecrud_da_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txtname,txtusername,txtpassword;
    EditText editname,editusername,editpassword;
    Button insert_b,delete_b,view_b,update_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db dbobj = new db(MainActivity.this);
        SQLiteDatabase databs = dbobj.getReadableDatabase();

        txtname = findViewById(R.id.name_text);
        txtusername = findViewById(R.id.username_text);
        txtpassword  =findViewById(R.id.password_text);

        editname = findViewById(R.id.name_edit);
        editusername = findViewById(R.id.username_edit);
        editpassword = findViewById(R.id.password_edit);

        insert_b = findViewById(R.id.insert_btns);
        delete_b =findViewById(R.id.delete_btns);
        view_b = findViewById(R.id.view_btns);
        update_b = findViewById(R.id.update_btns);

        insert_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nm = editname.getText().toString();
                String usrnm = editusername.getText().toString();
                String pass = editpassword.getText().toString();

                if(nm.equals("") || usrnm.equals("") || pass.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter all the required fields", Toast.LENGTH_SHORT).show();
                }

                else if(pass.length()<=6){
                    Toast.makeText(MainActivity.this, "Password must be more than 6 characters", Toast.LENGTH_SHORT).show();
                }

                else{
                        Boolean insertsuccess = dbobj.insertData(nm,usrnm,pass);
                        if(insertsuccess == true){
                            Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Try inserting data again", Toast.LENGTH_SHORT).show();
                        }
                }

                editname.setText("");
                editusername.setText("");
                editpassword.setText("");


            }
        });

        view_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor viewdata = dbobj.getData();
                if(viewdata.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }

                else{
                    StringBuffer buffer = new StringBuffer();
                    while(viewdata.moveToNext()){
                        buffer.append("Name: "+viewdata.getString(1)+"\n");
                        buffer.append("Username: "+viewdata.getString(2)+"\n\n");

                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Sign Up Users Data");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }
            }
        });


        delete_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editusername.getText().toString();
                Boolean values = dbobj.deleteData(username);

                if(values == true){
                    Toast.makeText(MainActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "No Similar Data to Delete", Toast.LENGTH_SHORT).show();
                }
            }
        });


        update_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editname.getText().toString();
                String username = editusername.getText().toString();
                String password = editpassword.getText().toString();

                Boolean updtData = dbobj.updateData(name,username,password);
                if(updtData == true){
                    Toast.makeText(MainActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();
                }


                editname.setText("");
                editusername.setText("");
                editpassword.setText("");
            }
        });

    }
}