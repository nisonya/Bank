package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bank.adress.Adress;
import com.example.bank.client.Client;
import com.example.bank.passport.PassportData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {
    private TextView name, email, gender, phone, adress, birthDay, passport;
    public String id_adress;
    private String id_client;
    private ImageView phone_editor, email_editor;
    private EditText new_phone, new_email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle arguments = getIntent().getExtras();
        id_client = arguments.get("id").toString();
        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        phone = findViewById(R.id.phone);
        adress = findViewById(R.id.adress);
        birthDay = findViewById(R.id.birthDay);
        passport= findViewById(R.id.passport_data);
        /*new_phone = findViewById(R.id.edit_new_phone);
        new_email = findViewById(R.id.edit_new_email);*/
        ImageView ProfileImg = (ImageView) findViewById(R.id.profile_image);

        //вывод информации о клиенте по его id
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API json = retrofit.create(API.class);
        Call<List<Client>> call = json.getClient(id_client);
        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if(!response.isSuccessful()){
                    name.setText(response.code());
                    return;
                }
                List<Client> posts = response.body();
                for (Client api: posts){
                    name.setText(api.getFirst_name()+ " "+api.getSurname());
                    email.setText(api.getEmail());
                    gender.setText(api.getId_gender());
                    String birth =api.getBirth_day().toString();
                    birthDay.setText(birth.substring(4,11)+birth.substring(24,28));
                    phone.setText(api.getPhone_code()+api.getPhone_number());
                    adress.setText(String.valueOf(api.getId_Adress()));
                    id_adress = String.valueOf(api.getId_Adress());
                    System.out.println(api.getProfile_pic()+" PPPPPPPPPPPPPPP");
                    if(api.getProfile_pic() != null){
                        String path = api.getProfile_pic();
                        Picasso.with(Profile.this)
                                .load(path)
                                .error(R.drawable.profile)
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(ProfileImg);
                    }
                    //получение и вывод адресса
                    if (id_adress == null){
                        adress.setText("Добавте адресс в отделении банка");
                    }
                    else {
                        API jsonAdress = retrofit.create(API.class);
                        Call<List<Adress>> callAdress = jsonAdress.getAdress(id_adress);
                        callAdress.enqueue(new Callback<List<Adress>>() {
                            @Override
                            public void onResponse(Call<List<Adress>> callAdress, Response<List<Adress>> response) {
                                if (!response.isSuccessful()) {
                                    adress.setText(response.code());
                                    return;
                                }
                                List<Adress> posts = response.body();
                                for (Adress api : posts) {
                                    adress.setText(api.getContry() + ", " + api.getRegion() + ", г." + api.getCity() + ", ул." + api.getStreet() + ", д." + api.getHouse() + ", кв." + api.getFlat());

                                }
                            }

                            @Override
                            public void onFailure(Call<List<Adress>> callAdress, Throwable t) {
                                adress.setText(t.getMessage());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                name.setText(t.getMessage());

            }
        });

        API jsonPass = retrofit.create(API.class);
        Call<List<PassportData>> callAPass = jsonPass.getPass(id_client);
        callAPass.enqueue(new Callback<List<PassportData>>() {
            @Override
            public void onResponse(Call<List<PassportData>> callAPass, Response<List<PassportData>> response) {
                if(!response.isSuccessful()){
                    adress.setText(response.code());
                    return;
                }
                List<PassportData> posts = response.body();
                for (PassportData api: posts){
                    String issue =api.getDate_of_issue();
                    passport.setText("Серия и номер: "+api.getSeries_passport()+" "+api.getNumber_passport()+"; "+api.getPassport_information() +" "+issue.substring(0,10));
                }
            }
            @Override
            public void onFailure(Call<List<PassportData>> callAPass, Throwable t) {
                adress.setText(t.getMessage());
            }
        });

        phone_editor = (ImageView) findViewById(R.id.phone_editor);
        email_editor = (ImageView) findViewById(R.id.email_editor);

        //ввыод окна для редактирования номера
        phone_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Profile.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_phone_editor,
                                (LinearLayout)findViewById(R.id.phone_editor_conteiner)
                        );
                bottomSheetView.findViewById(R.id.btn_phone_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new_phone = (EditText) bottomSheetView.findViewById(R.id.edit_new_phone);
                        System.out.println(new_phone.getText().toString());
                        String new_phone_str = new_phone.getText().toString();
                        System.out.println("11111111111111111111111111111111111111111111111");
                        System.out.println(new_phone_str.length());
                        if(new_phone_str.length()!=10){
                            Toast toast = Toast.makeText(Profile.this, "Некорректный номер", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else{
                            Retrofit retrofitcard = new Retrofit.Builder()
                                    .baseUrl("http://10.0.2.2:5000/api/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            // Обновление номера телефона
                            API jsonCards = retrofitcard.create(API.class);
                            PhoneOnUpdate phoneOnUpdate = new PhoneOnUpdate(new_phone_str, id_client);
                            Call<Results> call = jsonCards.updatePhone(phoneOnUpdate);
                            call.enqueue(new Callback<Results>() {
                                @Override
                                public void onResponse(Call<Results> callCards, retrofit2.Response<Results> response) {
                                    if(!response.isSuccessful()){
                                        System.out.println(response.code());
                                        Toast toast = Toast.makeText(Profile.this, "Ошибка", Toast.LENGTH_SHORT);
                                        toast.show();
                                        return;
                                    }
                                    Toast toast = Toast.makeText(Profile.this, "Успешно", Toast.LENGTH_SHORT);
                                    toast.show();
                                    bottomSheetDialog.dismiss();
                                }
                                @Override
                                public void onFailure(Call<Results> callCards, Throwable t) {
                                    System.out.println(t.getMessage());
                                }

                            });
                        }
                        System.out.println("CHAANGE");
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });


        //ввыод окна для редактирования почты
        email_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Profile.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_email_editor,
                                (LinearLayout)findViewById(R.id.email_editor_conteiner)
                        );
                bottomSheetView.findViewById(R.id.btn_email_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new_email = (EditText) bottomSheetView.findViewById(R.id.edit_new_email);
                        System.out.println(new_email.getText().toString());
                        String new_email_str = new_email.getText().toString();
                        System.out.println("11111111111111111111111111111111111111111111111");
                        System.out.println(new_email_str.length());
                        Pattern pattern;
                        Matcher matcher;
                        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                                "[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{2,3})$";
                        pattern = Pattern.compile(regex);
                        matcher = pattern.matcher(new_email_str);
                        if (!matcher.matches()) {
                            Toast toast = Toast.makeText(Profile.this, "Неверный email!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else{
                            Retrofit retrofitcard = new Retrofit.Builder()
                                    .baseUrl("http://10.0.2.2:5000/api/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            // Обновление почты пользователя
                            API jsonCards = retrofitcard.create(API.class);
                            EmailOnUpdate emailOnUpdate = new EmailOnUpdate(new_email_str, id_client);
                            Call<Results> call = jsonCards.updateEmail(emailOnUpdate);
                            call.enqueue(new Callback<Results>() {
                                @Override
                                public void onResponse(Call<Results> callCards, retrofit2.Response<Results> response) {
                                    if(!response.isSuccessful()){
                                        System.out.println(response.code());
                                        Toast toast = Toast.makeText(Profile.this, "Ошибка", Toast.LENGTH_SHORT);
                                        toast.show();
                                        return;
                                    }
                                    Toast toast = Toast.makeText(Profile.this, "Успешно", Toast.LENGTH_SHORT);
                                    toast.show();
                                    bottomSheetDialog.dismiss();
                                }
                                @Override
                                public void onFailure(Call<Results> callCards, Throwable t) {
                                    System.out.println(t.getMessage());
                                }

                            });
                        }
                        System.out.println("CHAANGE");
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });


    }

    public void goToMain(View view) {
        this.finish();
    }

}