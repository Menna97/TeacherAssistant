package com.example.teacherassistant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
public class start_survey extends AppCompatActivity {

    int selectedCount = 0;
    boolean [] isSelected ;
    int no_of_submissions =0;
    ListView studentsListview;
    //
    //
    // String[] studentNames;

    //Bitmap [] studentPictures;
    Button submit;

    //ArrayList<String> leftOutStudents;

    ArrayList<String> selectedStudents=new ArrayList<String>();

    ArrayList<String> studentsList = new ArrayList<String>();

    int [] numberOfTimesSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_survey);
        Intent intent2 = getIntent();
        studentsListview = findViewById(R.id.listView_start_survey);
        submit = findViewById(R.id.submit_btn);
        loadSelectedNames();
        loadNamesFromClass();
     //   Log.e("here",">>"+studentsList.get(1));
        isSelected = new boolean[studentsList.size()];
        numberOfTimesSelected = new int [studentsList.size()];

        for(int i=0;i<isSelected.length;i++){
            numberOfTimesSelected[i]=0;
            isSelected[i]=false;
        }

        final MyAdapter2 myAdapter2 = new MyAdapter2(this,studentsList);
        studentsListview.setAdapter(myAdapter2);

        studentsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String program=arrayAdapter.getItem(position);
                if(isSelected[i]==false) {
                    if (selectedCount < 2) {
                        view.setBackgroundColor(getResources().getColor(R.color.selectedColour));
                        isSelected[i] = true;
                        selectedCount++;
                    }
                }
                else{
                    view.setBackgroundColor(Color.TRANSPARENT);
                    isSelected[i] = false;
                    selectedCount--;
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNoOfSubmissions();
                if(selectedItems()) {

                    if (no_of_submissions < studentsList.size()) {
                        for (int i = 0; i < isSelected.length; i++) {
                            if (isSelected[i]) {
                                //numberOfTimesSelected[i]++;
                                selectedStudents.add(studentsList.get(i));
                                saveSelectedNames();
                            }
                        }
                        no_of_submissions++;
                        saveNoOfSubmissions();
                        Intent intent = new Intent(getApplicationContext(), start_survey.class);
                        finish();
                        startActivity(intent);
                    } else {
                        no_of_submissions = 0;
                        saveNoOfSubmissions();
                        submit.setClickable(false);
                        Toast.makeText(getBaseContext(), "You finished the survey", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getBaseContext(), "You must select 2 names", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void loadNamesFromClass(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("name_list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        studentsList= gson.fromJson(json, type);

        if (studentsList == null) {
            studentsList = new ArrayList<String>();
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

    private void saveNoOfSubmissions(){
        SharedPreferences sp = getSharedPreferences("my_pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("no_of_sub", no_of_submissions);
        editor.apply();
    }
    private void loadNoOfSubmissions(){
        SharedPreferences sp = getSharedPreferences("my_pref",Activity.MODE_PRIVATE);
        no_of_submissions = sp.getInt("no_of_sub",0);
    }

    private boolean selectedItems(){
        int count = 0;
        for(int i=0;i<isSelected.length;i++){
            if(isSelected[i]==true)
                count++;
        }

        if(count==2){
            return true;
        }
        else{
            return false;
        }

    }
}