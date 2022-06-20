package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler(); //управление потоками
        handler.postDelayed(new Runnable() { //Выполнить Runnable с задержкой
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Auth.class);
                startActivity(intent);
                finish(); //закрыть активность
            }
        }, 2000);


    }
    public void findbank(View view) {
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}