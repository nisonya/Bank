package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bank.client.Client;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registration extends AppCompatActivity {
    private boolean regist = false;
    private EditText first_name, last_name, Surname, login, pass1, pass2, birth_day, phone,email;
    private String name_str, last_name_str, surname_str,phone_str, login_str, pass1_str, pass2_str, email_str, gender = "женский", birth_day_str;
    public int id_client = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        first_name = (EditText) findViewById(R.id.name_txt);
        last_name = (EditText) findViewById(R.id.patron);
        Surname =(EditText)  findViewById(R.id.lastName);
        login = (EditText) findViewById(R.id.login_txt);
        pass1 = (EditText) findViewById(R.id.pass1_txt);
        pass2 = (EditText) findViewById(R.id.pass2_txt);
        birth_day = (EditText) findViewById(R.id.birthDay);
        phone =(EditText)  findViewById(R.id.phone_txt);
        email = (EditText) findViewById(R.id.email_txt);
    }

    public void goToNext(View view) {
        if(!regist){
            name_str =Regist1.first_name.getText().toString();
            last_name_str =Regist1.last_name.getText().toString();
            surname_str =Regist1.Surname.getText().toString();
            phone_str =Regist1.phone.getText().toString();
            String BD =birth_day.getText().toString();
            System.out.println(birth_day_str);
            if( !(name_str.equals(""))&&
                    !(last_name_str.equals(""))&&
                    !(surname_str.equals(""))&&
                    !(phone_str.equals("")))
            {
                birth_day_str =  BD.substring(6,10)+"-"+BD.substring(3,5)+"-"+BD.substring(0,2);
                Regist2 Reg2 = new Regist2();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, Reg2);
                transaction.addToBackStack(null);
                transaction.commit();
                regist = true;
            }
            else {
                Toast toast = Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else {
            login_str =Regist2.login.getText().toString();
            pass1_str =Regist2.pass1.getText().toString();
            pass2_str =Regist2.pass2.getText().toString();
            email_str =Regist2.email.getText().toString();
            if( !(login_str.equals(""))&&
                    !(pass1_str.equals(""))&&
                    !(pass2_str.equals(""))&&
                    !(email_str.equals(""))) {
                Pattern pattern;
                Matcher matcher;
                String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{2,3})$";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(email_str);
                if (!matcher.matches()) {
                    Toast toast = Toast.makeText(this, "Неверный email!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if (login_str.length() < 6) {
                        Toast toast = Toast.makeText(this, "Слишком короткий логин!", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        if (!pass1_str.equals(pass2_str)) {
                            Toast toast = Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            //добавление в таблицу клиентов

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://10.0.2.2:5000/api/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            API json = retrofit.create(API.class);
                            //Заполнение модели
                            ClientOnPost clientOnPost = new ClientOnPost(name_str, surname_str, last_name_str, "+7", phone_str, birth_day_str, gender, email_str);
                            Call<Results> call = json.createClient(clientOnPost);
                            call.enqueue(new Callback<Results>() {
                                @Override
                                public void onResponse(Call<Results> call, Response<Results> response) {
                                    Results loginres = response.body();
                                    System.out.println(response.isSuccessful());
                                    if (!response.isSuccessful()) {
                                        System.out.println(response.code());
                                        Toast toast1 = Toast.makeText(registration.this, "Ошибка", Toast.LENGTH_SHORT);
                                        toast1.show();
                                        return;
                                    } else {
                                        System.out.println(response.code());
                                        Toast toast = Toast.makeText(registration.this, "Успешно", Toast.LENGTH_SHORT);
                                        toast.show();
                                        //получение id добавленного клиента

                                        API json = retrofit.create(API.class);
                                        Call<List<Client>> callid = json.getLastClient();
                                        callid.enqueue(new Callback<List<Client>>() {
                                            @Override
                                            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                                                if (!response.isSuccessful()) {
                                                    System.out.println(response.code());
                                                    Toast toast1 = Toast.makeText(registration.this, "Ошибка получения", Toast.LENGTH_SHORT);
                                                    toast1.show();
                                                    return;
                                                }
                                                List<Client> posts = response.body();
                                                for (Client api : posts) {
                                                    id_client = api.getId_individual();
                                                }
                                                //Добавление в таблицу регистрации

                                                API json = retrofit.create(API.class);
                                                //Заполнение модели
                                                RegistOnPost registOnPost = new RegistOnPost(id_client, login_str, pass1_str.hashCode());
                                                Call<Results> callReg = json.createRegist(registOnPost);
                                                callReg.enqueue(new Callback<Results>() {
                                                    @Override
                                                    public void onResponse(Call<Results> call, Response<Results> response) {
                                                        System.out.println(response.isSuccessful());
                                                        System.out.println("issucsesfullRegist");
                                                        if (!response.isSuccessful()) {
                                                            System.out.println(response.code());
                                                            Toast toast1 = Toast.makeText(registration.this, "Ошибка", Toast.LENGTH_SHORT);
                                                            toast1.show();
                                                            return;
                                                        } else {
                                                            System.out.println(response.code());
                                                            Toast toast = Toast.makeText(registration.this, "Успешно", Toast.LENGTH_SHORT);
                                                            toast.show();
                                                            Intent intent1 = new Intent(registration.this, MainActivity2.class);
                                                            intent1.putExtra("id", id_client);
                                                            startActivity(intent1);
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(Call<Results> call, Throwable t) {
                                                        String cont = t.getMessage();
                                                        System.out.println(cont);

                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure(Call<List<Client>> call, Throwable t) {
                                                String cont = t.getMessage();
                                                System.out.println(cont);
                                            }
                                        });
                                        return;
                                    }
                                }
                                @Override
                                public void onFailure(Call<Results> call, Throwable t) {
                                    String cont = t.getMessage();
                                    System.out.println(cont);
                                }
                            });
                            regist = false;
                        }
                    }
                }
            }
            else{
                    Toast toast = Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT);
                    toast.show();
                }
        }
    }

    public void goToAuth(View view) {
        if(regist){
            Regist1 Reg1 = new Regist1();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, Reg1);
            transaction.addToBackStack(null);
            transaction.commit();
            regist=false;
        }
        else{
            this.finish();
        }
    }

    public void changeGender(View view) {
        switch (view.getId()) {

            case R.id.radioButton_female:
                gender = "женский";
                System.out.println("Женский");
                break;

            case R.id.radioButton_male:
                gender = "мужской";
                System.out.println("Мужской");
                break;
        }
    }
}