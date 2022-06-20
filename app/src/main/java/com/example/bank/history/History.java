package com.example.bank.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bank.API;
import com.example.bank.Extract;
import com.example.bank.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class History extends AppCompatActivity {

    ArrayList<HistoryItem> mHistory = new ArrayList<>();
    ArrayList<HistoryItem> mHistoryTrans = new ArrayList<>();
    ArrayList<HistoryItem> mHistoryPay = new ArrayList<>();
    private String id_client="1";
    private List<String> TypeList = new ArrayList<>();
    HashMap<String, String> changes_dic =
            new HashMap<>();
    Integer position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        CircularProgressIndicator mCircularProgressIndicator = (CircularProgressIndicator) findViewById(R.id.progress_circular);
        Bundle arguments = getIntent().getExtras();
        id_client = arguments.get("id").toString();
        TextView remainder = (TextView) findViewById(R.id.trans_mod);
        TextView balance_after = (TextView) findViewById(R.id.trans_amount);
        TextView trans_date = (TextView) findViewById(R.id.trans_date);
        TypeList.add("Все типы");
        TypeList.add("Перевод");
        TypeList.add("Оплата");
        Chip chip1 = (Chip) findViewById(R.id.chip_1);
        Chip chip2 = (Chip) findViewById(R.id.chip_2);

        // определяем слушателя нажатия элемента в списке
        HistoryAdapter.OnHisClickListener onHisClickListener  = new HistoryAdapter.OnHisClickListener() {
            @Override
            public void onHisClick(HistoryItem historyItem, int position) {
                Intent intent = new Intent(History.this, Extract.class);
                intent.putExtra("id", historyItem.getId_change());
                startActivity(intent);
                Toast.makeText(getApplicationContext(), String.valueOf(historyItem.getDiff()),
                        Toast.LENGTH_SHORT).show();
            }
        };
        //Spinner spinner = (Spinner) findViewById(R.id.spinner_history);
        ArrayAdapter<String> clientsCardsListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, TypeList);
        clientsCardsListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(clientsCardsListAdapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecyclerView.LayoutManager LLmm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RecyclerView rvHistory = (RecyclerView) findViewById(R.id.rv_history);
        rvHistory.setLayoutManager(LLmm);

        API jsonHistory = retrofit.create(API.class);
        Call<List<HistoryItem>> call = jsonHistory.getHistory(id_client);
        call.enqueue(new Callback<List<HistoryItem>>() {
            @Override
            public void onResponse(Call<List<HistoryItem>> call, Response<List<HistoryItem>> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                    return;
                }
                mCircularProgressIndicator.hide();
                List<HistoryItem> posts = response.body();

                for (HistoryItem api : posts) {
                    String date =api.getDate_of_change().substring(0,10);
                    HistoryItem dep = new HistoryItem(api.getId_change(), api.getDiff(),api.getBalance_after(),date, api.getTrans_type());

                    if(api.getTrans_type()==1){
                        mHistoryTrans.add(dep);
                    }
                    if(api.getTrans_type()==2){
                        mHistoryPay.add(dep);
                    }
                    HistoryAdapter adapterHis = new HistoryAdapter(mHistory, onHisClickListener);
                    rvHistory.setAdapter(adapterHis);
                    mHistory.add(dep);

                }
            }
            @Override
            public void onFailure(Call<List<HistoryItem>> callDep, Throwable t) {
                System.out.println(t.getMessage());

            }
        });


        //фильтрация по типу
        chip1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        History.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet_1,
                                (LinearLayout)findViewById(R.id.bottomSheetConteiner1)
                        );
                bottomSheetView.findViewById(R.id.type_operation_1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HistoryAdapter adapterHis = new HistoryAdapter(mHistoryTrans, onHisClickListener);
                        rvHistory.setAdapter(adapterHis);
                        bottomSheetDialog.dismiss();
                        chip1.setChecked(false);
                    }
                });
                bottomSheetView.findViewById(R.id.type_operation_2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HistoryAdapter adapterHis = new HistoryAdapter(mHistoryPay, onHisClickListener);
                        rvHistory.setAdapter(adapterHis);
                        bottomSheetDialog.dismiss();
                        chip1.setChecked(false);
                    }
                });
                bottomSheetView.findViewById(R.id.type_operation_3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HistoryAdapter adapterHis = new HistoryAdapter(mHistory, onHisClickListener);
                        rvHistory.setAdapter(adapterHis);
                        bottomSheetDialog.dismiss();
                        chip1.setChecked(false);
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        //фильтрация по периоду
        chip2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        History.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet_2,
                                (LinearLayout)findViewById(R.id.bottomSheetConteiner1)
                        );
                bottomSheetView.findViewById(R.id.period_1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//неделя
                        bottomSheetDialog.dismiss();
                        chip2.setChecked(false);
                        period(1);
                    }
                });
                bottomSheetView.findViewById(R.id.period_2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//месяц
                        bottomSheetDialog.dismiss();
                        chip2.setChecked(false);
                        period(2);
                    }
                });
                bottomSheetView.findViewById(R.id.period_3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//год
                        bottomSheetDialog.dismiss();
                        chip2.setChecked(false);
                        period(3);
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

    }
    private void period(int type){

        CircularProgressIndicator mCircularProgressIndicator = (CircularProgressIndicator) findViewById(R.id.progress_circular);
        mCircularProgressIndicator.show();
        Date  dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        String last_date = formatForDateNow.format(dateNow);
        String first_date = last_date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);
        if(type==1){
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            first_date = formatForDateNow.format(calendar.getTime());
        }
        if(type==2){
            calendar.add(Calendar.MONTH, -1);
            first_date = formatForDateNow.format(calendar.getTime());
        }
        if(type==3){
            calendar.add(Calendar.YEAR, -1);
            first_date = formatForDateNow.format(calendar.getTime());
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecyclerView.LayoutManager LLmm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RecyclerView rvHistory = (RecyclerView) findViewById(R.id.rv_history);
        rvHistory.setLayoutManager(LLmm);
        // определяем слушателя нажатия элемента в списке
        HistoryAdapter.OnHisClickListener onHisClickListener  = new HistoryAdapter.OnHisClickListener() {
            @Override
            public void onHisClick(HistoryItem historyItem, int position) {
                Intent intent = new Intent(History.this, Extract.class);
                intent.putExtra("id", historyItem.getId_change());
                startActivity(intent);
                Toast.makeText(getApplicationContext(), String.valueOf(historyItem.getDiff()),
                        Toast.LENGTH_SHORT).show();
            }
        };
        mHistory.clear();
        mHistoryPay.clear();
        mHistoryTrans.clear();
        HistoryAdapter adapterHis = new HistoryAdapter(mHistory, onHisClickListener);
        rvHistory.setAdapter(adapterHis);

        API json = retrofit.create(API.class);
        HistoryPeriod historyPeriod= new HistoryPeriod(id_client, first_date, last_date);
        Call<List<HistoryItem>> call = json.getHistorybyPeriod(historyPeriod);
        call.enqueue(new Callback<List<HistoryItem>>() {
            @Override
            public void onResponse(Call<List<HistoryItem>> call, Response<List<HistoryItem>> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                    return;
                }
                mCircularProgressIndicator.hide();
                List<HistoryItem> posts = response.body();
                for (HistoryItem api : posts) {
                    // определяем слушателя нажатия элемента в списке

                    String date =api.getDate_of_change().substring(0,10);
                    HistoryItem dep = new HistoryItem(api.getId_change(), api.getDiff(),api.getBalance_after(),date, api.getTrans_type());
                    if(api.getTrans_type()==1){
                        mHistoryTrans.add(dep);
                    }
                    if(api.getTrans_type()==2){
                        mHistoryPay.add(dep);
                    }

                    HistoryAdapter adapterHis = new HistoryAdapter(mHistory, onHisClickListener);
                    rvHistory.setAdapter(adapterHis);
                    mHistory.add(dep);

                }
            }
            @Override
            public void onFailure(Call<List<HistoryItem>> callDep, Throwable t) {
                System.out.println(t.getMessage());

            }
        });


    }

    public void goToMain(View view) {
        this.finish();
    }
}
