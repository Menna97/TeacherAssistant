package com.example.teacherassistant;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class show_results extends AppCompatActivity {
    ListView show_results_list_view;
    ArrayList<String> result_names= new ArrayList<String>();
    MyAdapter2 myAdapter;
    ArrayList<String> selectedStudents=new ArrayList<String>();
    ArrayList<String> leftOutStudents=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        show_results_list_view= (ListView)findViewById(R.id.show_results_list);
        loadNames();
        loadSelectedNames();
        for(int i=0;i<result_names.size();i++){
            if(!selectedStudents.contains(result_names.get(i)))
                leftOutStudents.add(result_names.get(i));
        }

        if(leftOutStudents.isEmpty()){
            Toast.makeText(getBaseContext(), "All Students have been selected", Toast.LENGTH_LONG).show();

        }
        else if(leftOutStudents.size()==result_names.size()){
            Toast.makeText(getBaseContext(), "Please start the survey first", Toast.LENGTH_LONG).show();
        }
        else{
            myAdapter = new MyAdapter2(this,leftOutStudents);
            show_results_list_view.setAdapter(myAdapter);
        }

        selectedStudents.clear();
        saveSelectedNames();

    }

    private void loadNames(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("name_list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        result_names = gson.fromJson(json, type);

        if (result_names == null) {
            result_names = new ArrayList<String>();
        }
    }

    private void loadSelectedNames(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences2", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("selected_students_list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        selectedStudents = gson.fromJson(json, type);

        if (selectedStudents == null) {
            selectedStudents = new ArrayList<String>();
        }
    }

    private void saveSelectedNames(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(selectedStudents);
        editor.putString("selected_students_list", json);
        editor.apply();
    }

}
