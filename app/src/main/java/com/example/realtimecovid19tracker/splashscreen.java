package com.example.realtimecovid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Thread thread = new Thread(){
            // creating object of thread class
            // thread used to implement multiple functions at same time
            public  void run(){
                try {
                   sleep(4000);
                }
                catch (Exception e ){
                    e.printStackTrace(); // mother of all exceptions
                }
                finally {
                    Intent intent = new Intent(splashscreen.this,MainActivity.class); //object of intent , intent is used to go from one activity to another
                    startActivity(intent);
                }
            }
        };thread.start();

    }
}