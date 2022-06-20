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

public class Regist2 extends Fragment {
    public static EditText login,pass1, email,pass2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.regist2, container, false);
        login = (EditText) view.findViewById(R.id.login_txt);
        pass1 = (EditText) view.findViewById(R.id.pass1_txt);
        pass2 = (EditText) view.findViewById(R.id.pass2_txt);
        email = (EditText) view.findViewById(R.id.email_txt);
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
