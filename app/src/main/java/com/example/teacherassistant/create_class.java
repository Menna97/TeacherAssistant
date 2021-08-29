package com.example.teacherassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class create_class extends AppCompatActivity {
    String str;
    //Bitmap bmp;
   // String[] studentNames = new String[25];
    //Bitmap[] studentPictures = new Bitmap[25];
    //ArrayList<Bitmap> list2 = new ArrayList<Bitmap>(Arrays.asList(studentPictures));
    ListView studentsList_view;
    ArrayList<String>list1= new ArrayList<String>();
    MyAdapter2 myAdapter;
    boolean delete=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        Intent intent = getIntent();
        str = intent.getStringExtra("name");
        studentsList_view = (ListView) findViewById(R.id.create_class_list);
        //byte[] byteArray = getIntent().getByteArrayExtra("photo");
        Log.e("here",">>"+"create class");
        loadNames();
        if (list1 != null ) {
            /*bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            loadNames();
            list1.add(str);
            saveNames();
            Collections.reverse(list1);
            studentNames = list1.toArray(studentNames);
            Collections.reverse(list1);
            list2.add(bmp);
            Collections.reverse(list2);
            studentPictures = list2.toArray(studentPictures);
            Collections.reverse(list2);

            Log.e("here",">>"+str);
            Log.e("here",">>"+bmp);
            myAdapter = new MyAdapter(this,studentNames);
            studentsList_view = (ListView) findViewById(R.id.show_results_list);
            studentsList_view.setAdapter(myAdapter);*/
            //isSelected = new boolean[list1.size()];
            loadNames();
            //Collections.reverse(list1);
            if(str!=null && !str.equals(""))
            list1.add(str);
            //studentNames = list1.toArray(studentNames);
            //Collections.reverse(list1);
            saveNames();
            Log.e("here",">>"+str);
            myAdapter = new MyAdapter2(this,list1);
            studentsList_view.setAdapter(myAdapter);
           // Log.e("here",">>"+studentNames[24]);

        }
        studentsList_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(delete){
                    loadNames();
                    list1.remove(i);
                    saveNames();
                    delete=false;
                    Intent create_class_intent = new Intent(getApplicationContext(), create_class.class);
                    finish();
                    startActivity(create_class_intent);
                }

            }
        });
        Button delete_student_button = (Button) findViewById(R.id.delete_student_btn);
        delete_student_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                delete =true;
            }
        });
        Button delete_All_button = (Button) findViewById(R.id.delete_all);
        delete_All_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadNames();
                list1.clear();
                saveNames();
                Intent create_class_intent = new Intent(getApplicationContext(), create_class.class);
                finish();
                startActivity(create_class_intent);
            }
        });

        Button class_done_btn = (Button) findViewById(R.id.class_done_btn);
        class_done_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent main_activity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main_activity);
            }
        });
        Button add_student_button = (Button) findViewById(R.id.add_student_btn);
        add_student_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent add_student_intent = new Intent(getApplicationContext(), add_students.class);
                startActivity(add_student_intent);
                Log.e("here",">>"+"add_student");
            }
        });
    }
    private void saveNames(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list1);
        editor.putString("name_list", json);
        editor.apply();
    }

    private void loadNames(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("name_list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        list1 = gson.fromJson(json, type);

        if (list1 == null) {
            list1 = new ArrayList<String>();
        }
    }


}