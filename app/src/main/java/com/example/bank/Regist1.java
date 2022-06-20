package com.example.bank;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Regist1 extends Fragment {
    public static EditText first_name,last_name, Surname,birth_day, phone;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.regist1, container, false);
        first_name = (EditText) view.findViewById(R.id.name_txt);
        last_name = (EditText) view.findViewById(R.id.patron);
        Surname =(EditText)  view.findViewById(R.id.lastName);
        birth_day = (EditText) view.findViewById(R.id.birthDay);
        phone =(EditText)  view.findViewById(R.id.phone_txt);
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
