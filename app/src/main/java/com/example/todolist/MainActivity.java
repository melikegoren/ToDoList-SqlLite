package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    ImageButton add_btn;
    EditText et_text;
    ListView task_lv;
    DBHelper dbHelper;
    ArrayAdapter taskArrayAdapter;

    private void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_text = findViewById(R.id.et_name);
        task_lv = findViewById(R.id.task_lv);
        add_btn = findViewById(R.id.add_btn);

        dbHelper = new DBHelper(MainActivity.this);
        showTasksOnListview(dbHelper);

        add_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               EnterTask enterTask;
                     try{
                        enterTask = new EnterTask(-1, et_text.getText().toString());
                        Toast.makeText(MainActivity.this, enterTask.toString(), Toast.LENGTH_SHORT).show();
                     }
                     catch(Exception e){ // if an error occurs
                        Toast.makeText(MainActivity.this, "Error creating task", Toast.LENGTH_SHORT).show();
                        enterTask = new EnterTask(-1, "error");
                     }
                     DBHelper dbHelper = new DBHelper(MainActivity.this);
                     boolean success = dbHelper.addOne(enterTask);
                     Toast.makeText(MainActivity.this, "Added:"+enterTask , Toast.LENGTH_SHORT).show();
                     dbHelper = new DBHelper(MainActivity.this);
                     showTasksOnListview(dbHelper);
                     et_text.getText().clear();
                     closeKeyboard();


           }
       });

        task_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EnterTask clickedTask = (EnterTask) adapterView.getItemAtPosition(i);
                dbHelper.deleteOne(clickedTask);
                showTasksOnListview(dbHelper);
                Toast.makeText(MainActivity.this, "Deleted:"+clickedTask, Toast.LENGTH_SHORT).show();
            }
        });{
        }
    }

    private void showTasksOnListview(DBHelper dbHelper2) {
        taskArrayAdapter = new ArrayAdapter<EnterTask>(MainActivity.this, R.layout.listview_row, dbHelper2.getEverything());
        task_lv.setAdapter(taskArrayAdapter);
    }

}