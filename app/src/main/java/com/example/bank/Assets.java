package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bank.typeCard.TypeCard;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Assets extends AppCompatActivity {

    ArrayList<Cards> mCards = new ArrayList<>();
    ArrayList<Accounts> mAccounts = new ArrayList<>();
    ArrayList<Deposits> mDeposits = new ArrayList<>();
    ArrayList<Credit> mCredits = new ArrayList<>();

    private TextView assets_name;
    private String id_client="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);
        Bundle arguments = getIntent().getExtras();
        String asset = arguments.get("type").toString();
        id_client = arguments.get("id").toString();
        System.out.println(asset);
        assets_name = findViewById(R.id.assets_name);
        assets_name.setText(asset);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecyclerView.LayoutManager LLmm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RecyclerView rvAssets = (RecyclerView) findViewById(R.id.rv_assets);
        rvAssets.setLayoutManager(LLmm);

        if (asset.equals("Карты")) {
            API jsonCards = retrofit.create(API.class);
            Call<List<Cards>> callCards = jsonCards.getCards(id_client);
            callCards.enqueue(new Callback<List<Cards>>() {
                @Override
                public void onResponse(Call<List<Cards>> callCards, Response<List<Cards>> response) {
                    if (!response.isSuccessful()) {
                        System.out.println(response.code());
                        return;
                    }
                    List<Cards> posts = response.body();
                    for (Cards api : posts) {
                        System.out.println();
                        String number = "************" + api.getCard_number().substring(11, 15);
                        Cards card = new Cards(number, api.getBalance(), api.getId_card_type(), api.getId_card(), api.getId_payment_system());
                        API jsonType = retrofit.create(API.class);
                        Call<List<TypeCard>> callType = jsonType.getCardType(String.valueOf(api.getId_payment_system()));
                        callType.enqueue(new Callback<List<TypeCard>>() {
                            @Override
                            public void onResponse(Call<List<TypeCard>> callType, Response<List<TypeCard>> response) {
                                if (!response.isSuccessful()) {
                                    System.out.println(response.code());
                                    return;
                                }
                                List<TypeCard> posts = response.body();
                                for (TypeCard api : posts) {
                                    card.setCard_type(api.getDescription());
                                    System.out.println(api.getDescription());
                                }
                                // определяем слушателя нажатия элемента в списке
                                CardsAdapter.OnCardClickListener cardClickListener = new CardsAdapter.OnCardClickListener() {
                                    @Override
                                    public void onCardClick(Cards conn, int position) {

                                        //нажатие
                                    }
                                };
                                CardsAdapter adapterCards = new CardsAdapter(mCards, cardClickListener, true);
                                rvAssets.setAdapter(adapterCards);

                            }

                            @Override
                            public void onFailure(Call<List<TypeCard>> callType, Throwable t) {
                                System.out.println(t.getMessage());

                            }
                        });
                        mCards.add(card);
                    }
                }

                @Override
                public void onFailure(Call<List<Cards>> callCards, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });

            // определяем слушателя нажатия элемента в списке
            CardsAdapter.OnCardClickListener cardClickListener = new CardsAdapter.OnCardClickListener() {
                @Override
                public void onCardClick(Cards conn, int position) {

                    //нажатие
                }
            };
        }
        if (asset.equals("Счета")) {
            API jsonAcc = retrofit.create(API.class);
            Call<List<Accounts>> callAcc = jsonAcc.getAcc(id_client);
            callAcc.enqueue(new Callback<List<Accounts>>() {
                @Override
                public void onResponse(Call<List<Accounts>> callCards, Response<List<Accounts>> response) {
                    if (!response.isSuccessful()) {
                        System.out.println(response.code());
                        return;
                    }
                    List<Accounts> posts = response.body();
                    for (Accounts api : posts) {
                        System.out.println("Счета");
                        // определяем слушателя нажатия элемента в списке
                        AccountsAdapter.OnAcClickListener accClickListener = new AccountsAdapter.OnAcClickListener() {
                            @Override
                            public void onAccClick(Accounts acc, int position) {

                                //нажатие
                            }
                        };
                        Accounts acc = new Accounts(api.getId_account(), api.getFrst_order_balance(),api.getSecond_order_balance(), api.getCurrency_cade(), api.getControl_digit(), api.getBank_division_code(), api.getBank_account_number(), api.getAccount_balance());
                        AccountsAdapter adapterAcc = new AccountsAdapter(mAccounts, accClickListener);
                        rvAssets.setAdapter(adapterAcc);
                        mAccounts.add(acc);

                    }
                }
                @Override
                public void onFailure(Call<List<Accounts>> callAcc, Throwable t) {
                    System.out.println(t.getMessage());

                }
            });

        }
        if (asset.equals("Вклады")) {
            API jsonDep = retrofit.create(API.class);
            Call<List<Deposits>> callDep = jsonDep.getDep(id_client);
            callDep.enqueue(new Callback<List<Deposits>>() {
                @Override
                public void onResponse(Call<List<Deposits>> callDep, Response<List<Deposits>> response) {
                    if (!response.isSuccessful()) {
                        System.out.println(response.code());
                        return;
                    }
                    List<Deposits> posts = response.body();
                    for (Deposits api : posts) {
                        System.out.println("Вклады");
                        // определяем слушателя нажатия элемента в списке
                        DepositsAdapter.OnDepClickListener depClickListener = new DepositsAdapter.OnDepClickListener() {
                            @Override
                            public void onDepClick(Deposits dep, int position) {
                                //нажатие
                            }
                        };
                        String date =api.getTerm_date().substring(0,10);
                        Deposits dep = new Deposits(api.getTotal(),api.getReplenishment(),date);
                        DepositsAdapter adapterDep = new DepositsAdapter(mDeposits, depClickListener);
                        rvAssets.setAdapter(adapterDep);
                        mDeposits.add(dep);

                    }
                }
                @Override
                public void onFailure(Call<List<Deposits>> callDep, Throwable t) {
                    System.out.println(t.getMessage());

                }
            });
        }
        if (asset.equals("Кредиты")) {
            API jsonCred = retrofit.create(API.class);
            Call<List<Credit>> callCred = jsonCred.getCredits(id_client);
            callCred.enqueue(new Callback<List<Credit>>() {
                @Override
                public void onResponse(Call<List<Credit>> callCred, Response<List<Credit>> response) {
                    if (!response.isSuccessful()) {
                        System.out.println(response.code());
                        return;
                    }
                    List<Credit> posts = response.body();
                    for (Credit api : posts) {
                        System.out.println("Кредиты");
                        // определяем слушателя нажатия элемента в списке
                        CreditAdapter.OnCredClickListener credClickListener = new CreditAdapter.OnCredClickListener(){
                            @Override
                            public void onCredClick(Credit cred, int position) {
                                //нажатие
                            }
                        };
                        Credit cred = new Credit(api.getTerm_date(),api.getTotal(), api.getPaid(), api.getMonthly_payment());
                        CreditAdapter adapterCred = new CreditAdapter(mCredits, credClickListener);
                        rvAssets.setAdapter(adapterCred);
                        mCredits.add(cred);

                    }
                }
                @Override
                public void onFailure(Call<List<Credit>> callCred, Throwable t) {
                    System.out.println(t.getMessage());

                }
            });
        }



    }

    public void goToMain(View view) {
        this.finish();
    }
}