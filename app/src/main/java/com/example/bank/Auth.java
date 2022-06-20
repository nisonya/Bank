package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bank.registr.Register;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Auth extends AppCompatActivity {

    private TextView loginTV;
    private TextView passwordTV;
    private String login;
    private String password;
    private String client_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Runtime.getRuntime().totalMemory();
        setContentView(R.layout.activity_auth);
        loginTV = findViewById(R.id.login_txt);
        passwordTV = findViewById(R.id.pass_txt);
        password = "1234";
        System.out.println(password.hashCode());

    }

    public void Auth(View view) {
        System.out.println("push");
        password = passwordTV.getText().toString();
        login = loginTV.getText().toString();
        if(password.equals("")&&login.equals("")){
            Toast.makeText(getApplicationContext(), "Заполните все поля",
                    Toast.LENGTH_SHORT).show();
        }
        if(password.equals("")&&(!(login.equals("")))){
            Toast.makeText(getApplicationContext(), "Заполните поле пароля",
                    Toast.LENGTH_SHORT).show();
        }
        if((!(password.equals(""))&&login.equals(""))){
            Toast.makeText(getApplicationContext(), "Заполните поле логина",
                    Toast.LENGTH_SHORT).show();
        }
        if(!(password.equals(""))&&(!(login.equals("")))){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:5000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API jsonReg = retrofit.create(API.class);
            Call<List<Register>> callReg = jsonReg.getRegist(login);
            callReg.enqueue(new Callback<List<Register>>() {
                @Override
                public void onResponse(Call<List<Register>> callReg, Response<List<Register>> response) {
                    if (!response.isSuccessful()) {
                        System.out.println(response.code());
                        return;
                    }
                    List<Register> posts = response.body();
                    for (Register api : posts) {
                        System.out.println("Вход пользователя");
                        String hashPass = String.valueOf(password.hashCode());
                        if(hashPass.equals(api.getPass())){
                            Intent intentmain = new Intent(Auth.this, MainActivity2.class);
                            intentmain.putExtra("id", api.getId_individual());
                            startActivity(intentmain);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Неверный пароль",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<Register>> callReg, Throwable t) {
                    System.out.println(t.getMessage());

                }
            });
        }

    }

    public void goToRegistr(View view) {
        Intent newintent = new Intent(this, registration.class);
        startActivity(newintent);
    }
}