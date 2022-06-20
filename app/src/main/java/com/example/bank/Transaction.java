package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bank.valute.CardOnUodate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transaction extends AppCompatActivity {

    private String id_client, id_card, id_getter_card;
    private double balance_value, balance_getter;
    private EditText amountET;
    private List<Cards> ClientsCards = new ArrayList<>();
    private List<String> ClientCardsList = new ArrayList<>();
    private List<String> GetterCardsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Bundle arguments = getIntent().getExtras();
        id_client = arguments.get("id").toString();
        TextView balance = (TextView) findViewById(R.id.balance_txt);
        amountET = (EditText) findViewById(R.id.amount);
        HashMap<String, String> map = new HashMap<>(); //ля хранения номера и id карты
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCards);
        ArrayAdapter<String> clientsCardsListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ClientCardsList);
        clientsCardsListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Retrofit retrofitcard = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Получение карт клиента
        API jsonCards = retrofitcard.create(API.class);
        Call<List<Cards>> callCards = jsonCards.getCards(id_client);
        callCards.enqueue(new Callback<List<Cards>>() {
            @Override
            public void onResponse(Call<List<Cards>> callCards, retrofit2.Response<List<Cards>> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.code());
                    return;
                }
                List<Cards> posts = response.body();
                int i=0;
                for (Cards api: posts){
                    if(i==0) {
                        id_card = String.valueOf(api.getId_card());
                        balance.setText("Баланс: "+api.getBalance());
                        balance_value = api.getBalance();
                    }
                    System.out.println();
                    String number = "************"+api.getCard_number().substring(12,16);
                    ClientsCards.add(new Cards(number,api.getBalance(),api.getId_card_type(), api.getId_card(), api.getId_payment_system()));
                    ClientCardsList.add(number);
                    map.put(String.valueOf(api.getId_card()),number);
                }
                spinner.setAdapter(clientsCardsListAdapter);
            }
            @Override
            public void onFailure(Call<List<Cards>> callCards, Throwable t) {
                System.out.println(t.getMessage());
            }

        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<HashMap.Entry> entries =
                        new ArrayList<>(map.entrySet());
                for (HashMap.Entry entry : entries) {
                    if(entry.getValue().equals(parent.getSelectedItem().toString())){
                        id_card = entry.getKey().toString();
                        System.out.println(id_card);
                    }
                }
                // Получение карты по id
                API jsonCards = retrofitcard.create(API.class);
                Call<List<Cards>> callCard = jsonCards.getCard(id_card);
                callCard.enqueue(new Callback<List<Cards>>() {
                    @Override
                    public void onResponse(Call<List<Cards>> callCards, retrofit2.Response<List<Cards>> response) {
                        if(!response.isSuccessful()){
                            System.out.println(response.code());
                            return;
                        }
                        List<Cards> posts = response.body();
                        for (Cards api: posts){
                            balance.setText("Баланс: "+api.getBalance());
                            balance_value = api.getBalance();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Cards>> callCards, Throwable t) {
                        System.out.println(t.getMessage());
                    }

                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void Main(View view) {
        this.finish();
    }

    public void makeTransition(View view) {
        try {
        Double d1 = Double.parseDouble(amountET.getText().toString());
        System.out.println(d1);


        Float amount = Float.valueOf(amountET.getText().toString());
        if(amount>balance_value){
            Toast toast = Toast.makeText(this, "Недостаточно средств!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            EditText getter_card = (EditText) findViewById(R.id.getter_card);
            String getter_card_str = getter_card.getText().toString();
            if (getter_card_str.length() != 16) {
                Toast toast = Toast.makeText(this, "Некорректный номер карты получателя", Toast.LENGTH_SHORT);
                toast.show();
            } else {

                Retrofit retrofitcard = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:5000/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                //проверка существования карты
                // Обновление баланса карты
                API jsonCard = retrofitcard.create(API.class);
                Call<List<Cards>> callCard = jsonCard.getNumber(getter_card_str);
                callCard.enqueue(new Callback<List<Cards>>() {
                    @Override
                    public void onResponse(Call<List<Cards>> callCards, retrofit2.Response<List<Cards>> response) {
                        if (!response.isSuccessful()) {
                            System.out.println(response.code());
                            Toast toast = Toast.makeText(Transaction.this, "Ошибка", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }
                        List<Cards> posts = response.body();
                        if(posts.isEmpty()){
                            Toast toast1 = Toast.makeText(Transaction.this, "Данной карты не существует", Toast.LENGTH_SHORT);
                            toast1.show();
                        }
                        else {
                            for (Cards api : posts) {
                                id_getter_card = String.valueOf(api.getId_card());
                                balance_getter = api.getBalance();
                                System.out.println(api.getId_card());
                                System.out.println(id_getter_card);
                            }


                            // Обновление баланса карты отправителя
                            API jsonCards = retrofitcard.create(API.class);
                            double new_balance = balance_value - amount;
                            CardOnUodate cardOnUodate = new CardOnUodate(new_balance, id_card);
                            Call<Results> callCardss = jsonCards.updateBalance(cardOnUodate);
                            callCardss.enqueue(new Callback<Results>() {
                                @Override
                                public void onResponse(Call<Results> callCards, retrofit2.Response<Results> response) {
                                    if (!response.isSuccessful()) {
                                        System.out.println(response.code());
                                        Toast toast = Toast.makeText(Transaction.this, "Ошибка", Toast.LENGTH_SHORT);
                                        toast.show();
                                        return;
                                    }
                                    Toast toast = Toast.makeText(Transaction.this, "Успешно", Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                                @Override
                                public void onFailure(Call<Results> callCards, Throwable t) {
                                    System.out.println(t.getMessage());
                                }

                            });


                            // Обновление баланса карты получателя
                            API jsonCardsG = retrofitcard.create(API.class);
                            double new_balance_getter = balance_getter + amount;
                            CardOnUodate cardOnUodate1 = new CardOnUodate(new_balance_getter, id_getter_card);
                            Call<Results> callCardssG = jsonCardsG.updateBalance(cardOnUodate1);
                            callCardssG.enqueue(new Callback<Results>() {
                                @Override
                                public void onResponse(Call<Results> callCards, retrofit2.Response<Results> response) {
                                    if (!response.isSuccessful()) {
                                        System.out.println(response.code());
                                        Toast toast = Toast.makeText(Transaction.this, "Ошибка", Toast.LENGTH_SHORT);
                                        toast.show();
                                        return;
                                    }
                                    Toast toast = Toast.makeText(Transaction.this, "Успешно", Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                                @Override
                                public void onFailure(Call<Results> callCards, Throwable t) {
                                    System.out.println(t.getMessage());
                                }

                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Cards>> callCards, Throwable t) {
                        System.out.println(t.getMessage());
                    }

                });

            }
        }
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат строки!");
        }
    }
}