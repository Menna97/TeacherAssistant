package com.example.teacherassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button create_class = (Button) findViewById(R.id.create_class_btn);
        Button start_survey = (Button) findViewById(R.id.start_survey_btn);
        Button show_results= (Button) findViewById(R.id.show_results_btn);
        create_class.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent create_class_act = new Intent(getApplicationContext(), create_class.class);
                startActivity(create_class_act);
            }
        });
        start_survey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent start_surv_act = new Intent(getApplicationContext(), start_survey.class);
                startActivity(start_surv_act);
            }
        });
        show_results.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent show_results_act = new Intent(getApplicationContext(), show_results.class);
                startActivity(show_results_act);
            }
        });
    }
}
