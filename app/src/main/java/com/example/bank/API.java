package com.example.bank;

import com.example.bank.adress.Adress;
import com.example.bank.client.Client;
import com.example.bank.history.HistoryItem;
import com.example.bank.history.HistoryPeriod;
import com.example.bank.passport.PassportData;
import com.example.bank.registr.Register;
import com.example.bank.typeCard.TypeCard;
import com.example.bank.valute.CardOnUodate;
import com.example.bank.valute.ValuteInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    @GET("deposits/{user}")
    Call<List<Deposits>> getDep(@Path("user") String user);
    @GET("history_last/{user}")
    Call<List<HistoryItem>> getHistory(@Path("user") String user);
    @POST("history_period")
    Call<List<HistoryItem>> getHistorybyPeriod(
            @Body HistoryPeriod newPeriod
    );
    @POST("client/add_new")
    Call<Results> createClient(
            @Body ClientOnPost newClient
    );

    @POST("card/add_new")
    Call<Results> createCard(
            @Body CardOnPost newcard
    );

    @POST("account/add_new")
    Call<Results> createAcc(
            @Body AccOnPost newacc
    );
    @POST("deposits/add_new")
    Call<Results> createDep(
            @Body DepOnPost newdep
    );

    @POST("registration/add_new")
    Call<Results> createRegist(
            @Body RegistOnPost newRegist
    );
    @GET("latest.js")
    Call<ValuteInfo> getVal();
    @GET("card_type/{id}")
    Call<List<TypeCard>> getCardType(@Path("id") String id);
    @GET("registr/{login}")
    Call<List<Register>> getRegist(@Path("login") String login);
    @GET("passport/{user}")
    Call<List<PassportData>> getPass(@Path("user") String user);
    @GET("credits/{user}")
    Call<List<Credit>> getCredits(@Path("user") String user);
    @GET("client/{user}")
    Call<List<Client>> getClient(@Path("user") String user);

    @GET("last_client")
    Call<List<Client>> getLastClient();
    @GET("adress/{adress}")
    Call<List<Adress>> getAdress(@Path("adress") String adress);
    @GET("accounts/{user}")
    Call<List<Accounts>> getAcc(@Path("user") String user);
    @GET("extract/{id}")
    Call<List<ExtractItem>> getExtract(@Path("id") String id);
    @GET("clients_card/{user}")
    Call<List<Cards>> getCards(@Path("user") String user);
    @GET("clients_acc/{user}")
    Call<List<Accounts>> getAccs(@Path("user") String user);
    @GET("card/{user}")
    Call<List<Cards>> getCard(@Path("user") String user);
    @PUT("update_balance")
    Call<Results> updateBalance(
            @Body CardOnUodate newCard
    );
    @PUT("update_phone")
    Call<Results> updatePhone(
            @Body PhoneOnUpdate newPhone
    );
    @PUT("update_email")
    Call<Results> updateEmail(
            @Body EmailOnUpdate newEmail
    );
    @GET("card_number/{user}")
    Call<List<Cards>> getNumber(@Path("user") String user);
}
