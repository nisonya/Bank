package com.example.bank;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bank.client.Client;
import com.example.bank.history.History;
import com.example.bank.typeCard.TypeCard;
import com.example.bank.valute.ValuteInfo;
import com.example.bank.valute.ValuteRate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {


    private NavigationBarView.OnItemSelectedListener mOnItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.home_menu:
                    return true;
                case R.id.transaction_menu:
                    goToTrans();
                    return true;
                case R.id.history_menu:
                    goToHistory();
                    return true;
                case R.id.maps_menu:
                    goToMaps();
                    return true;
            }
            return false;
        }
    };
    ArrayList<ConnecctedItem> mItems = new ArrayList<>();
    ArrayList<Cards> mCards = new ArrayList<>();

    private TextView Valeu;
    private Toolbar mToolbar;
    private ImageView ProfileImg;
    private TextView GBP, USD, EUR;
    private String id_client="3";
    private ConstraintLayout frontLayout;
    private ConstraintLayout backLayout;
    private RelativeLayout.LayoutParams lp;
    boolean showBackLayout = false;
    private Button MapsOpen;
    private Button mButtonMore;
    public List<Cards> ClientsCards;
    private String first_name;
    private String last_name;

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFf");
        navigation.setSelectedItemId(R.id.home_menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnItemSelectedListener(mOnItemSelectedListener);

        Bundle arguments = getIntent().getExtras();
        id_client = arguments.get("id").toString();
        mToolbar = findViewById(R.id.toolBar);
        frontLayout = findViewById(R.id.front_layout);
        backLayout = findViewById(R.id.back_layout);
        mButtonMore=findViewById(R.id.more);
        ProfileImg =findViewById(R.id.profile_image);
        USD = findViewById(R.id.USD);
        EUR = findViewById(R.id.EUR);
        GBP = findViewById(R.id.GBP);


        setSupportActionBar(mToolbar);
        //ресайкл вывода финансовых активов
        RecyclerView.LayoutManager LLmm =new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        RecyclerView rvAssets = (RecyclerView) findViewById(R.id.rv_connected_item);
        rvAssets.setLayoutManager(LLmm);
        mItems.add(new ConnecctedItem("Счета", R.drawable.account));
        mItems.add(new ConnecctedItem("Вклады",  R.drawable.deposits));
        mItems.add(new ConnecctedItem("Кредиты",  R.drawable.credit));
        mItems.add(new ConnecctedItem("Карты",  R.drawable.cards));

        // определяем слушателя нажатия элемента в списке
        ConnecctedAdapter.OnConnClickListener connClickListener = new ConnecctedAdapter.OnConnClickListener() {
            @Override
            public void onConnClick(ConnecctedItem conn, int position) {
                Intent intentAssets = new Intent(MainActivity2.this, Assets.class);
                intentAssets.putExtra("type", conn.getNameOfType());
                intentAssets.putExtra("id", id_client);
                startActivity(intentAssets);
            }
        };
        ConnecctedAdapter adapter = new ConnecctedAdapter(mItems, connClickListener, true);
        rvAssets.setAdapter(adapter);


        TextView Hellouser =findViewById(R.id.hellouser);

        //Получение последнего курса валюты с центробанка

        Retrofit retrofitval = new Retrofit.Builder()
                .baseUrl("https://www.cbr-xml-daily.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API jsonVal = retrofitval.create(API.class);
        Call<ValuteInfo> callVal = jsonVal.getVal();
        callVal.enqueue(new Callback<ValuteInfo>() {
            @Override
            public void onResponse(Call<ValuteInfo> call, Response<ValuteInfo> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.code());
                    System.out.println("VVVVVVVVVVV");
                    Toast toast1 = Toast.makeText(MainActivity2.this, "Ошибка получения", Toast.LENGTH_SHORT);
                    toast1.show();
                    return;
                }
                else {
                ValuteInfo posts = response.body();
                ValuteRate valuteRate = posts.getRates();
                String usd = String.format("%.2f", 1/valuteRate.getUSD());
                String eur = String.format("%.2f", 1/valuteRate.getEUR());
                String gbp = String.format("%.2f", 1/valuteRate.getGBP());
                USD.setText("USD:\n"+usd);
                EUR.setText("EUR:\n"+eur);
                GBP.setText("GBP:\n"+gbp);
                }
            }

            @Override
            public void onFailure(Call<ValuteInfo> call, Throwable t) {
                String cont = t.getMessage();
                System.out.println(cont);
            }
        });

        //получение имени клиента и загрузка его изображения
        Retrofit retrofitname = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
               // .baseUrl("http://192.168.0.104:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API json = retrofitname.create(API.class);
        Call<List<Client>> call = json.getClient(id_client);
        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if(!response.isSuccessful()){
                    Hellouser.setText(response.code());
                    return;
                }
                List<Client> posts = response.body();
                for (Client api: posts){
                    System.out.println("Здравствуйте, "+api.getFirst_name());
                    Hellouser.setText("Здравствуйте, "+api.getFirst_name());
                    first_name = api.getFirst_name();
                    last_name = api.getSurname();
                    if(api.getProfile_pic() != null){
                        String path = api.getProfile_pic();
                        Picasso.with(MainActivity2.this)
                                .load(path)
                                .error(R.drawable.profile)
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(ProfileImg);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                Hellouser.setText(t.getMessage());
            }
        });

        //ресайкл карт клиента
        RecyclerView rvCards = findViewById(R.id.rv_cards);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(MainActivity2.this, LinearLayoutManager.VERTICAL, false);
        rvCards.setLayoutManager(layoutManager);

        //получение карт клиента
        API jsonCards = retrofitname.create(API.class);
        Call<List<Cards>> callCards = jsonCards.getCards(id_client);
        callCards.enqueue(new Callback<List<Cards>>() {
            @Override
            public void onResponse(Call<List<Cards>> callCards, Response<List<Cards>> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.code());
                    return;
                }
                List<Cards> posts = response.body();
                ClientsCards = posts;
                for (Cards api: posts){
                    System.out.println();
                    String number = "************"+api.getCard_number().substring(11,15);
                    Cards card =new Cards(number,api.getBalance(),api.getId_card_type(), api.getId_card(), api.getId_payment_system());
                    API jsonType = retrofitname.create(API.class);
                    Call<List<TypeCard>> callType = jsonType.getCardType(String.valueOf(api.getId_payment_system()));
                    callType.enqueue(new Callback<List<TypeCard>>() {
                        @Override
                        public void onResponse(Call<List<TypeCard>> callType, Response<List<TypeCard>> response) {
                            if(!response.isSuccessful()){
                                System.out.println(response.code());
                                return;
                            }
                            List<TypeCard> posts = response.body();
                            for (TypeCard api: posts){
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
                            CardsAdapter adapterCards = new CardsAdapter(mCards, cardClickListener, false);
                            rvCards.setAdapter(adapterCards);

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
    }

    //анимация скрытия передней панели
    private void dropLayout(){
        showBackLayout=!showBackLayout;
        lp = (RelativeLayout.LayoutParams) frontLayout.getLayoutParams();
        if(showBackLayout){
            ValueAnimator var = ValueAnimator.ofInt(backLayout.getHeight());
            var.setDuration(300);
            var.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    lp.setMargins(0, (Integer) animation.getAnimatedValue(),0,0);
                    frontLayout.setLayoutParams(lp);

                }
            });

            var.start();
        }
        else {
            DisplayMetrics displayMetrics = new DisplayMetrics();//высота дисплея
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels - 60;
            ValueAnimator var1 = ValueAnimator.ofInt(frontLayout.getHeight());
            //ValueAnimator var1 = ValueAnimator.ofInt(height);
            var1.setDuration(500);
            var1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    lp.setMargins(0, 0,0, 0);
                    frontLayout.setLayoutParams(lp);

                }
            });
            var1.start();
        }
    }


    public void drop(View view) {
        dropLayout();
    }

    //открытия активности карты с банками
    public void goToMaps() {
        Intent intentmaps = new Intent(this, Maps.class);
        startActivity(intentmaps);

    }

    //открытие профиля клиента
    public void goToProfile(View view) {
        Intent intentprofile = new Intent(this, Profile.class);
        intentprofile.putExtra("id", id_client);
        startActivity(intentprofile);
    }

    public void goToTrans() {
        Intent intentTrans = new Intent(this, Transaction.class);
        intentTrans.putExtra("id", id_client);
        startActivity(intentTrans);
    }

    public void goToHistory() {
        Intent intentTrans = new Intent(this, History.class);
        intentTrans.putExtra("id", id_client);
        startActivity(intentTrans);
    }

    public void openAcc(View view) { //Открытие нового счета
        Dialog dialog = new Dialog(MainActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Передайте ссылку на разметку
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView text_dialog = (TextView) dialog.findViewById(R.id.dialog_remainder);
        text_dialog.setText("Подвердите создание нового счета");
        Button btn_yes= (Button) dialog.findViewById(R.id.acc_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                //генерация данных
                Date dateNow = new Date();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat("YYYY-MM-dd");
                String open_date = formatForDateNow.format(dateNow);
                String frst_order_balance = New_card.getNumber(3);
                String second_order_balance = New_card.getNumber(2);
                String currency_cade = New_card.getNumber(3);
                String control_digit = New_card.getNumber(1);
                String bank_division_code = New_card.getNumber(4);
                String bank_account_number = New_card.getNumber(7);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:5000/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                API json = retrofit.create(API.class);
                //Заполнение модели
                AccOnPost accOnPost = new AccOnPost(Integer.valueOf(id_client),open_date, frst_order_balance, second_order_balance, currency_cade, control_digit, bank_division_code,bank_account_number);
                Call<Results> call = json.createAcc(accOnPost);
                call.enqueue(new Callback<Results>() {
                    @Override
                    public void onResponse(Call<Results> call, Response<Results> response) {
                        Results results = response.body();
                        System.out.println(response.isSuccessful());
                        if (!response.isSuccessful()) {
                            System.out.println(response.code());
                            Toast toast1 = Toast.makeText(MainActivity2.this, "Ошибка", Toast.LENGTH_SHORT);
                            toast1.show();
                            return;
                        } else {
                            System.out.println(response.code());
                            Toast toast = Toast.makeText(MainActivity2.this, "Успешно", Toast.LENGTH_SHORT);
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
        });
        Button btn_no= (Button) dialog.findViewById(R.id.acc_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void openDep(View view) {//открытие нового вклада
        Dialog dialog = new Dialog(MainActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Передайте ссылку на разметку
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView text_dialog = (TextView) dialog.findViewById(R.id.dialog_remainder);
        text_dialog.setText("Подтведите создание нового вклада");
        Button btn_yes= (Button) dialog.findViewById(R.id.acc_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //генерация данных
                Date dateNow = new Date();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat("YYYY-MM-dd");
                String open_date = formatForDateNow.format(dateNow);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateNow);
                calendar.add(Calendar.YEAR, 3);
                String Term_date = formatForDateNow.format(calendar.getTime());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:5000/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                API json = retrofit.create(API.class);
                //Заполнение модели
                DepOnPost depOnPost = new DepOnPost(Integer.valueOf(id_client),open_date, Term_date);
                Call<Results> call = json.createDep(depOnPost);
                call.enqueue(new Callback<Results>() {
                    @Override
                    public void onResponse(Call<Results> call, Response<Results> response) {
                        Results results = response.body();
                        System.out.println(response.isSuccessful());
                        if (!response.isSuccessful()) {
                            System.out.println(response.code());
                            Toast toast1 = Toast.makeText(MainActivity2.this, "Ошибка", Toast.LENGTH_SHORT);
                            toast1.show();
                            return;
                        } else {
                            System.out.println(response.code());
                            Toast toast = Toast.makeText(MainActivity2.this, "Успешно", Toast.LENGTH_SHORT);
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
        });
        Button btn_no= (Button) dialog.findViewById(R.id.acc_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void openCard(View view) { //открытие новой карты
        Intent intent = new Intent(this, New_card.class);
        intent.putExtra("id", id_client);
        intent.putExtra("first", first_name);
        intent.putExtra("last", last_name);
        startActivity(intent);
    }
}