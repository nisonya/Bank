package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class New_card extends AppCompatActivity {

    private String id_indnvidual;
    private String first_name;
    private String last_name;
    private List<String> ClientAccsList = new ArrayList<>();
    private int type_card=1;
    private String id_acc="1";
    private String number_acc="";
    private int payment_system=1;
    static List<Character> NUMBERS_WITHOUT_ZERO =  Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9');
    static List<Character> NUMBERS_WITH_ZERO =  Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    private static final SecureRandom random = new SecureRandom();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        Bundle arguments = getIntent().getExtras();
        id_indnvidual = arguments.get("id").toString();
        first_name = arguments.get("first").toString();
        last_name = arguments.get("last").toString();
        HashMap<String, String> map = new HashMap<>();


    }

    public void back(View view) {
                this.finish();
    }

    public void make_new_card(View view) {//обавление новой карты

        //генерация данных для заполнения
        String card_number = String.valueOf(getNumber(16));
        String cvc = String.valueOf(getNumber(3));
        //обработка даты и создание срока истечения карты
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("YYYY-MM-dd");
        String release_date = formatForDateNow.format(dateNow);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);
        calendar.add(Calendar.YEAR, 3);
        String expriration_date = formatForDateNow.format(calendar.getTime());
        //создание транслита имени и фамилии владельца
        Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
        String first = toLatinTrans.transliterate(first_name);
        String last = toLatinTrans.transliterate(last_name);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        API json = retrofit.create(API.class);
        //Заполнение модели
        CardOnPost cardOnPost = new CardOnPost(Integer.valueOf(id_indnvidual),card_number, expriration_date, release_date, first, last, cvc,1, payment_system, type_card);
        Call<Results> call = json.createCard(cardOnPost);
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Results results = response.body();
                System.out.println(response.isSuccessful());
                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                    Toast toast1 = Toast.makeText(New_card.this, "Ошибка", Toast.LENGTH_SHORT);
                    toast1.show();
                    return;
                } else {
                    System.out.println(response.code());
                    Toast toast = Toast.makeText(New_card.this, "Успешно", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                String cont = t.getMessage();
                System.out.println(cont);
            }
        });
    }

    public static String getNumber(int length) { //генерация числа с заданной длиной
        StringBuilder res = new StringBuilder(length);
        res.append(NUMBERS_WITHOUT_ZERO.get(random.nextInt(NUMBERS_WITHOUT_ZERO.size())));
        for (int i = 2; i <= length; i++)
            res.append(NUMBERS_WITH_ZERO.get(random.nextInt(NUMBERS_WITH_ZERO.size())));
        return res.toString();
    }

    public void changeCardType(View view) {
        switch (view.getId()) {
            case R.id.radioButton_credit_card:
                type_card = 1;
                System.out.println("КК");
                break;
            case R.id.radioButton_debit:
                type_card = 2;
                System.out.println("ДК");
                break;
            case R.id.radioButton_debit_molo:
                type_card = 3;

                System.out.println("ДМК");
                break;
        }
    }

    public void changePaymentSystem(View view) {
        switch (view.getId()) {
            case R.id.radioButton_visa:
                payment_system = 1;

                System.out.println("в");
                break;
            case R.id.radioButton_mastercard:
                payment_system = 2;

                System.out.println("мК");
                break;
            case R.id.radioButton_debit_mir:
                payment_system = 3;
                System.out.println("М");
                break;
        }
    }
}