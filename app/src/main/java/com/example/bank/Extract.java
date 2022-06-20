package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Extract extends AppCompatActivity {

    private String id_change;
    private TextView fio;
    private TextView card_number;
    private TextView date;
    private TextView diff;
    private TextView balance;
    private TextView summ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);
        Bundle arguments = getIntent().getExtras();
        id_change = arguments.get("id").toString();
        System.out.println(id_change);
        fio = (TextView) findViewById(R.id.fio_extract);
        card_number = (TextView) findViewById(R.id.card_extract);
        date = (TextView) findViewById(R.id.date_extract);
        diff = (TextView) findViewById(R.id.diff_extract);
        balance = (TextView) findViewById(R.id.balance_extract);
        summ = (TextView) findViewById(R.id.summ_extract);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API json = retrofit.create(API.class);
        Call<List<ExtractItem>> call = json.getExtract(id_change);
        call.enqueue(new Callback<List<ExtractItem>>() {
            @Override
            public void onResponse(Call<List<ExtractItem>> call, Response<List<ExtractItem>> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                    return;
                }
                List<ExtractItem> posts = response.body();
                for (ExtractItem item: posts) {
                    String date_str = item.getDate_of_change().substring(0, 10);
                    String number_str = "************" + String.valueOf(item.getCard_number()).substring(11, 15);
                    fio.setText(item.getSurname() +" "+ item.getFirst_name()+" " + item.getLast_name());
                    date.setText(date_str);
                    diff.setText(String.valueOf(item.getDiff()));
                    balance.setText(String.valueOf(item.getBalance_after()));
                    card_number.setText(number_str);
                    System.out.println(date_str + "    " + number_str);
                    if(item.getDiff()>0){
                        summ.setText("Сумма пополнения:");
                    }
                    else summ.setText("Сумма списания:");
                }

            }

            @Override
            public void onFailure(Call<List<ExtractItem>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    public void goToBack(View view) {
        this.finish();
    }
}